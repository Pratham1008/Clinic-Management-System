package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
    List<Appointment> findAllByStatus(AppointmentStatus appointmentStatus);

    List<Appointment> findByStatus(AppointmentStatus appointmentStatus);

    List<Appointment> findByStatusNot(AppointmentStatus appointmentStatus);

    List<Appointment> findByAppointmentTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Appointment> findByStatusAndAppointmentTimeBetween(AppointmentStatus appointmentStatus, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
