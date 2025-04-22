package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,Long> {
    Patient findByEmail(String email);
    Patient findByFirebaseUid(String firebaseUid);
}
