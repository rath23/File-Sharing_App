package com.mycompany.filesharing.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.filesharing.model.FileModel;


public interface FileService {
        public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedBy, UserDetails userDetails) throws IOException;
        public ResponseEntity<?> getFile(String id);
        public ResponseEntity<?> deleteFile(String id,UserDetails userDetails) ;
        public void deleteExpiredFiles();
       // public List<FileModel> getAllFiles();
       public List<FileModel> getAllFiles(UserDetails userDetails);
        public ResponseEntity<?> shareFile(String id);
}
