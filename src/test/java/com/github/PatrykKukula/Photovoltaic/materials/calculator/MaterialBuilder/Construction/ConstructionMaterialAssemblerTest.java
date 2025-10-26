package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialAssembler;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.InstallationMaterialAssembler.Construction.ConstructionMaterialFactory;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.InstallationMaterial;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Row;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConstructionMaterialAssemblerTest {
    @Mock
    private MaterialService materialService;
    @Mock
    private ConstructionMaterialFactory constructionMaterialCalculator;
    private ConstructionMaterialAssembler constructionMaterialBuilder;
    private Installation installation;
    private Project project;
    private ProjectDto projectDto;

    @BeforeEach
    public void setUp(){
        project = Project.builder()
                .title("project")
                .investor("investor")
                .country("country")
                .voivodeship("voivodeship")
                .city("city")
                .modulePower(500)
                .moduleFrame(30)
                .moduleLength(2000)
                .moduleWidth(1000)
                .build();
        projectDto = ProjectDto.builder()
                .title("project")
                .investor("investor")
                .country("country")
                .voivodeship("voivodeship")
                .city("city")
                .modulePower(500)
                .moduleFrame(30)
                .moduleLength(2000)
                .moduleWidth(1000)
                .build();
        installation = Installation.builder()
                .installationType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .project(project)
                .materials(Collections.emptyList())
                .rows(List.of(Row.builder().rowNumber(1L).moduleQuantity(10L).build()))
                .build();
        constructionMaterialBuilder = new ConstructionMaterialAssembler(installation, materialService, constructionMaterialCalculator, projectDto);
        mockCommonMaterials();
    }
    @Test
    @DisplayName("Should build trapeze installation with expected materials")
    public void shouldBuildTrapezeInstallationWithExpectedMaterials(){
        when(constructionMaterialCalculator.setTrapeze()).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setScrewsForTrapeze(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());

        List<InstallationMaterial> materials = constructionMaterialBuilder.createInstallationConstructionMaterials();

        assertEquals(6, materials.size());
        verify(constructionMaterialCalculator, times(1)).setTrapeze();
        verify(constructionMaterialCalculator, times(1)).setScrewsForTrapeze(anyLong());
        verify(materialService,times(1)).createConstructionMaterial(eq("End clamp 30 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Mid clamp"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Allen screw 20 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Sliding key"), anyLong(), eq(installation));
    }
    @Test
    @DisplayName("Should build vario hook installation with expected materials")
    public void shouldBuildVarioHookInstallationWithExpectedMaterials(){
        installation.setInstallationType(ConstructionType.VARIO_HOOK);
        mockProfileBasedMaterials();
        when(constructionMaterialCalculator.setVarioHook(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setScrewsForVarioHook(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());

        List<InstallationMaterial> materials = constructionMaterialBuilder.createInstallationConstructionMaterials();

        assertEquals(10, materials.size());
        verify(constructionMaterialCalculator, times(1)).setVarioHook(anyLong());
        verify(constructionMaterialCalculator, times(1)).setScrewsForVarioHook(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfileJoiner(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfile();
        verify(constructionMaterialCalculator, times(1)).setHexagonScrew(anyLong());
        verify(constructionMaterialCalculator, times(1)).setHexagonNut(anyLong());
        verify(materialService,times(1)).createConstructionMaterial(eq("End clamp 30 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Mid clamp"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Allen screw 20 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Sliding key"), anyLong(), eq(installation));
    }
    @Test
    @DisplayName("Should build double threaded screw oblique installation with expected materials")
    public void shouldBuildDoubleThreadedScrewObliqueInstallationWithExpectedMaterials(){
        installation.setInstallationType(ConstructionType.DOUBLE_THREADED_SCREW_OBLIQUE);
        mockProfileBasedMaterials();
        when(constructionMaterialCalculator.setDoubleThreadedScrew(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setAdapterOblique(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());

        List<InstallationMaterial> materials = constructionMaterialBuilder.createInstallationConstructionMaterials();

        assertEquals(10, materials.size());
        verify(constructionMaterialCalculator, times(1)).setDoubleThreadedScrew(anyLong());
        verify(constructionMaterialCalculator, times(1)).setAdapterOblique(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfileJoiner(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfile();
        verify(constructionMaterialCalculator, times(1)).setHexagonScrew(anyLong());
        verify(constructionMaterialCalculator, times(1)).setHexagonNut(anyLong());
        verify(materialService,times(1)).createConstructionMaterial(eq("End clamp 30 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Mid clamp"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Allen screw 20 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Sliding key"), anyLong(), eq(installation));
    }
    @Test
    @DisplayName("Should build double threaded screw flat installation with expected materials")
    public void shouldBuildDoubleThreadedScrewFlatInstallationWithExpectedMaterials(){
        installation.setInstallationType(ConstructionType.DOUBLE_THREADED_SCREW_FLAT);
        mockProfileBasedMaterials();
        when(constructionMaterialCalculator.setDoubleThreadedScrew(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setAdapterOblique(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setAngleBar()).thenReturn(InstallationMaterial.builder().quantity(10L).build());

        List<InstallationMaterial> materials = constructionMaterialBuilder.createInstallationConstructionMaterials();

        assertEquals(11, materials.size());
        verify(constructionMaterialCalculator, times(1)).setDoubleThreadedScrew(anyLong());
        verify(constructionMaterialCalculator, times(1)).setAdapterOblique(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfileJoiner(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfile();
        verify(constructionMaterialCalculator, times(1)).setHexagonScrew(anyLong());
        verify(constructionMaterialCalculator, times(1)).setHexagonNut(anyLong());
        verify(constructionMaterialCalculator, times(1)).setAngleBar();
        verify(materialService,times(1)).createConstructionMaterial(eq("End clamp 30 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Mid clamp"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Allen screw 20 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Sliding key"), anyLong(), eq(installation));
    }
    @Test
    @DisplayName("Should build threaded rod flat installation with expected materials")
    public void shouldBuildThreadedRodFlatInstallationWithExceptedMaterials(){
        installation.setInstallationType(ConstructionType.DOUBLE_THREADED_ROD);
        mockProfileBasedMaterials();
        when(constructionMaterialCalculator.setThreadedRod()).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setEpdm(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setAngleBar()).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setSleeve(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setChemicalAnchor(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());

        List<InstallationMaterial> materials = constructionMaterialBuilder.createInstallationConstructionMaterials();

        assertEquals(13, materials.size());
        verify(constructionMaterialCalculator, times(1)).setThreadedRod();
        verify(constructionMaterialCalculator, times(1)).setEpdm(anyLong());
        verify(constructionMaterialCalculator, times(1)).setSleeve(anyLong());
        verify(constructionMaterialCalculator, times(1)).setChemicalAnchor(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfileJoiner(anyLong());
        verify(constructionMaterialCalculator, times(1)).setProfile();
        verify(constructionMaterialCalculator, times(1)).setHexagonScrew(anyLong());
        verify(constructionMaterialCalculator, times(1)).setHexagonNut(anyLong());
        verify(constructionMaterialCalculator, times(1)).setAngleBar();
        verify(materialService,times(1)).createConstructionMaterial(eq("End clamp 30 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Mid clamp"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Allen screw 20 mm"), anyLong(), eq(installation));
        verify(materialService,times(1)).createConstructionMaterial(eq("Sliding key"), anyLong(), eq(installation));
    }
    private void mockCommonMaterials(){
        when(materialService.createConstructionMaterial(eq("End clamp 30 mm"), anyLong(), eq(installation))).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(materialService.createConstructionMaterial(eq("Mid clamp"), anyLong(), eq(installation))).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(materialService.createConstructionMaterial(eq("Allen screw 20 mm"), anyLong(), eq(installation))).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(materialService.createConstructionMaterial(eq("Sliding key"), anyLong(), eq(installation))).thenReturn(InstallationMaterial.builder().quantity(10L).build());
    }
    private void mockProfileBasedMaterials(){
        when(constructionMaterialCalculator.setProfile()).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setProfileJoiner(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setHexagonScrew(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());
        when(constructionMaterialCalculator.setHexagonNut(anyLong())).thenReturn(InstallationMaterial.builder().quantity(10L).build());

    }
}
