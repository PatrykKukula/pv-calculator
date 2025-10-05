package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super("%s not found for ID: %s".formatted(resource, id));
    }
}
