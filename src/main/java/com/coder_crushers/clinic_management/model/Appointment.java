package com.coder_crushers.clinic_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDateTime appointmentTime; // User-selected slot

    private int estimatedConsultationTime; // Time in minutes

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;









    //getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getEstimatedConsultationTime() {
        return estimatedConsultationTime;
    }

    public void setEstimatedConsultationTime(int estimatedConsultationTime) {
        this.estimatedConsultationTime = estimatedConsultationTime;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}

