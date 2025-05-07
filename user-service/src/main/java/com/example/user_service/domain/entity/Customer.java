package com.example.user_service.domain.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;


import java.util.List;

@Document(collection = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true) // 중복방지
    private String email;

    @Indexed(unique = true)
    private String username;

    private String password;

    // @DBRef  MongoDB의 1:1 참조
    private Phone phone;
}
