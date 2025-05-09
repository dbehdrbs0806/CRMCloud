package com.example.user_service.domain.repository;


import com.example.user_service.domain.entity.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends MongoRepository<Phone, String> {
    boolean existsByDeviceSerialNumber(String deviceSerialNumber);      // DeviceSerialNumber를 중복방지 하기위해
}
