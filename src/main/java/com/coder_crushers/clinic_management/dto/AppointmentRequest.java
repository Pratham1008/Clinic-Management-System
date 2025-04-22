package com.coder_crushers.clinic_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {
    private Long patientId;
    private LocalDateTime appointmentBookingTime;
}
