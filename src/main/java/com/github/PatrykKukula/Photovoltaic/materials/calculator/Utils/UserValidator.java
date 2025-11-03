package com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.RegexConstants;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User.RegisterDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserValidator {
    public void validateRegisterData(RegisterDto registerDto){
        if (!registerDto.getUsername().matches(RegexConstants.USERNAME_REGEX)) throw new IllegalArgumentException("Username must contain only alphanumeric characters and be between 3-64 characters long");
        if (!registerDto.getPassword().matches(RegexConstants.PASSWORD_REGEX)) throw new IllegalArgumentException("Password must contain 1 capital and small letter, number, special character and be at least" +
                "8 character long");
    }
}
