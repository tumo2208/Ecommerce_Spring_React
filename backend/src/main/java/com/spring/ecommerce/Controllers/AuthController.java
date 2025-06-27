package com.spring.ecommerce.Controllers;

import com.spring.ecommerce.DTOs.LoginRequest;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.DTOs.UserDTO;
import com.spring.ecommerce.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserDTO userDTO) {
        try {
            Response response = userService.registerUser(userDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest LoginRequest) {
        try {
            Response response = userService.loginUser(LoginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
