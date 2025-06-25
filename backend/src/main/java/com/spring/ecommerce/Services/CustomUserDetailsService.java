package com.spring.ecommerce.Services;

import com.spring.ecommerce.Exceptions.ResourceNotFoundException;
import com.spring.ecommerce.Models.User;
import com.spring.ecommerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Implement the methods required by UserDetailsService
    // For example, loadUserByUsername(String username) to fetch user details from the database

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));
        return user;
    }


}
