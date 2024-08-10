package com.mycompany.filesharing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.filesharing.entities.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);
    UserInfo findByVerificationCode(String code);
}
