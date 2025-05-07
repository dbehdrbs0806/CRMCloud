package com.example.user_service.domain.entity;


import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "phone")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {

    @Id
    private String id;

    @Indexed
    private String phoneNumber;

    private String deviceSerialNumber;
}
