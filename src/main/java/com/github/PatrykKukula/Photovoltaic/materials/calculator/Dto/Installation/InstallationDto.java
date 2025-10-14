package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @Builder
@NoArgsConstructor
@Data
public class InstallationDto {
    /*
         VALUES ARE BIT BELOW LOWEST AND HIGHEST POSSIBLE VALUES ON MARKET FOR GIVEN PARAMETER
     */
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "Pick installation type")
    private ConstructionType installationType;
    @NotNull(message = "Pick module orientation")
    private ModuleOrientation moduleOrientation;
    private List<RowDto> rows;
    @NotNull(message = "AC cable cannot by empty")
    @Min(value = 1, message = "AC cable cannot be less then 1")
    private Integer acCableLength;
    @NotNull(message = "DC cable cannot by empty")
    @Min(value = 1, message = "DC cable cannot be less then 1")
    private Integer dcCableLength;
}
