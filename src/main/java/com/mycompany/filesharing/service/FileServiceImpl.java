package com.mycompany.filesharing.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.filesharing.entities.FileEntity;
import com.mycompany.filesharing.entities.UserInfo;
import com.mycompany.filesharing.model.FileModel;
import com.mycompany.filesharing.repository.FileRepository;
import com.mycompany.filesharing.repository.UserInfoRepository;
import com.mycompany.filesharing.exception.FileNotFoundException;
import com.mycompany.filesharing.exception.UserEmailNotFound;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedBy,UserDetails userDetails) throws IOException {
        UserInfo user = userInfoRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setUploadedBy(uploadedBy);
        fileEntity.setUploadTime(LocalDateTime.now());
        fileEntity.setExpiryTime(LocalDateTime.now().plusDays(1)); // 24 hours expiry
        fileEntity.setUserInfo(user);
        fileEntity.setFileData(file.getBytes());
        fileRepository.save(fileEntity);
        FileModel fileModel = new FileModel();
        BeanUtils.copyProperties(fileEntity, fileModel);
        return ResponseEntity.ok().body(fileModel);
    }

    public ResponseEntity<?> getFile(String id) {
        Optional<FileEntity> fileEntityOptional = fileRepository.findById(id);

        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            FileModel fileModel = new FileModel();
            BeanUtils.copyProperties(fileEntity, fileModel);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(fileModel.getFileData());
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    public ResponseEntity<?> deleteFile(String id, UserDetails userDetails) {
        UserInfo user = userInfoRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
        
        Optional <FileEntity> entity = fileRepository.findById(id);
        if (entity.get().getUserInfo().equals(user)) {
        if(entity.isPresent()){
            fileRepository.delete(entity.get());
            return ResponseEntity.ok().body("Deleted successfully");
        }
        else{
            throw new FileNotFoundException("File not found");
        }}
        else{
            throw new UserEmailNotFound("Can't delete file as user info does not match");
        }

    }

    // @Scheduled(cron = "0 0 * * * *") 
    @Scheduled(cron = "*/1 * * * * *")
    public void deleteExpiredFiles() {
        List<FileEntity> expiredFiles = fileRepository.findByExpiryTimeBefore(LocalDateTime.now());
        expiredFiles.forEach(fileRepository::delete);
        System.out.println("Deleted expired files at: " + LocalDateTime.now());
    }

    private FileModel convertToModel(FileEntity entity) {
        FileModel model = new FileModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    
    @Override
    public List<FileModel> getAllFiles(UserDetails userDetails) {
        UserInfo user = userInfoRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
        List<FileEntity> entityList = fileRepository.findByUserInfo(user);
        return entityList.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> shareFile(String id) {
        Optional <FileEntity> fileEntity = fileRepository.findById(id);
        if(fileEntity.isPresent()){
            FileEntity file = fileEntity.get();
            FileModel fileModel = new FileModel();
            BeanUtils.copyProperties(file, fileModel);
            return ResponseEntity.ok().body(fileModel);
 }
 else{
     throw new FileNotFoundException("File not found");
 }
}
}
