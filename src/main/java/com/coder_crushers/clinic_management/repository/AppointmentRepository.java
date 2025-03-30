package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
}
