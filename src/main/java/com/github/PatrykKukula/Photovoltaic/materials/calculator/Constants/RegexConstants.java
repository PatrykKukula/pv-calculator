package com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants;

public class RegexConstants {
    private RegexConstants(){}

    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]*{3,64}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-+]).{8,}$";
}
