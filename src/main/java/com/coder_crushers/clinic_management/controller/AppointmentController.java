package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.model.Appointment;
import com.coder_crushers.clinic_management.model.Patient;
import com.coder_crushers.clinic_management.service.AppointmentService;
import com.coder_crushers.clinic_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/book")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
        String response = appointmentService.bookAppointment(appointment);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/check-slot")
//    public ResponseEntity<Boolean> isSlotAvailable(@RequestParam Long doctorId, @RequestParam LocalDateTime appointmentTime, @RequestParam int duration) {
//        boolean available = appointmentService.isSlotAvailable(doctorId, appointmentTime, duration);
//        return ResponseEntity.ok(available);
//    }

    @PutMapping("/mark-present/{appointmentId}")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<String> markPatientPresent(@PathVariable Long appointmentId) {
        appointmentService.markPatientPresent(appointmentId);
        return ResponseEntity.ok("Patient marked as present.");
    }

    @GetMapping("/next-patient")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Appointment> getNextPatient() {
        Appointment appointment = appointmentService.getNextPatient();
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/present")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Appointment>> getAppointmentsForPresentPatients() {
        List<Appointment> appointments = appointmentService.getAppointmentsForPresentPatients();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/not-present")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<List<Appointment>> getAppointmentsForNotPresentPatients() {
        List<Appointment> appointments = appointmentService.getAppointmentsForNotPresentPatients();
        return ResponseEntity.ok(appointments);
    }

//    @GetMapping("/patients")
//    @PreAuthorize("hasRole('RECEPTIONIST')")
//    public ResponseEntity<List<Patient>> getAllPatients() {
//        List<Patient> patients = patientService.getAllPatients();
//        return ResponseEntity.ok(patients);
//    }
//
//    @GetMapping("/patients/present")
//    @PreAuthorize("hasRole('RECEPTIONIST')")
//    public ResponseEntity<List<Patient>> getPresentPatients() {
//        List<Patient> presentPatients = patientService.getPresentPatients();
//        return ResponseEntity.ok(presentPatients);
//    }
//
//    @GetMapping("/patients/not-present")
//    @PreAuthorize("hasRole('RECEPTIONIST')")
//    public ResponseEntity<List<Patient>> getNotPresentPatients() {
//        List<Patient> notPresentPatients = patientService.getNotPresentPatients();
//        return ResponseEntity.ok(notPresentPatients);
//    }
}