package com.spring.ecommerce.Services.Implementations;

import com.spring.ecommerce.DTOs.AddressDTO;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Mapping.EntityDTOMapping;
import com.spring.ecommerce.Models.Address;
import com.spring.ecommerce.Models.User;
import com.spring.ecommerce.Repositories.AddressRepository;
import com.spring.ecommerce.Services.Interfaces.AddressService;
import com.spring.ecommerce.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Override
    public Response<?> saveAndUpdateAddress(AddressDTO addressDTO) {
        User user = userService.getLoginUser();
        Address address = user.getAddress();
        if (user.getAddress() == null) {
            address = new Address();
            address.setUser(user);
        }
        if (addressDTO.getNumber() != null) address.setNumber(addressDTO.getNumber());
        if (addressDTO.getCity() != null) address.setCity(addressDTO.getCity());
        if (addressDTO.getDistrict() != null) address.setDistrict(addressDTO.getDistrict());
        if (addressDTO.getZipCode() != null) address.setZipCode(addressDTO.getZipCode());
        if (addressDTO.getCountry() != null) address.setCountry(addressDTO.getCountry());

        addressRepository.save(address);
        Response response = new Response<>();
        response.setStatus(200);
        response.setMessage("Address saved successfully");
        return response;
    }
}
