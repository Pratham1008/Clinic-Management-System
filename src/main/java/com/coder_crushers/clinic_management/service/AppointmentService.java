package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.AppointmentStatus;
import com.coder_crushers.clinic_management.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    // Queue for booked appointments sorted by appointment time
    private final PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>(
            (a1, a2) -> a1.getAppointmentTime().compareTo(a2.getAppointmentTime())
    );

    // Queue for patients present in the clinic
    private final PriorityQueue<Appointment> clinicQueue = new PriorityQueue<>(
            (a1, a2) -> a1.getAppointmentTime().compareTo(a2.getAppointmentTime())
    );

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public String bookAppointment(Appointment appointment) {
        // Check if appointment slot is available
        if (isSlotAvailable(appointment.getDoctor().getId(), appointment.getAppointmentTime(), appointment.getEstimatedConsultationTime())) {
            appointment.setStatus(AppointmentStatus.BOOKED);
            appointmentRepository.save(appointment);
            appointmentQueue.add(appointment);
            return "Appointment booked successfully!";
        } else {
            return "Time slot is not available!";
        }
    }

    public boolean isSlotAvailable(Long doctorId, LocalDateTime appointmentTime, int duration) {
        // Check if the slot is available based on existing appointments
        return appointmentQueue.stream()
                .noneMatch(app -> app.getDoctor().getId().equals(doctorId) &&
                        isOverlapping(app.getAppointmentTime(), app.getEstimatedConsultationTime(), appointmentTime, duration));
    }

    private boolean isOverlapping(LocalDateTime existingTime, int existingDuration, LocalDateTime newTime, int newDuration) {
        LocalDateTime existingEnd = existingTime.plusMinutes(existingDuration);
        LocalDateTime newEnd = newTime.plusMinutes(newDuration);
        return !(newEnd.isBefore(existingTime) || newTime.isAfter(existingEnd));
    }

    public void markPatientPresent(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus(AppointmentStatus.PRESENT);
        clinicQueue.add(appointment);
        appointmentRepository.save(appointment);
    }

    public Appointment getNextPatient() {
        return clinicQueue.poll(); // Fetch the next patient in line
    }
}
