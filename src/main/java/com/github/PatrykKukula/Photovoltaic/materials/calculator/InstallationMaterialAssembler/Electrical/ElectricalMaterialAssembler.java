package com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;

import java.util.ArrayList;
import java.util.List;

public class ElectricalMaterialAssembler {
    private final ElectricalMaterialFactory electricalMaterialFactory;

    public ElectricalMaterialAssembler(ElectricalMaterialFactory electricalMaterialFactory){
        this.electricalMaterialFactory = electricalMaterialFactory;
    }
    public List<InstallationMaterial> createInstallationElectricalMaterials(){
        List<InstallationMaterial> materials = new ArrayList<>();
        materials.add(electricalMaterialFactory.createPhotovoltaicModule());
        materials.add(electricalMaterialFactory.createInverter());
        materials.add(electricalMaterialFactory.createDcSwitchboard());
        materials.add(electricalMaterialFactory.createAcSwitchboard());
        materials.add(electricalMaterialFactory.createAcCable());
        materials.add(electricalMaterialFactory.createDcCable());
        materials.add(electricalMaterialFactory.createDcFuse());
        materials.add(electricalMaterialFactory.createDcFuseHolder());
        materials.add(electricalMaterialFactory.createDcSurgeArresters());
        materials.add(electricalMaterialFactory.createAcSurgeArrester());
        materials.add(electricalMaterialFactory.createLgyCable());
        materials.add(electricalMaterialFactory.createDifferentialCircuitBreaker());
        materials.add(electricalMaterialFactory.createOvercurrentCircuitBreakerB());
        materials.add(electricalMaterialFactory.createOvercurrentCircuitBreakerC());
        return materials;
    }


}
