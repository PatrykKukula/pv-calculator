package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Data
public class InstallationUpdateDto implements InstallationInterface {
    private Long projectId;
    private Long installationId;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "Pick installation type")
    private ConstructionType constructionType;
    @NotNull(message = "Pick module orientation")
    private ModuleOrientation moduleOrientation;
    @NotNull(message = "Pick phase number")
    private PhaseNumber phaseNumber;
    @NotNull(message = "Specify number of DC strings")
    private Long strings;
    private List<RowDto> rows;
    @NotNull(message = "Choose whether object has lighting protection installation")
    private boolean lightingProtection;
    @NotNull(message = "Specify length from inverter to electric switchboard")
    @Min(value = 1, message = "AC cable cannot be less then 1")
    private Integer acCableLength;
    @NotNull(message = "Specify length from inverter to the first row of modules")
    @Min(value = 1, message = "DC cable cannot be less then 1")
    private Integer dcCableLength;
    @NotNull(message = "Specify length from inverter to the first row of modules")
    @Min(value = 1, message = "LgY cable cannot be less then 1")
    private Integer lgyCableLength;
}
