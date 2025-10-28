package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class PowerOutOfBoundsException extends AppException{

    public PowerOutOfBoundsException(String allowedPower, String totalPower, String username) {
        super(("Total power cannot exceed %s kW and is: " + totalPower).formatted(allowedPower), "Total power exceed while creating installation: %s by %s".formatted(totalPower, username));
    }
}
