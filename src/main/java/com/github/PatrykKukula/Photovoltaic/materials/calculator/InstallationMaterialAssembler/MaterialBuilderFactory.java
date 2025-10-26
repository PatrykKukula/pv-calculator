package com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Electrical.ElectricalMaterialFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaterialBuilderFactory {
    private final MaterialService materialService;
    /*
        Factory to create material builders
     */
    public ConstructionMaterialAssembler createConstructionAssembler(Installation installation, ProjectDto project){
        ConstructionMaterialFactory constructionMaterialfactory = new ConstructionMaterialFactory(materialService, installation, project);
        return new ConstructionMaterialAssembler(installation, materialService, constructionMaterialfactory, project);
    }
    public ElectricalMaterialAssembler createElectricalAssembler(Installation installation, Long modulePower){
        ElectricalMaterialFactory electricalMaterialFactory = new ElectricalMaterialFactory(materialService, installation, modulePower);
        return new ElectricalMaterialAssembler(electricalMaterialFactory);
    }
}
