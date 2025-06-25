package com.spring.ecommerce.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    // Base response information
    private int status;
    private String message;
    private final LocalDate timestamp = LocalDate.now();

    // Authentication details
    private String token;
    private String role;
    private String expirationTime;

    // Pagination details
    private int totalPages;
    private int totalElements;

    private T data;
}
