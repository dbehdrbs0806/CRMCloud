package com.example.user_service.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CustomerDto {
    private String id;
    private String name;
    private String email;
    private String password;

    private PhoneDto phone;
}
