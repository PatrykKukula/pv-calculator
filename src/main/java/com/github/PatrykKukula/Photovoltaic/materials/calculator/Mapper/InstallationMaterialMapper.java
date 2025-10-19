package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.InstallationMaterial.InstallationMaterialDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;

public class InstallationMaterialMapper {
    private InstallationMaterialMapper(){}

    public static InstallationMaterialDto mapInstallationMaterialToInstallationMaterialDto(InstallationMaterial material){
        return InstallationMaterialDto.builder()
                .name(material.getConstructionMaterial() != null ? material.getConstructionMaterial().getName() : material.getElectricalMaterial().getName())
                .quantity(material.getQuantity())
                .build();
    }
}
