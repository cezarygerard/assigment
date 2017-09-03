package com.cgz.dto;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long id) {
        super("User not found, id:" + id, null, false, false);
    }
}
