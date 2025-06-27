package com.spring.ecommerce.Services.Implementations;

import com.spring.ecommerce.DTOs.LoginRequest;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.DTOs.UserDTO;
import com.spring.ecommerce.Enums.UserRole;
import com.spring.ecommerce.Exceptions.InvalidCredentialsException;
import com.spring.ecommerce.Exceptions.ResourceNotFoundException;
import com.spring.ecommerce.Mapping.EntityDTOMapping;
import com.spring.ecommerce.Models.User;
import com.spring.ecommerce.Repositories.UserRepository;
import com.spring.ecommerce.Security.JwtUtils;
import com.spring.ecommerce.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EntityDTOMapping entityDTOMapping;

    @Override
    public Response<?> registerUser(UserDTO registrationRequest) {
        UserRole role = UserRole.USER;
        if (registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")) {
            role = UserRole.ADMIN;
        }
        User user = new User();
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setPhoneNumber(registrationRequest.getPhoneNumber());
        user.setRole(role);
        user.setOrderItemList(new ArrayList<>());

        User savedUser = userRepository.save(user);
        UserDTO userDTO = entityDTOMapping.userDtoFromUser(savedUser);
        Response<UserDTO> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User registered successfully");
        response.setData(userDTO);
        return response;
    }

    @Override
    public Response<?> loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String token = jwtUtils.generateToken(user);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Login successful");
        response.setToken(token);
        response.setExpirationTime("24 hours");
        response.setRole(user.getRole().name());
        return response;
    }

    @Override
    public Response<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(entityDTOMapping::userDtoFromUser).toList();
        Response<List<UserDTO>> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("All users retrieved successfully");
        response.setData(userDTOs);
        return response;
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public Response<?> getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDTO userDTO = entityDTOMapping.userDtoFromUser(user);
        Response<UserDTO> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("User info and order history retrieved successfully");
        response.setData(userDTO);
        return response;
    }
}
