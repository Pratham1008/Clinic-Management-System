package com.coder_crushers.clinic_management.dto;

import com.coder_crushers.clinic_management.model.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status; // Pending, Confirmed, Cancelled
}
