package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory,Long> {

    List<MedicalHistory> findByPatientId(Long patientId);
}
