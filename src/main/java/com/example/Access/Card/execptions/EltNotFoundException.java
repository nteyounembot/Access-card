package com.example.Access.Card.execptions;


public class EltNotFoundException extends RuntimeException {
    public EltNotFoundException(String message) {
        super(message);
    }
}