package com.task_pay.task_pay.exceptions;

public class BadApiRequestException extends  RuntimeException{
    public BadApiRequestException(String message){
        super(message);
    }
    public BadApiRequestException(){
        super("Bad Request");
    }
}
