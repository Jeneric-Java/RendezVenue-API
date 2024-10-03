package com.Jeneric_Java.calendarappapi.exception;

public class NoResultsFoundException extends RuntimeException{
    public NoResultsFoundException(String message){
        super(message);
    }
}