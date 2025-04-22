package com.coder_crushers.clinic_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {
    private Long id;
    private String name;
    private String email;
    private String specialization;
    private String mobileNo;
}
