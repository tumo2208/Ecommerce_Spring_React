package com.spring.ecommerce.Controllers;

import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        try {
            Response response = userService.getAllUsers();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to retrieve users: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Response> getUserProfile() {
        try {
            Response response = userService.getUserInfoAndOrderHistory();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to retrieve user profile: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
