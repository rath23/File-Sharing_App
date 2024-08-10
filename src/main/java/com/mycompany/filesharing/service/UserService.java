package com.mycompany.filesharing.service;

import java.io.UnsupportedEncodingException;

import com.mycompany.filesharing.model.UserInfoModel;

import jakarta.mail.MessagingException;

public interface UserService {
    void saveUser(UserInfoModel user, String siteURL)throws UnsupportedEncodingException, MessagingException;
    UserInfoModel findByEmail (String email);
    public boolean verify(String verificationCode);
}
