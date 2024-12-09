package com.survey.exceptions;

public class ServiceLogicException extends Exception{
    public ServiceLogicException() {
        super("Something went wrong. Please try again later!");
    }
}
