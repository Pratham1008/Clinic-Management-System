package com.coder_crushers.clinic_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Allows table inheritance
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Mobile Number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    @Column(nullable = false, unique = true)
    private String mobileNo;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;


    private String imageUrl;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING) // Stores role as a string in DB
    @Column(nullable = false)
    private Role role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Mobile Number cannot be blank") @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(@NotBlank(message = "Mobile Number cannot be blank") @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public @NotBlank(message = "Password cannot be blank") @Size(min = 6, message = "Password must be at least 6 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank") @Size(min = 6, message = "Password must be at least 6 characters long") String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public @Past(message = "Birth date must be in the past") LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@Past(message = "Birth date must be in the past") LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
