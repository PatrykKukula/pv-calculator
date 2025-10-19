package com.github.PatrykKukula.Photovoltaic.materials.calculator.Mapper;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.InstallationMaterial.InstallationMaterialDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ConstructionMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ElectricalMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstallationMaterialMapperTest {
    private InstallationMaterial installationMaterial;

    @BeforeEach
    public void setUp(){
        installationMaterial = InstallationMaterial.builder()
                .quantity(10L)
                .constructionMaterial(ConstructionMaterial.builder().name("construction material").build())
                .build();
    }
    @Test
    @DisplayName("Should map Installation material to Installation material dto correctly with construction material")
    public void shouldMapInstallationMaterialToInstallationMaterialDtoCorrectlyWithConstructionMaterialCorrectly(){
        InstallationMaterialDto mappedMaterial = InstallationMaterialMapper.mapInstallationMaterialToInstallationMaterialDto(installationMaterial);

        assertEquals(10, mappedMaterial.getQuantity());
        assertEquals("construction material", mappedMaterial.getName());
    }
    @Test
    @DisplayName("Should map Installation material to Installation material dto correctly with electrical material")
    public void shouldMapInstallationMaterialToInstallationMaterialDtoCorrectlyWithElectricalMaterialCorrectly(){
        installationMaterial.setConstructionMaterial(null);
        installationMaterial.setElectricalMaterial(ElectricalMaterial.builder().name("electrical material").build());
        InstallationMaterialDto mappedMaterial = InstallationMaterialMapper.mapInstallationMaterialToInstallationMaterialDto(installationMaterial);

        assertEquals(10, mappedMaterial.getQuantity());
        assertEquals("electrical material", mappedMaterial.getName());
    }

}
