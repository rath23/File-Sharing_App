package com.mycompany.filesharing.service;

import com.mycompany.filesharing.model.UserInfoModel;

public interface UserService {
    void saveUser(UserInfoModel user);
    UserInfoModel findByEmail (String email);
}
