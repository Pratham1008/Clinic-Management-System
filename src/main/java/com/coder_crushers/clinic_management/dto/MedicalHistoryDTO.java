package com.coder_crushers.clinic_management.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicalHistoryDTO {
    private Long patientID;
    private Long id;
    private LocalDate date;
    private String diagnosis;
    private String prescribedMedication;
}
