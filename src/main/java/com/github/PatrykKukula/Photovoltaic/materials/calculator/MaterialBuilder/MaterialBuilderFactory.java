package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialBuilder;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction.ConstructionMaterialCalculator;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaterialBuilderFactory {
    private final MaterialService materialService;

    public ConstructionMaterialBuilder createConstructionBuilder(Installation installation, Project project){
        ConstructionMaterialCalculator constructionMaterialCalculator = new ConstructionMaterialCalculator(materialService, installation, project);
        return new ConstructionMaterialBuilder(installation, materialService, constructionMaterialCalculator, project);
    }
}
