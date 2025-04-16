package com.coder_crushers.clinic_management.dto;

import java.time.LocalDateTime;

public class AppointmentRequest {
    private Long patientId;
    private LocalDateTime appointmentBookingTime;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getAppointmentBookingTime() {
        return appointmentBookingTime;
    }

    public void setAppointmentBookingTime(LocalDateTime appointmentBookingTime) {
        this.appointmentBookingTime = appointmentBookingTime;
    }
}
