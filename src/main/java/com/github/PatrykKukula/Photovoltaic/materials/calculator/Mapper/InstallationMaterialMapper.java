package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.InstallationMaterial.InstallationMaterialDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;

public class InstallationMaterialMapper {
    private static final String[] metersUnit = {"cable", "profile"};
    private static final String UNIT_PCS = " pcs.";
    private static final String UNIT_METER = " m";
    private InstallationMaterialMapper(){}

    public static InstallationMaterialDto mapInstallationMaterialToInstallationMaterialDto(InstallationMaterial material){
        String materialName;
        String unit = UNIT_PCS;
        if(material.getElectricalMaterial() != null) materialName = material.getElectricalMaterial().getName();
        else materialName = material.getConstructionMaterial().getName();
        for (String name : metersUnit) {
            if (materialName.contains(name)) {
                unit = UNIT_METER;
                break;
            }
        }
        return InstallationMaterialDto.builder()
                .name(material.getConstructionMaterial() != null ? material.getConstructionMaterial().getName() : material.getElectricalMaterial().getName())
                .quantity(material.getQuantity())
                .unit(unit)
                .build();
    }
}
