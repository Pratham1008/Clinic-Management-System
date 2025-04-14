package com.coder_crushers.clinic_management.dto;

import java.util.List;

public class MedicalCondition {
        private List<String> allergies;

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
