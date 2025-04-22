package com.coder_crushers.clinic_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MedicalCondition {
    private Set<String> allergies;
}
