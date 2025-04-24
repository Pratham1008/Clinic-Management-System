package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.DoctorDTO;
import com.coder_crushers.clinic_management.dto.MedicalCondition;
import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.dto.PatientDTO;
import com.coder_crushers.clinic_management.mapper.EntityToDTOMapper;
import com.coder_crushers.clinic_management.model.Doctor;
import com.coder_crushers.clinic_management.model.MedicalHistory;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.repository.DoctorRepository;
import com.coder_crushers.clinic_management.repository.MedicalHistoryRepository;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepo patientRepo;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final DoctorRepository doctorRepository;


    @Autowired
    public PatientService(PatientRepo patientRepo, MedicalHistoryRepository medicalHistoryRepository, DoctorRepository doctorRepository) {
        this.patientRepo = patientRepo;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.doctorRepository = doctorRepository;
    }

    public PatientDTO getUserById(String uid) {
        Patient patient=  patientRepo.findByFirebaseUid(uid);
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

    public DoctorDTO getDoctorById() {
        Doctor patient = doctorRepository.findById(1L).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return EntityToDTOMapper.toDoctorDTO(patient);
    }

    public List<MedicalHistoryDTO> findByPatientId(Long id) {
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findByPatientId(id);
        return EntityToDTOMapper.toMedicalHistoryDTOList(medicalHistoryList);
    }
}
