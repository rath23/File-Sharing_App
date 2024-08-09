package com.mycompany.filesharing.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.filesharing.entities.UserInfo;
import com.mycompany.filesharing.exception.UserEmailNotFound;
import com.mycompany.filesharing.model.UserInfoModel;
import com.mycompany.filesharing.repository.UserInfoRepository;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
  

    @Override
    public void saveUser(UserInfoModel user) {
        UserInfo entity = new UserInfo();
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        userInfoRepository.save(entity);
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
    
}
