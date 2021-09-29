package com.example.blog.exception;

public class ConfigNotFoundException extends Exception {

    private String funcName;

    public ConfigNotFoundException(String funcName, String message) {
        super(message);
        this.funcName = funcName;
    }

    public String getFuncName() {
        return funcName;
    }
}
