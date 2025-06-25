package com.spring.ecommerce.Services.Interfaces;

import com.spring.ecommerce.DTOs.AddressDTO;
import com.spring.ecommerce.DTOs.Response;

public interface AddressService {
    Response<?> saveAndUpdateAddress(AddressDTO addressDTO);
}
