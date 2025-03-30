package com.coder_crushers.clinic_management.service;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.model.Role;
import com.coder_crushers.clinic_management.model.User;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

   private  final PatientRepo patientRepo;
   private final BCryptPasswordEncoder passwordEncoder=  new BCryptPasswordEncoder(12);

   @Autowired
    public AuthService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public void registerUser(User user) {
       Patient patient = new Patient();

       patient.setEmail(user.getEmail());
       patient.setName(user.getName());
       patient.setMobileNo(user.getMobileNo());
       patient.setBirthDate(user.getBirthDate());
       patient.setRole(Role.PATIENT);
       patient.setPassword(passwordEncoder.encode(user.getPassword()));


       patientRepo.save(patient);
    }
}
