package com.coder_crushers.clinic_management.repository;

import com.coder_crushers.clinic_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    public User findByEmail(String email);
}
