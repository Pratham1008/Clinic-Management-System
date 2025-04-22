package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.dto.AppointmentRequest;
import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.dto.PatientDTO;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AppointmentService;
import com.coder_crushers.clinic_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/id/{uid}")
    public ResponseEntity<ApiResponse>getPatientById(@PathVariable String uid)
    {
        try {
            PatientDTO patientDTO = patientService.getUserById(uid);
            return ResponseEntity.ok(new ApiResponse("user found",patientDTO));
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Data not found",null));
        }
    }

    @PostMapping("/book-app")
    public ResponseEntity<ApiResponse> bookAppointment(@RequestBody AppointmentRequest appointmentRequest)
    {
        ApiResponse apiResponse = appointmentService.bookAppointment(appointmentRequest);
        return ResponseEntity.ok(new ApiResponse("success",apiResponse));
    }

    @PutMapping("/cancel-app/{id}")
    public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable Long id)
    {
        try{
            String str = appointmentService.cancelAppointment(id);
            return ResponseEntity.ok(new ApiResponse(str,null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/get-med-hist/id/{id}")
    public ResponseEntity<ApiResponse> getMedHistoryById(@PathVariable Long id)
    {
        List<MedicalHistoryDTO> medicalHistoryDTOList = patientService.findByPatientId(id);
        return ResponseEntity.ok(new ApiResponse("success",medicalHistoryDTOList));

    }


}
