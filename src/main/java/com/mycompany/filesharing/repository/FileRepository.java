package com.mycompany.filesharing.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.filesharing.entities.FileEntity;
import com.mycompany.filesharing.entities.UserInfo;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    List<FileEntity> findByExpiryTimeBefore(LocalDateTime now);
    List<FileEntity> findByUserInfo(UserInfo userInfo);

}
