package com.mycompany.filesharing.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.filesharing.model.FileModel;


public interface FileService {
        public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedBy) throws IOException;
        public ResponseEntity<?> getFile(String id);
        public ResponseEntity<?> deleteFile(String id) ;
        public void deleteExpiredFiles();
        public List<FileModel> getAllFiles();
        public ResponseEntity<?> shareFile(String id);
}
