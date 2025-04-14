package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.MedicalCondition;
import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.dto.PatientDTO;
import com.coder_crushers.clinic_management.exception.UserNotFoundException;
import com.coder_crushers.clinic_management.mapper.EntityToDTOMapper;
import com.coder_crushers.clinic_management.model.MedicalHistory;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.repository.AppointmentRepository;
import com.coder_crushers.clinic_management.repository.MedicalHistoryRepository;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import com.coder_crushers.clinic_management.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepo patientRepo;
    private final MedicalHistoryRepository medicalHistoryRepository;



    @Autowired
    public PatientService(PatientRepo patientRepo, MedicalHistoryRepository medicalHistoryRepository) {
        this.patientRepo = patientRepo;
        this.medicalHistoryRepository = medicalHistoryRepository;

    }


    public PatientDTO getUserById(long id) {
        Patient patient=  patientRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return  EntityToDTOMapper.toPatientDTO(patient);
    }

    public List<MedicalHistoryDTO> getMedicalHistoryByPatientId(Long id)
    {
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findByPatientId(id);
        return EntityToDTOMapper.toMedicalHistoryDTOList(medicalHistoryList);
    }

    public MedicalCondition getPatientMedicalConditions(long id) {
        Patient patient=  patientRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return  EntityToDTOMapper.toMedicalConditionDTO(patient);
    }
}
