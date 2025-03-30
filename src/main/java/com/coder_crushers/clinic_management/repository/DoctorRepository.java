package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
