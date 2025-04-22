package com.coder_crushers.clinic_management.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PatientDTO {
    private Long id;
    private String name;
    private String email;
    private String mobileNo;
    private LocalDate birthDate;
    private String imageUrl;
}
