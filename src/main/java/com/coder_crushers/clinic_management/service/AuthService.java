package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.model.Role;
import com.coder_crushers.clinic_management.model.User;
import com.coder_crushers.clinic_management.repository.PatientRepo;
import com.coder_crushers.clinic_management.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

   private  final PatientRepo patientRepo;


    public ApiResponse registerUser(Patient user) {
       Patient patient = new Patient();


       patient.setEmail(user.getEmail());
       patient.setName(user.getName());
       patient.setMobileNo(user.getMobileNo());
       patient.setRole(Role.PATIENT);
//       patient.setAllergies(user.getAllergies());
//       patient.setOtherConditions(user.getOtherConditions());

       patient.setFirebaseUid(user.getFirebaseUid());
       patient.setFcmToken(user.getFcmToken());

       patientRepo.save(patient);

       return new ApiResponse("Successfully registered", null);
    }

    public ApiResponse loginUser(User user) {
       Patient patient = patientRepo.findByEmail(user.getEmail());
       if (patient == null) {
          return new ApiResponse("Invalid email or password", null);
       }
       patient.setFcmToken(user.getFcmToken());
       return new ApiResponse("Successfully logged in", patient);
    }
}
