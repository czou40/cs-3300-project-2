package com.group1.billsplitter.entities.exceptions;

public class UnauthorizedException extends RuntimeException{
    // Parameterless Constructor
    public UnauthorizedException() {}

    // Constructor that accepts a message
    public UnauthorizedException(String message)
    {
        super(message);
    }
}
