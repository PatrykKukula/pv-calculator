package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor @Builder
@Data
public class InstallationDto {
    /*
         VALUES ARE BIT BELOW LOWEST AND HIGHEST POSSIBLE VALUES ON MARKET FOR GIVEN PARAMETER
     */
    @NotEmpty(message = "Installation type cannot be empty")
    private ConstructionType installationType;
    @Min(value = 350, message = "Module power cannot be less then 350")
    @NotNull(message = "Pick module orientation [VERTICAL/HORIZONTAL]")
    private ModuleOrientation moduleOrientation;
    private List<Row> rows;
}
