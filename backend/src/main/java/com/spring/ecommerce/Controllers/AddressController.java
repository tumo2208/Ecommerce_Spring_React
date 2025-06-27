package com.spring.ecommerce.Controllers;

import com.spring.ecommerce.DTOs.AddressDTO;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Services.Interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveAddress(@RequestBody AddressDTO address) {
        try {
            Response response = addressService.saveAndUpdateAddress(address);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to save address: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
