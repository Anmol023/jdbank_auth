package com.xyz.jdbnk.exception;

public class UserAlreadyExistsException extends Exception {

    private static final long serialVersionUID = -7634767090481356066L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
