package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.AppointmentDTO;
import com.coder_crushers.clinic_management.dto.AppointmentRequest;
import com.coder_crushers.clinic_management.exception.UserNotFoundException;
import com.coder_crushers.clinic_management.mapper.EntityToDTOMapper;
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

    }

    public String bookAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment();
        lock.lock();
        try {
            if (!canBookAppointments) {
                logger.warn("Appointment booking is disabled by the doctor.");
                return "Booking is closed as per the doctor's request.";
            }

            LocalTime appointmentTime = appointmentRequest.getAppointmentBookingTime().toLocalTime();
            if (appointmentTime.isBefore(CLINIC_OPEN_TIME) || appointmentTime.isAfter(CLINIC_CLOSE_TIME)) {
                logger.warn("Attempted booking outside clinic hours: {}", appointmentTime);
                return "Clinic is only open from 9 AM to 9 PM.";
            }

            if (!hasEnoughTimeBeforeClosing(appointmentRequest.getAppointmentBookingTime())) {
                logger.warn("Insufficient time to book before clinic closes.");
                return "Not enough time left before clinic closing.";
            }
            // Determine the latest appointment time in the queue
            LocalDateTime lastAppointmentTime = appointmentQueue.stream()
                    .map(Appointment::getAppointmentTime)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.of(appointmentRequest.getAppointmentBookingTime().toLocalDate(), CLINIC_OPEN_TIME));

            // Calculate next available slot
            LocalDateTime calculatedAppointmentTime = lastAppointmentTime.isAfter(LocalDateTime.now())
                    ? lastAppointmentTime.plusMinutes((long) averageConsultationTime)
                    : LocalDateTime.now().plusMinutes((long) averageConsultationTime);

            // Ensure it’s not beyond clinic closing
            if (calculatedAppointmentTime.toLocalTime().isAfter(CLINIC_CLOSE_TIME)) {
                logger.warn("Insufficient time to book before clinic closes.");
                return "Not enough time left before clinic closing.";
            }

            // Populate and save appointment
            appointment.setStatus(AppointmentStatus.BOOKED);
            appointment.setAppointmentTime(calculatedAppointmentTime);

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

    public void setCanBookAppointments(boolean status) {
        this.canBookAppointments = status;
        logger.info("Appointment booking status updated: {}", status);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findByStatusNot(AppointmentStatus.COMPLETED);
    }

    public List<AppointmentDTO> getAppointmentsForPresentPatients() throws UserNotFoundException {
        List<Appointment> appointments = appointmentRepository.findByStatus(AppointmentStatus.PRESENT);
        if(appointments.isEmpty())
            throw new UserNotFoundException("No patient is present in hospital");

        return EntityToDTOMapper.appointmentDTOList(appointments);

    }

    public List<Appointment> getAppointmentsForNotPresentPatients(){
       return appointmentRepository.findByStatusNot(AppointmentStatus.PRESENT);
    }


    public String cancelAppointment(Long id) {
        lock.lock();
        try {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            // Remove from both queues
            appointmentQueue.removeIf(a -> a.getId().equals(id));
            clinicQueue.removeIf(a -> a.getId().equals(id));

            // Update status
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentRepository.save(appointment);

            logger.info("Appointment with ID {} cancelled successfully.", id);
            return "Appointment cancelled successfully.";

        } catch (Exception e) {
            logger.error("Error cancelling appointment: {}", e.getMessage());
            return "An error occurred while cancelling the appointment.";
        } finally {
            lock.unlock();
        }
    }

    public String markAppointmentAsCompleted(Long appointmentId) {
        lock.lock();
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                logger.info("Appointment ID {} is already marked as completed.", appointmentId);
                return "Appointment is already completed.";
            }

            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.save(appointment);

            // Remove from clinic queue if still present
            appointmentQueue.removeIf(a->a.getId().equals(appointmentId));
            clinicQueue.removeIf(a -> a.getId().equals(appointmentId));

            logger.info("Appointment ID {} marked as completed.", appointmentId);
            return "Appointment marked as completed successfully.";
        } catch (Exception e) {
            logger.error("Error marking appointment as completed: {}", e.getMessage());
            return "An error occurred while marking the appointment as completed.";
        } finally {
            lock.unlock();
        }
    }










}






























