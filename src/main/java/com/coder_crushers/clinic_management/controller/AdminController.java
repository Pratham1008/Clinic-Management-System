package com.coder_crushers.clinic_management.controller;

import com.coder_crushers.clinic_management.model.Doctor;
import com.coder_crushers.clinic_management.model.Receptionist;
import com.coder_crushers.clinic_management.response.ApiResponse;
import com.coder_crushers.clinic_management.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addDoctor(@RequestBody Doctor doctor) {
        adminService.addDoctor(doctor);
        return ResponseEntity.ok(new ApiResponse("Doctor added successfully!",doctor));
    }

    @PostMapping("/add-receptionist")
    public ResponseEntity<ApiResponse> addReceptionist(@RequestBody Receptionist receptionist) {
        adminService.addReceptionist(receptionist);
        return ResponseEntity.ok(new ApiResponse("Receptionist added successfully!",null));
    }

    @GetMapping("/list-doctors")
    public ResponseEntity<ApiResponse> listDoctors() {
        List<Doctor> list = adminService.getAllDoctors();
        if(!list.isEmpty())
        {
            return ResponseEntity.ok(new ApiResponse("found",list));
        }
        return ResponseEntity.ok(new ApiResponse("not found",new Doctor()));
    }

    @GetMapping("/list-receptionists")
    public ResponseEntity<ApiResponse> listReceptionists() {
        List<Receptionist> list = adminService.getAllReceptionists();
        if(!list.isEmpty())
        {
            return ResponseEntity.ok(new ApiResponse("found",list));
        }
        return ResponseEntity.ok(new ApiResponse("not found",null));
    }

    @GetMapping("/DocId/{id}")
    public ResponseEntity<ApiResponse> getDoctorById(@PathVariable Long id)
    {
        Doctor doctor= adminService.getDoctorById(id);
        return ResponseEntity.ok(new ApiResponse("doctor ",doctor));
    }

    @GetMapping("/csrf")
     public CsrfToken getCsrf(HttpServletRequest http )
    {
        return (CsrfToken) http.getAttribute("_csrf");
    }


}
