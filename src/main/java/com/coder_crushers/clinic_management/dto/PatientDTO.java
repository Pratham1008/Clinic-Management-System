package com.coder_crushers.clinic_management.dto;

import com.coder_crushers.clinic_management.model.MedicalHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public class PatientDTO {
    private Long id;
    private String name;
    private String email;
    private String mobileNo;
    private LocalDate birthDate;
    private String imageUrl;
    List<MedicalHistoryDTO> medicalHistories;
    private Set<String> allergies;
    private List<AppointmentDTO> appointments; // List of patient’s booked appointments


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MedicalHistoryDTO> getMedicalHistories() {
        return medicalHistories;
    }

    public void setMedicalHistories(List<MedicalHistoryDTO> medicalHistories) {
        this.medicalHistories = medicalHistories;
    }

    public Set<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<String> allergies) {
        this.allergies = allergies;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }


}
