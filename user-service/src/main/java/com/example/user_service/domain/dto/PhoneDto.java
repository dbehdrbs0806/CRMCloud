package com.example.user_service.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDto {

    private String phoneNumber;
    private String deviceSerialNumber;
}
