package com.github.PatrykKukula.Photovoltaic.materials.calculator.Service;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.InstallationMaterial.InstallationMaterialDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.ConstructionMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ConstructionMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.ElectricalMaterialRepository;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Repository.InstallationMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaterialServiceTest {
    @Mock
    private ConstructionMaterialRepository constructionMaterialRepository;
    @Mock
    private ElectricalMaterialRepository electricalMaterialRepository;
    @Mock
    private InstallationMaterialRepository materialRepository;
    @InjectMocks
    private MaterialService materialService;
    private ConstructionMaterial constructionMaterial;

    @BeforeEach
    public void setUp(){
        constructionMaterial = ConstructionMaterial.builder()
                .name("construction")
                .materialId(1L)
                .build();
    }
    @Test
    @DisplayName("Should create construction material correctly")
    public void shouldCreateConstructionMaterialCorrectly(){
        when(constructionMaterialRepository.fetchConstructionMaterialByName(anyString())).thenReturn(Optional.of(constructionMaterial));

        InstallationMaterial createdMaterial = materialService.createConstructionMaterial("material", 1L, new Installation());

        assertEquals("construction", createdMaterial.getConstructionMaterial().getName());
        assertEquals(1, createdMaterial.getQuantity());
        assertNull(createdMaterial.getElectricalMaterial());
    }
    @Test
    @DisplayName("Should throw RuntimeException when create construction material and material not found")
    public void shouldThrowRuntimeExceptionWhenCreateConstructionMaterialAndMaterialNotFound(){
        when(constructionMaterialRepository.fetchConstructionMaterialByName(anyString())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> materialService.createConstructionMaterial("", 1L, new Installation()));
        assertTrue(ex.getMessage().contains("not found"));
    }
    @Test
    @DisplayName("Should fetch Construction Material for Installation correctly")
    public void shouldFetchConstructionMaterialForInstallationCorrectly(){
        when(materialRepository.fetchConstructionMaterialsForInstallation(anyLong())).thenReturn(List.of(InstallationMaterial.builder().constructionMaterial(constructionMaterial).build()));

        List<InstallationMaterialDto> materials = materialService.fetchConstructionMaterialsForInstallation(1L);

        assertEquals(1, materials.size());
        assertEquals("construction", materials.getFirst().getName());
    }
    @Test
    @DisplayName("Should return empty list when fetch Construction Materials for Installation and no materials found")
    public void shouldReturnEmptyListWhenFetchConstructionMaterialsForInstallationAndNoMaterialsFound(){
        when(materialRepository.fetchConstructionMaterialsForInstallation(anyLong())).thenReturn(Collections.emptyList());

        List<InstallationMaterialDto> materials = materialService.fetchConstructionMaterialsForInstallation(1L);

        assertEquals(0, materials.size());
    }
}
