package com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstallationMaterialListToMapMapper {

    public Map<String, Long> createElectricalMaterialsMap(List<InstallationMaterial> materials){
        Map<String, Long> materialsMap = new HashMap<>();
        for (InstallationMaterial material : materials){
            Long value = materialsMap.getOrDefault(material.getElectricalMaterial().getName(), 0L);
            materialsMap.put(material.getElectricalMaterial().getName(), material.getQuantity() + value);
        }
        return materialsMap;
    }
    public Map<String, Long> createConstructionMaterialsMap(List<InstallationMaterial> materials){
        Map<String, Long> materialsMap = new HashMap<>();
        for (InstallationMaterial material : materials){
            Long value = materialsMap.getOrDefault(material.getConstructionMaterial().getName(), 0L);
            materialsMap.put(material.getConstructionMaterial().getName(), material.getQuantity() + value);
        }
        return materialsMap;
    }
}
