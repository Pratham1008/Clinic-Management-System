package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory,Long> {
}
