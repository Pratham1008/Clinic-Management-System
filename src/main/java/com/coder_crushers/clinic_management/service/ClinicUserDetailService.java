package com.coder_crushers.clinic_management.service;

import com.coder_crushers.clinic_management.model.User;
import com.coder_crushers.clinic_management.model.UserPrinciple;
import com.coder_crushers.clinic_management.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClinicUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public ClinicUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user==null)
        {
            throw new  UsernameNotFoundException("user not found");
        }
        return new  UserPrinciple(user);
    }
}
