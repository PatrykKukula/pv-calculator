package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class InvalidIdException extends AppException {
    public InvalidIdException(Long id) {
        super("Invalid ID", "ID cannot be less than 1 but was: "+ id);
    }
}
