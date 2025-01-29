package com.example.exception;

public class TransactionFailedException extends IllegalStateException{
    public TransactionFailedException(String message){
        super(message);
    }
}
