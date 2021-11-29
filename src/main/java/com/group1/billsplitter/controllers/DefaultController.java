package com.group1.billsplitter.controllers;

import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.exceptions.NotFoundException;
import com.group1.billsplitter.entities.exceptions.UnauthorizedException;
import com.group1.billsplitter.entities.requests.Message;
import com.group1.billsplitter.services.FirebaseService;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@ControllerAdvice
@CrossOrigin
public class DefaultController {

    @ExceptionHandler({ MissingRequestHeaderException.class })
    protected ResponseEntity<Object> MissingRequestHeaderException(MissingRequestHeaderException exception) {
        System.out.println(exception.getHeaderName());
        if (exception.getHeaderName().equals("Authorization")) {
            return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> handleException(Exception exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidParameterException.class })
    protected ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ NotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UnauthorizedException.class })
    public ResponseEntity<Message> handleUnauthorizedException(UnauthorizedException exception){
        return new ResponseEntity<>(new Message(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
