package com.example.slotify_backend.exception;

public class UploadFailedException extends RuntimeException {
    public UploadFailedException(String message) {
        super(message);
    }
    public UploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
