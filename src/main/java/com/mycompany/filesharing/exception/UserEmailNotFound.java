package com.mycompany.filesharing.exception;

public class UserEmailNotFound extends  RuntimeException{
    public UserEmailNotFound(String message) {
        super(message);
    }
}