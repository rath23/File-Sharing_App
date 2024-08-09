package com.mycompany.filesharing.configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mycompany.filesharing.entities.UserInfo;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;
    

    public CustomUserDetails(UserInfo userInfo) {
     email = userInfo.getEmail();
     password = userInfo.getPassword();
     authorities = null;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
    }

    @Override
    public String getPassword() {
       return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

  

}
