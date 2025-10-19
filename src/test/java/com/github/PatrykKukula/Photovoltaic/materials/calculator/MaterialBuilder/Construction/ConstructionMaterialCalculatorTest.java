package com.github.PatrykKukula.Photovoltaic.materials.calculator.MaterialBuilder.Construction;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project.ProjectDto;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.*;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConstructionMaterialCalculatorTest {
    @Mock
    private MaterialService materialService;
    private Installation installation;
    private ProjectDto projectDto;
    private ConstructionMaterialCalculator constructionMaterialCalculator;

    @BeforeEach
    public void setUp() {
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
                .project(new Project())
                .materials(Collections.emptyList())
                .rows(List.of(Row.builder().rowNumber(1L).moduleQuantity(10L).build()))
                .build();
        constructionMaterialCalculator = new ConstructionMaterialCalculator(materialService, installation, projectDto);
    }

    @Test
    @DisplayName("Should set profile correctly")
    public void shouldSetProfileCorrectly() {
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenAnswer(invocation -> {
            String name = invocation.getArgument(0);
            Long qty = invocation.getArgument(1);
            return InstallationMaterial.builder()
                    .constructionMaterial(ConstructionMaterial.builder().name(name).build())
                    .quantity(qty)
                    .build();
        });

        InstallationMaterial material = constructionMaterialCalculator.setProfile();

        verify(materialService).createConstructionMaterial(eq("Aluminium profile 40x40"), eq(21L), eq(installation));
        assertEquals(21, material.getQuantity(), 0);
        assertEquals("Aluminium profile 40x40", material.getConstructionMaterial().getName());
    }
    @Test
    @DisplayName("Should set profile joiner correctly")
    public void shouldSetProfileJoinerCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setProfileJoiner(12000);

        verify(materialService).createConstructionMaterial(eq("Profile joiner"), eq(2L), eq(installation));
    }
    @Test
    @DisplayName("Should set hexagon screw correctly")
    public void shouldSetHexagonScrewCorrectly(){
        installation.setInstallationType(ConstructionType.DOUBLE_THREADED_SCREW_OBLIQUE);
        when(materialService.createConstructionMaterial(anyString(), anyLong(), eq(installation))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setHexagonScrew(12000);

        verify(materialService).createConstructionMaterial(eq("Hexagon screw M10x250"), anyLong(), eq(installation));
    }
    @Test
    @DisplayName("Should throw RuntimeException when set hexagon screw with Trapeze installation")
    public void shouldThrowRuntimeExceptionWhenSetHexagonScrewWithTrapezeInstallation(){
        RuntimeException ex = assertThrows(RuntimeException.class, () -> constructionMaterialCalculator.setHexagonScrew(12000));
        assertEquals("Error during calculating hexagon screws. This shouldn't happen - please contact administrator", ex.getMessage());
    }
    @Test
    @DisplayName("Should set hexagon nu correctly")
    public void shouldSetHexagonNutCorrectly(){
        installation.setInstallationType(ConstructionType.DOUBLE_THREADED_SCREW_OBLIQUE);
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setHexagonNut(12000);

        verify(materialService).createConstructionMaterial(eq("Hexagon nut M10"), anyLong(), eq(installation));
    }
    @Test
    @DisplayName("Should set Vario Hook correctly")
    public void shouldSetVarioHookCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setVarioHook(10L);

        verify(materialService).createConstructionMaterial(eq("Vario hook"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should set double headed screw correctly")
    public void shouldSetDoubleHeadedScrewCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setDoubleThreadedScrew(10L);

        verify(materialService).createConstructionMaterial(eq("Double threaded screw L=250mm"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should set adapter oblique correctly")
    public void shouldSetAdapterObliqueCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setAdapterOblique(10L);

        verify(materialService).createConstructionMaterial(eq("Adapter"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should set screws for vario hook correctly")
    public void shouldSetScrewsForVarioHookCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setScrewsForVarioHook(10L);

        verify(materialService).createConstructionMaterial(eq("Screws for vario hook"), eq(40L), eq(installation));
    }
    @Test
    @DisplayName("Should set trapeze correctly")
    public void shouldSetSTrapezeCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setTrapeze();

        verify(materialService).createConstructionMaterial(eq("Trapeze"), eq(22L), eq(installation));
    }
    @Test
    @DisplayName("Should set screws for trapeze correctly")
    public void shouldSetScrewsForTrapezeCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setScrewsForTrapeze(10L);

        verify(materialService).createConstructionMaterial(eq("Trapeze screws"), eq(60L), eq(installation));
    }
    @Test
    @DisplayName("Should set angle bar correctly")
    public void shouldSetAngleBarCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setAngleBar();

        verify(materialService).createConstructionMaterial(eq("Aluminium angle bar 40x3"), eq(39L), eq(installation));
    }
    @Test
    @DisplayName("Should set threaded rod correctly")
    public void shouldSetThreadedRodCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setThreadedRod();

        verify(materialService).createConstructionMaterial(eq("Threaded rod M10"), eq(22L), eq(installation));
    }
    @Test
    @DisplayName("Should set EPDM correctly")
    public void shouldSetEpdmCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setEpdm(10L);

        verify(materialService).createConstructionMaterial(eq("EPDM M10"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should set chemical anchor correctly")
    public void shouldSetChemicalAnchorCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setChemicalAnchor(10L);

        verify(materialService).createConstructionMaterial(eq("Chemical anchor"), eq(2L), eq(installation));
    }
    @Test
    @DisplayName("Should set sleeve correctly")
    public void shouldSetSleeveCorrectly(){
        when(materialService.createConstructionMaterial(anyString(), anyLong(), any(Installation.class))).thenReturn(new InstallationMaterial());

        constructionMaterialCalculator.setSleeve(10L);

        verify(materialService).createConstructionMaterial(eq("Sleeve for threaded rod"), eq(10L), eq(installation));
    }
    @Test
    @DisplayName("Should calculate end clamps correctly")
    public void shouldCalculateEndClampsCorrectly(){
        long endClamps = constructionMaterialCalculator.calculateEndClamps();

        assertEquals(4, endClamps);
    }
    @Test
    @DisplayName("Should calculate mid clamps correctly")
    public void shouldCalculateMidClampsCorrectly(){
        long midClamps = constructionMaterialCalculator.calculateMidClamps();

        assertEquals(18, midClamps);
    }
    @Test
    @DisplayName("Should calculate edge material correctly")
    public void shouldCalculateEdgeMaterialCorrectly(){
        long edgeMaterial = constructionMaterialCalculator.calculateEdgeMaterial();

        assertEquals(22, edgeMaterial);
    }
}
