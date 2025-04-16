package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.model.MedicalHistory;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.repository.MedicalHistoryRepository;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceptionistService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepo patientRepo;

    @Autowired
    public ReceptionistService(MedicalHistoryRepository medicalHistoryRepository, PatientRepo patientRepo) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.patientRepo = patientRepo;
    }


    public void addMedHist(MedicalHistoryDTO medicalHistoryDTO) {
        MedicalHistory medicalHistory = new MedicalHistory();
        Patient patient = patientRepo.findById(medicalHistoryDTO.getPatientID()).orElse(null);
        medicalHistory.setPatient(patient);
        medicalHistory.setDiagnosis(medicalHistoryDTO.getDiagnosis());
        medicalHistory.setTreatment(medicalHistoryDTO.getPrescribedMedication());
        medicalHistoryRepository.save(medicalHistory);
    }
}
