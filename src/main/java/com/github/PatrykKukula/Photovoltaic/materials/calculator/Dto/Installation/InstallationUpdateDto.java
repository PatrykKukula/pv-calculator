package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Builder
@Data
public class InstallationUpdateDto {
    private ConstructionType installationType;
    private ModuleOrientation moduleOrientation;
    private List<Row> rows;
}
