package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.dto.AppointmentRequest;
import com.coder_crushers.clinic_management.dto.PatientDTO;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AppointmentService;
import com.coder_crushers.clinic_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/user")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public PatientController(PatientService patientService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse>getPatientById(@PathVariable long id)
    {
        try {
            PatientDTO patientDTO = patientService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("user found",patientDTO));
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("book-app")
    public ResponseEntity<ApiResponse> bookAppointment(@RequestBody AppointmentRequest appointmentRequest)
    {
        appointmentService.bookAppointment(appointmentRequest);
        return ResponseEntity.ok(new ApiResponse("success",null));
    }





}
