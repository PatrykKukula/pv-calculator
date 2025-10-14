package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public abstract class AppException extends RuntimeException {
    private final String userMessage;
    protected AppException(String userMessage, String logMessage) {
        super(logMessage);
        this.userMessage = userMessage;
    }
    public String getUserMessage(){
        return userMessage;
    }
}
