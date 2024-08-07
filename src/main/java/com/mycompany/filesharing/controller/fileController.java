package com.mycompany.filesharing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mycompany.filesharing.service.FileService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
 @RequestMapping("/files")
public class fileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public String listFiles(Model model) {
        model.addAttribute("files", fileService.getAllFiles());
        return "list-files";
    }


     @PostMapping("/upload")
     public String uploadFile(@RequestParam("file") MultipartFile file,
             @RequestParam("uploadedBy") String uploadedBy) throws IOException {
         fileService.uploadFile(file, uploadedBy);
         return "redirect:/files";
     }



       @GetMapping("/share/{id}")
    public String shareFile(@PathVariable("id") String id, Model model) {
        ResponseEntity<?> fileModel = fileService.shareFile(id);
        if(fileModel.hasBody()) {
            String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            model.addAttribute("shareUrl", currentUrl);                       
            model.addAttribute("file", fileModel.getBody());
            return "share-file"; 
        }
        else {
            return "redirect:/files";
        }
    }


    @GetMapping("/download/{id}")
     public ResponseEntity<?> downloadFile(@PathVariable("id") String id) {
        return fileService.getFile(id);
    }



    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable String id) {
        ResponseEntity<?> file = fileService.deleteFile(id);
        if(file.hasBody()){
            return "redirect:/files";
        }
        else{
            return "downloadError";
        }
       
    }
}
