package com.mycompany.filesharing.exception;

public class UserEmailAlraedyExists extends RuntimeException {
    public UserEmailAlraedyExists(String message) {
        super(message);
    }
}
