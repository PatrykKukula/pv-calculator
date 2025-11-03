package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class InvalidOwnershipException extends AppException {
    public InvalidOwnershipException(String resource, String username) {
        super("You are not owner of this resource", "%s doesn't belong to current user: %s".formatted(resource, username));
    }
}
