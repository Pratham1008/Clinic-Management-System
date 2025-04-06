package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.AppointmentStatus;
import com.coder_crushers.clinic_management.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final Lock lock = new ReentrantLock();

    private final ConcurrentLinkedQueue<Appointment> appointmentQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Appointment> clinicQueue = new ConcurrentLinkedQueue<>();

    private static final LocalTime CLINIC_OPEN_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLINIC_CLOSE_TIME = LocalTime.of(21, 0);

    private boolean canBookAppointments = true;
    private double averageConsultationTime = 15;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        updateAverageConsultationTime();
    }

    public String bookAppointment(Appointment appointment) {
        lock.lock();
        try {
            if (!canBookAppointments) {
                logger.warn("Appointment booking is disabled by the doctor.");
                return "Booking is closed as per the doctor's request.";
            }

            LocalTime appointmentTime = appointment.getAppointmentTime().toLocalTime();
            if (appointmentTime.isBefore(CLINIC_OPEN_TIME) || appointmentTime.isAfter(CLINIC_CLOSE_TIME)) {
                logger.warn("Attempted booking outside clinic hours: {}", appointmentTime);
                return "Clinic is only open from 9 AM to 9 PM.";
            }

            if (!hasEnoughTimeBeforeClosing(appointment.getAppointmentTime())) {
                logger.warn("Insufficient time to book before clinic closes.");
                return "Not enough time left before clinic closing.";
            }

            appointment.setStatus(AppointmentStatus.BOOKED);
            appointmentRepository.save(appointment);
            appointmentQueue.add(appointment);
            logger.info("Appointment booked successfully: {}", appointment);
            return "Appointment booked successfully!";


        } catch (Exception e) {
            logger.error("Error while booking appointment: {}", e.getMessage());
            return "An error occurred while booking the appointment.";
        } finally {
            lock.unlock();
        }
    }

    private boolean hasEnoughTimeBeforeClosing(LocalDateTime appointmentTime) {
        long remainingAppointments = appointmentQueue.size();
        long timeRemaining = CLINIC_CLOSE_TIME.toSecondOfDay() - appointmentTime.toLocalTime().toSecondOfDay();
        return (timeRemaining - (remainingAppointments * averageConsultationTime * 60)) > (averageConsultationTime * 60);
    }

    public void markPatientPresent(Long appointmentId) {
        lock.lock();
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));
            appointment.setStatus(AppointmentStatus.PRESENT);
            clinicQueue.add(appointment);
            appointmentRepository.save(appointment);
            updateAverageConsultationTime();
            logger.info("Patient marked as present for appointment ID: {}", appointmentId);
        } catch (Exception e) {
            logger.error("Error marking patient present: {}", e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public Appointment getNextPatient() {
        return clinicQueue.poll();
    }

    private void updateAverageConsultationTime() {
        lock.lock();
        try {
            List<Appointment> pastAppointments = appointmentRepository.findAllByStatus(AppointmentStatus.COMPLETED);
            OptionalDouble avgTime = pastAppointments.stream()
                    .mapToInt(Appointment::getEstimatedConsultationTime)
                    .average();
            avgTime.ifPresent(value -> {
                averageConsultationTime = value;
                logger.info("Updated average consultation time: {} minutes", averageConsultationTime);
            });
        } catch (Exception e) {
            logger.error("Error updating average consultation time: {}", e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void setCanBookAppointments(boolean status) {
        this.canBookAppointments = status;
        logger.info("Appointment booking status updated: {}", status);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findByStatusNot(AppointmentStatus.COMPLETED);
    }

    public List<Appointment> getAppointmentsForPresentPatients() {
        return appointmentRepository.findByStatus(AppointmentStatus.PRESENT);
    }

    public List<Appointment> getAppointmentsForNotPresentPatients() {
        return appointmentRepository.findByStatusNot(AppointmentStatus.PRESENT);
    }


    public void cancelAppointment(Long id)
    {
        appointmentQueue.removeIf(appointment -> appointment.getId().equals(id));
    }


}





























//package com.coder_crushers.clinic_management.service;
//
//import com.coder_crushers.clinic_management.model.Appointment;
//import com.coder_crushers.clinic_management.model.AppointmentStatus;
//import com.coder_crushers.clinic_management.repository.AppointmentRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.PriorityQueue;
//
//@Service
//public class AppointmentService {
//
//    private final AppointmentRepository appointmentRepository;
//
//    // Queue for booked appointments sorted by appointment time
//    private final PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>(
//            (a1, a2) -> a1.getAppointmentTime().compareTo(a2.getAppointmentTime())
//    );
//
//    // Queue for patients present in the clinic
//    private final PriorityQueue<Appointment> clinicQueue = new PriorityQueue<>(
//            (a1, a2) -> a1.getAppointmentTime().compareTo(a2.getAppointmentTime())
//    );
//
//    public AppointmentService(AppointmentRepository appointmentRepository) {
//        this.appointmentRepository = appointmentRepository;
//    }
//
//    public String bookAppointment(Appointment appointment) {
//        // Check if appointment slot is available
//        if (isSlotAvailable(appointment.getDoctor().getId(), appointment.getAppointmentTime(), appointment.getEstimatedConsultationTime())) {
//            appointment.setStatus(AppointmentStatus.BOOKED);
//            appointmentRepository.save(appointment);
//            appointmentQueue.add(appointment);
//            return "Appointment booked successfully!";
//        } else {
//            return "Time slot is not available!";
//        }
//    }
//
//    public boolean isSlotAvailable(Long doctorId, LocalDateTime appointmentTime, int duration) {
//        // Check if the slot is available based on existing appointments
//        return appointmentQueue.stream()
//                .noneMatch(app -> app.getDoctor().getId().equals(doctorId) &&
//                        isOverlapping(app.getAppointmentTime(), app.getEstimatedConsultationTime(), appointmentTime, duration));
//    }
//
//    private boolean isOverlapping(LocalDateTime existingTime, int existingDuration, LocalDateTime newTime, int newDuration) {
//        LocalDateTime existingEnd = existingTime.plusMinutes(existingDuration);
//        LocalDateTime newEnd = newTime.plusMinutes(newDuration);
//        return !(newEnd.isBefore(existingTime) || newTime.isAfter(existingEnd));
//    }
//
//    public void markPatientPresent(Long appointmentId) {
//        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
//        appointment.setStatus(AppointmentStatus.PRESENT);
//        clinicQueue.add(appointment);
//        appointmentRepository.save(appointment);
//    }
//
//    public Appointment getNextPatient() {
//        return clinicQueue.poll(); // Fetch the next patient in line
//    }
//}
