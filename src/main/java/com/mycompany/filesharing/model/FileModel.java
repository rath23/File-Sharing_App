package com.mycompany.filesharing.model;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class FileModel {
    private String id;
    private String fileName;
    private String uploadedBy;
    private LocalDateTime uploadTime;
    private LocalDateTime expiryTime;
    private byte[] fileData;
}
