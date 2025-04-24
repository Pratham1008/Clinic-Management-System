package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.dto.AppointmentDTO;
import com.coder_crushers.clinic_management.dto.MedicalHistoryDTO;
import com.coder_crushers.clinic_management.exception.UserNotFoundException;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AppointmentService;
import com.coder_crushers.clinic_management.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/recep")
public class ReceptionistController {

    private final AppointmentService appointmentService;
    private final ReceptionistService receptionistService;

    @Autowired
    public ReceptionistController(AppointmentService appointmentService, ReceptionistService receptionistService) {
        this.appointmentService = appointmentService;
        this.receptionistService = receptionistService;
    }

    @PutMapping("/mark-present/{id}")
    public ResponseEntity<ApiResponse>markAsPresent(@PathVariable Long id){
        appointmentService.markPatientPresent(id);
        return ResponseEntity.ok(new ApiResponse("success",null));
    }

    @GetMapping("/appointment-date")
    public ResponseEntity<ApiResponse>getAllAppointmentByDate(@RequestParam LocalDate localDate)
    {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getAppointmentsByDate(localDate);
        return ResponseEntity.ok(new ApiResponse("success",appointmentDTOList));
    }

    @PutMapping("/mark-complete/{id}")
    public ResponseEntity<ApiResponse>markAsComplete(@PathVariable Long id){
        try{
            String str =appointmentService.markAppointmentAsCompleted(id);
            return ResponseEntity.ok(new ApiResponse(str,null));
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all-present")
    public ResponseEntity<ApiResponse>getAllPresent()
    {
        try {
            List<AppointmentDTO>appointmentDTOList=appointmentService.getAppointmentsForPresentPatients();
            return ResponseEntity.ok(new ApiResponse("success",appointmentDTOList));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/date-comp/appointment")
    public ResponseEntity<ApiResponse>getCompletedForDate(@RequestParam LocalDate localDate)
    {
        List<AppointmentDTO> appointmentDTOList = appointmentService.getCompletedAppointmentsForDate(localDate);
        return ResponseEntity.ok(new ApiResponse("success",appointmentDTOList));
    }

    @PostMapping("/history")
    public ResponseEntity<ApiResponse>addMedHist(@RequestBody MedicalHistoryDTO medicalHistoryDTO){
        receptionistService.addMedHist(medicalHistoryDTO);
        return ResponseEntity.ok(new ApiResponse("success",null));
    }

}
