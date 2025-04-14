package com.coder_crushers.clinic_management.model;

import jakarta.persistence.*;


import java.util.List;
import java.util.Set;

@Entity
public class Patient extends User {

    @ElementCollection
    private Set<String> allergies;

    @ElementCollection
    private Set<String> otherConditions;  // Example: ["Asthma", "Hypertension"]

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalHistory> medicalHistories;


    public List<String> getAllergies() {
        return allergies.stream().toList();
    }

    public void setAllergies(Set<String> allergies) {
        this.allergies = allergies;
    }

    public Set<String> getOtherConditions() {
        return otherConditions;
    }

    public void setOtherConditions(Set<String> otherConditions) {
        this.otherConditions = otherConditions;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<MedicalHistory> getMedicalHistories() {
        return medicalHistories;
    }

    public void setMedicalHistories(List<MedicalHistory> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }
}

