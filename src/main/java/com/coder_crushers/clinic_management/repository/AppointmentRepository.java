package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
    List<Appointment> findAllByStatus(AppointmentStatus appointmentStatus);

    List<Appointment> findByStatus(AppointmentStatus appointmentStatus);

    List<Appointment> findByStatusNot(AppointmentStatus appointmentStatus);
}
