package com.whatsapp.userservice.exception;

public class ContactAlreadyExistsException extends RuntimeException {

    public ContactAlreadyExistsException(String message) {
        super(message);
    }
}
