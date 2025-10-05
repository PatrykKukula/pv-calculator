package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("ID cannot be less than 1");
    }
}
