package com.mycompany.filesharing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.mycompany.filesharing.model.UserInfoModel;
import com.mycompany.filesharing.service.FileService;
import com.mycompany.filesharing.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

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

        @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registered")
    public String registerUser(@ModelAttribute UserInfoModel user) {
        userService.saveUser(user);
        return "redirect:/login";
    }


    // @GetMapping("/home")
    // public String listFiles(Model model,Principal principal) {
    //     String email = ((CustomUserDetails) ((Authentication) principal).getPrincipal()).getUsername();
    //     model.addAttribute("userName", userService.findByEmail(email).getUsername());
    //     model.addAttribute("files", fileService.getAllFiles());
    //     return "list-files";
    // }

        @GetMapping("/home")
    public String listFiles(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
            model.addAttribute("userName", userService.findByEmail(email).getUsername());
            model.addAttribute("files", fileService.getAllFiles(userDetails));
            return "list-files";
    }


     @PostMapping("/upload")
     public String uploadFile(@RequestParam("file") MultipartFile file,@AuthenticationPrincipal UserDetails userDetails,
             @RequestParam("uploadedBy") String uploadedBy) throws IOException {
         fileService.uploadFile(file, uploadedBy,userDetails);
         return "redirect:/files/home";
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
    public String deleteFile(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity<?> file = fileService.deleteFile(id,userDetails);
        if(file.hasBody()){
            return "redirect:/files/home";
        }
        else{
            return "redirect:/files";
        }
       
    }
}
