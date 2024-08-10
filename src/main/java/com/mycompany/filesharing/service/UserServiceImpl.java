package com.mycompany.filesharing.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.filesharing.entities.UserInfo;
import com.mycompany.filesharing.exception.UserEmailAlraedyExists;
import com.mycompany.filesharing.exception.UserEmailNotFound;
import com.mycompany.filesharing.model.UserInfoModel;
import com.mycompany.filesharing.repository.UserInfoRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
  

    @Override
    public void saveUser(UserInfoModel user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        Optional<UserInfo> option = userInfoRepository.findByEmail(user.getEmail());
        if (option.isPresent()) {
            throw new UserEmailAlraedyExists("Email already exists");
        }
        else{
        UserInfo entity = new UserInfo();
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        String randomCode = RandomString.make(64);

        entity.setVerificationCode(randomCode);
        entity.setEnabled(false);

        userInfoRepository.save(entity);
        sendVerificationEmail(entity, siteURL);
    }
    }

    private void sendVerificationEmail(UserInfo user, String siteURL)
        throws MessagingException, UnsupportedEncodingException {
    String toAddress = user.getEmail();
    String fromAddress = "filesharing1306@gmail.com";
    String senderName = "File Sharing";
    String subject = "Please verify your registration";
    String content = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Your company name.";
     
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
     
    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);
     
    content = content.replace("[[name]]", user.getUsername());
    String verifyURL = siteURL + "/files/verify?code=" + user.getVerificationCode();;
     
    content = content.replace("[[URL]]", verifyURL);
     
    helper.setText(content, true);
     
    mailSender.send(message);
     
}

    @Override
    public UserInfoModel findByEmail(String email) {
           Optional<UserInfo> option =  userInfoRepository.findByEmail(email);
        if(option.isEmpty()){
            throw new UserEmailNotFound("Email doesn't exist please check email.");
        }
        else {
            UserInfo entity = option.get();
            UserInfoModel userModel = new UserInfoModel();
            BeanUtils.copyProperties(entity, userModel);
            return userModel;
        }
    }

    @Override
    public boolean verify(String verificationCode) {
        UserInfo user = userInfoRepository.findByVerificationCode(verificationCode);
         
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userInfoRepository.save(user);
             
            return true;
        }
         
    }
    
}
