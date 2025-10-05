package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class InvalidOwnershipException extends RuntimeException {
    public InvalidOwnershipException(String resource, String username) {
        super("%s doesn't belong to current user: %s");
    }
}
