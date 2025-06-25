package com.spring.ecommerce.Services.Interfaces;

import com.spring.ecommerce.DTOs.LoginRequest;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.DTOs.UserDTO;
import com.spring.ecommerce.Models.User;

public interface UserService {
    Response<?> registerUser(UserDTO userDTO);
    Response<?> loginUser(LoginRequest loginRequest);
    Response<?> getAllUsers();
    User getLoginUser();
    Response<?> getUserInfoAndOrderHistory();
}
