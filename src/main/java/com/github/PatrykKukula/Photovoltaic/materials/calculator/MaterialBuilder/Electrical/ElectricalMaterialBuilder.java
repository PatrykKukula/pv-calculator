package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Electrical;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Supplier;

public class ElectricalMaterialBuilder {
    private final NavigableMap<Integer, Supplier<List<InstallationMaterial>>> strategyMap = new TreeMap<>();

    public ElectricalMaterialBuilder (){
        strategyMap.put(3, strategy3kW());
    }
    public Supplier<List<InstallationMaterial>> strategy3kW(){
        return () -> {
            List<InstallationMaterial> list = new ArrayList<>();
            return list;
        };
    }

}
