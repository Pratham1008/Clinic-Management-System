package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.dto.AppointmentDTO;
import com.coder_crushers.clinic_management.dto.MedicalCondition;
import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.exception.UserNotFoundException;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AppointmentService;
import com.coder_crushers.clinic_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/doc")
public class DoctorController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    @Autowired
    public DoctorController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    @GetMapping("/present-patient")
    public ResponseEntity<ApiResponse> getAllPresentPatient() {
        try {
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsForPresentPatients();
            return ResponseEntity.ok(new ApiResponse("success", appointments));

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("data not found", null));
        }
    }

    @GetMapping("/history/patient-id/{id}")
    public ResponseEntity<ApiResponse> getHistoryByPatientId(@PathVariable Long id) {
        List<MedicalHistoryDTO> medicalHistoryDTOS = patientService.getMedicalHistoryByPatientId(id);
        return ResponseEntity.ok(new ApiResponse("success", medicalHistoryDTOS));
    }


    @GetMapping("/med-cond/{id}")
    public ResponseEntity<ApiResponse> getPatientMedicalConditions(@PathVariable Long id)
    {

        MedicalCondition medicalCondition = patientService.getPatientMedicalConditions(id);
        return ResponseEntity.ok(new ApiResponse("success",null));
    }


}
