package com.mycompany.filesharing.model;

import lombok.Data;

@Data
public class UserInfoModel {
    private int id;
    private String username;
    private String password;    
    private String email;
}
