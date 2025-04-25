package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.MedicalHistory;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.repository.AppointmentRepository;
import com.coder_crushers.clinic_management.repository.MedicalHistoryRepository;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepo patientRepo;


    public void addMedHist(MedicalHistoryDTO medicalHistoryDTO) {
        MedicalHistory medicalHistory = new MedicalHistory();
        Patient patient = patientRepo.findById(medicalHistoryDTO.getPatientID()).orElse(null);
        medicalHistory.setPatient(patient);
        medicalHistory.setDiagnosis(medicalHistoryDTO.getDiagnosis());
        medicalHistory.setTreatment(medicalHistoryDTO.getPrescribedMedication());
        medicalHistory.setVisitDate(LocalDate.now());
        medicalHistoryRepository.save(medicalHistory);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
