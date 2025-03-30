package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.PatientDTO;
import com.coder_crushers.clinic_management.exception.UserNotFoundException;
import com.coder_crushers.clinic_management.mapper.EntityToDTOMapper;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import com.coder_crushers.clinic_management.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepo patientRepo;


    @Autowired
    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public PatientDTO getUserById(long id) {
        Patient patient=  patientRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return  EntityToDTOMapper.toPatientDTO(patient);
    }
}
