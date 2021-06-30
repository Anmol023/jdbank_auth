package com.xyz.jdbnk.exception;

public class AwsSesClientException extends RuntimeException {

    /**
     * Aws Client Exception with error message and throwable
     *
     * @param errorMessage error message
     * @param throwable    error
     */
    public AwsSesClientException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

}