package com.xyz.jdbnk.exception;

public class InvalidLicense extends Exception{
    private static final long serialVersionUID = -7634767090481356066L;

    public InvalidLicense(String message) {
        super(message);
    }
}
