package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ConstructionMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ElectricalMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ConstructionMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ElectricalMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final ConstructionMaterialRepository constructionMaterialRepository;
    private final ElectricalMaterialRepository electricalMaterialRepository;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public InstallationMaterial createConstructionMaterial(String materialName, Long quantity, Installation installation){
        return InstallationMaterial.builder()
                .constructionMaterial((fetchConstructionMaterial(materialName)))
                .quantity(quantity)
                .installation(installation)
                .build();
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public InstallationMaterial createElectricalMaterial(String materialName, Long quantity){
        return InstallationMaterial.builder()
                .electricalMaterial(fetchElectricalMaterial(materialName))
                .quantity(quantity).build();
    }
    private ConstructionMaterial fetchConstructionMaterial(String name){
        return constructionMaterialRepository.fetchConstructionMaterialByName(name)
                .orElseThrow(() -> new RuntimeException("Material %s not found. Please contact administrator - this shouldn't happen".formatted(name)));
    }
    private ElectricalMaterial fetchElectricalMaterial(String name){
        return electricalMaterialRepository.fetchElectricalMaterialByName(name)
                .orElseThrow(() -> new RuntimeException("Material %s not found. Please contact administrator - this shouldn't happen".formatted(name)));
    }
}
