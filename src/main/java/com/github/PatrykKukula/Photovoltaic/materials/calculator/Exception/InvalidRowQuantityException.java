package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class InvalidRowQuantityException extends AppException {
    public InvalidRowQuantityException(Long moduleQuantity) {
        super("Each row should have at least 5 modules", "Invalid module quantity in a row: " + moduleQuantity);
    }
}
