package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.RegexConstants.PASSWORD_REGEX;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.RegexConstants.USERNAME_REGEX;

@AllArgsConstructor @Builder
@NoArgsConstructor
@Data
public class RegisterDto {
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 characters")
    @Pattern(regexp = USERNAME_REGEX, message = "Username can only contains letters and numbers")
    private String username;
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "Password must contain small and capital letter, number and special character of the following: [!@#$%^&*()\\-+]")
    private String password;
}
