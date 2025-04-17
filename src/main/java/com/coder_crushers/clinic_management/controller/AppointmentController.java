package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.dto.AppointmentDTO;
import com.coder_crushers.clinic_management.dto.AppointmentRequest;
import com.coder_crushers.clinic_management.exception.UserNotFoundException;
import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AppointmentService;
import com.coder_crushers.clinic_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

//    @PutMapping("/mark-present/{appointmentId}")
//    @PreAuthorize("hasRole('RECEPTIONIST')")
//    public ResponseEntity<String> markPatientPresent(@PathVariable Long appointmentId) {
//        appointmentService.markPatientPresent(appointmentId);
//        return ResponseEntity.ok("Patient marked as present.");
//    }


//    @GetMapping("/present")
//    @PreAuthorize("hasRole('RECEPTIONIST')")
//    public ResponseEntity<ApiResponse> getAppointmentsForPresentPatients() {
//        try {
//            List<AppointmentDTO> appointments= appointmentService.getAppointmentsForPresentPatients();
//            return ResponseEntity.ok(new ApiResponse("success",appointments));
//
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("data not found",null));
//        }
//
//    }

    @GetMapping("/not-present")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Appointment>> getAppointmentsForNotPresentPatients() {
        List<Appointment> appointments = appointmentService.getAppointmentsForNotPresentPatients();
        return ResponseEntity.ok(appointments);
    }


}