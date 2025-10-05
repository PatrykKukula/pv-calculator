package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
@Builder
public class ProjectUpdateDto {
    @Size(max = 255, message = "Project title cannot exceed 255 characters")
    private String title;
    @Size(max = 255, message = "Investor cannot exceed 255 characters")
    private String investor;
    @Size(max = 255, message = "Country cannot exceed 64 characters")
    private String country;
    @Size(max = 255, message = "Voivodeship cannot exceed 64 characters")
    private String voivodeship;
    @Size(max = 255, message = "City cannot exceed 64 characters")
    private String city;
    @Max(value = 800, message = "Module power cannot exceed 800")
    @Min(value = 350, message = "Module power cannot be less than 350")
    private Integer modulePower;
    @Min(value = 1600, message = "Module length cannot be less than 1600")
    @Max(value = 2500, message = "Module length cannot be more than 2500")
    private Integer moduleLength;
    @Min(value = 990, message = "Module width cannot be less than 990")
    @Max(value = 1400, message = "Module width cannot be more than 1400")
    private Integer moduleWidth;
    private Integer moduleFrame;
}
