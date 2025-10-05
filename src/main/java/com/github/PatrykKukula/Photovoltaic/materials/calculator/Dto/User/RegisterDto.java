package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.RegexConstants.PASSWORD_REGEX;
import static com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.RegexConstants.USERNAME_REGEX;

@AllArgsConstructor @Builder
@Data
public class RegisterDto {
    @NotEmpty(message = "Username cannot be empty")
    @Size(max = 32, message = "Username cannot exceed 32 characters")
    @Pattern(regexp = USERNAME_REGEX, message = "Username can only contains letters and numbers")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    @Pattern(regexp = PASSWORD_REGEX,
            message = "Password must contain small and capital letter, number and special character of the following: [!@#$%^&*()\\-+]")
    private String password;
}
