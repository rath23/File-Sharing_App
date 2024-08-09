package com.mycompany.filesharing.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mycompany.filesharing.entities.UserInfo;
import com.mycompany.filesharing.repository.UserInfoRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional <UserInfo> userInfo = userInfoRepository.findByEmail(email);
        if(userInfo.isPresent()){
            return new CustomUserDetails(userInfo.get());
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

}
