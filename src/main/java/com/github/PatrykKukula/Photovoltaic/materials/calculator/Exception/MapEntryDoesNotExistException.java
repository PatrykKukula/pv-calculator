package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class MapEntryDoesNotExistException extends AppException{
    public MapEntryDoesNotExistException(String logMessage) {
        super("Error creating materials list, please try again. If error occurs again, please contact administrator.",
                "Error creating material list - null in navigable map" + logMessage);
    }
}
