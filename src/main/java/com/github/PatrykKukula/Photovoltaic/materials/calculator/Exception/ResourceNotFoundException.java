package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String resource, Long id) {
        super("Resource not found: " + resource, "%s not found for ID: %s".formatted(resource, id));
    }
}
