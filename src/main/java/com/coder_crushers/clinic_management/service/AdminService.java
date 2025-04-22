package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.dto.DoctorDTO;
import com.coder_crushers.clinic_management.mapper.EntityToDTOMapper;
import com.coder_crushers.clinic_management.model.Doctor;
import com.coder_crushers.clinic_management.model.Receptionist;
import com.coder_crushers.clinic_management.model.Role;
import com.coder_crushers.clinic_management.repository.DoctorRepository;
import com.coder_crushers.clinic_management.repository.ReceptionistRepository;
import com.coder_crushers.clinic_management.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class AdminService {


    private final BCryptPasswordEncoder passwordEncoder=  new BCryptPasswordEncoder(12);
    private final DoctorRepository doctorRepository;
    private final ReceptionistRepository receptionistRepository;

    @Autowired
    public AdminService(DoctorRepository doctorRepository, ReceptionistRepository receptionistRepository) {
        this.doctorRepository = doctorRepository;
        this.receptionistRepository = receptionistRepository;
    }


    public void addDoctor(Doctor doctor) {
        doctor.setRole(Role.DOCTOR);
        doctorRepository.save(doctor);
    }

    public void addReceptionist(Receptionist receptionist) {
        receptionistRepository.save(receptionist);
    }

    public List<DoctorDTO> getAllDoctors() {

        List<Doctor>doctors= doctorRepository.findAll();
        return  EntityToDTOMapper.doctorDTOList(doctors);
    }

    public List<Receptionist> getAllReceptionists() {
        return receptionistRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(new Doctor());
    }
}
