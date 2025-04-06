package com.coder_crushers.clinic_management.model;

public enum AppointmentStatus {
    COMPLETED,
    BOOKED, // Patient has scheduled an appointment
    PRESENT, // Patient has arrived at the clinic
    CHECKED,
    CANCELED,
}
