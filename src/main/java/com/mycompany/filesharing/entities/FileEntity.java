package com.mycompany.filesharing.entities;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FileEntity{

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String fileName;
    private String uploadedBy;
    private LocalDateTime uploadTime;
    private LocalDateTime expiryTime;

    @Lob
    @Column(name = "file_data", columnDefinition="LONGBLOB")
    private byte[] fileData;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Getters and setters
} 
    
