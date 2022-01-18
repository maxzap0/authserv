package com.maxzap.authserv.exception;

public class LoginException extends RuntimeException{
    public LoginException(String msg){
        super(msg);
    }
}
