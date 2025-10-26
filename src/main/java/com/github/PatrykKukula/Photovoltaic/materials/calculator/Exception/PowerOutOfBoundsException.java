package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class PowerOutOfBoundsException extends AppException{

    public PowerOutOfBoundsException(String totalPower, String username) {
        super("Total power cannot exceed 49.995 kW and is: " + totalPower, "Total power exceed while creating installation: %s by %s".formatted(totalPower, username));
    }
}
