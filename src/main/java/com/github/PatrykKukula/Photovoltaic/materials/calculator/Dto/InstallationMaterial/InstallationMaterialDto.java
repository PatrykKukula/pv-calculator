package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.InstallationMaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class InstallationMaterialDto {
    private String name;
    private Long quantity;
}
