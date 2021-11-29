package com.group1.billsplitter.entities.exceptions;

public class NotFoundException extends RuntimeException{
    // Parameterless Constructor
    public NotFoundException() {}

    // Constructor that accepts a message
    public NotFoundException(String message)
    {
        super(message);
    }
}