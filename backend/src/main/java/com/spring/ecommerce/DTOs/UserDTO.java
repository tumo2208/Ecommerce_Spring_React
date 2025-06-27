package com.spring.ecommerce.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.ecommerce.Enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private List<Long> orderItemIdList;
    private Long addressId;

    private LocalDate createdAt;
}
