package com.coder_crushers.clinic_management.mapper;

import com.coder_crushers.clinic_management.dto.*;
import com.coder_crushers.clinic_management.model.*;

import java.util.stream.Collectors;

public class EntityToDTOMapper {

    public static PatientDTO toPatientDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setMobileNo(patient.getMobileNo());
        dto.setBirthDate(patient.getBirthDate());
        dto.setImageUrl(patient.getImageUrl());

        // Convert list of MedicalHistory entities to DTOs
        dto.setMedicalHistories(
                patient.getMedicalHistories().stream()
                        .map(EntityToDTOMapper::toMedicalHistoryDTO)
                        .collect(Collectors.toList())
        );

        // Convert list of Appointments to AppointmentDTOs
        dto.setAppointments(
                patient.getAppointments().stream()
                        .map(EntityToDTOMapper::toAppointmentDTO)
                        .collect(Collectors.toList())
        );

        dto.setAllergies(patient.getAllergies());
        return dto;
    }


    public static DoctorDTO toDoctorDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setEmail(doctor.getEmail());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setMobileNo(doctor.getMobileNo());
        return dto;
    }

    public static ReceptionistDTO toReceptionistDTO(Receptionist receptionist) {
        ReceptionistDTO dto = new ReceptionistDTO();
        dto.setId(receptionist.getId());
        dto.setName(receptionist.getName());
        dto.setEmail(receptionist.getEmail());
        dto.setMobileNo(receptionist.getMobileNo());
        return dto;
    }

    public static AppointmentDTO toAppointmentDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    public static MedicalHistoryDTO toMedicalHistoryDTO(MedicalHistory history) {
        MedicalHistoryDTO dto = new MedicalHistoryDTO();
        dto.setId(history.getId());
        dto.setDate(history.getVisitDate());
        dto.setDiagnosis(history.getDiagnosis());
        dto.setPrescribedMedication(history.getTreatment());
        return dto;
    }
}
