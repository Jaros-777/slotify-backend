package com.example.slotify_backend.exception;

public class EventAlreadyBookedException extends RuntimeException {
    public EventAlreadyBookedException(String message) {
        super(message);
    }
}
