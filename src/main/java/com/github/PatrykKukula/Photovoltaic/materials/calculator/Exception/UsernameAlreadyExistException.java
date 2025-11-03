package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class UsernameAlreadyExistException extends AppException {
    public UsernameAlreadyExistException(String username){
        super("Username already exists", "Registration attempt with username %s and username already exists".formatted(username));
    }
}
