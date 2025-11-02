package com.github.PatrykKukula.Photovoltaic.materials.calculator.Files;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ConstructionType;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.ModuleOrientation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Constants.PhaseNumber;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
public class MaterialExporterTest {
    private List<Installation> installations = new ArrayList<>();
    private Installation installation;
    private Map<String, Long> constructuionMaterials = new HashMap<>();
    private Map<String, Long> electricalMaterials = new HashMap<>();
    private MaterialsExporter materialsExporter = new MaterialsExporter();
    private Project project;

    @BeforeEach
    public void setUp(){
        installation = Installation.builder()
                .installationId(1L)
                .address("address")
                .installationType(ConstructionType.TRAPEZE)
                .moduleOrientation(ModuleOrientation.VERTICAL)
                .lightingProtection(true)
                .acCableLength(10)
                .dcCableLength(20)
                .lgyCableLength(30)
                .rows(new ArrayList<>())
                .materials(new ArrayList<>())
                .phaseNumber(PhaseNumber.THREE_PHASE)
                .project(Project.builder().projectId(1L).build())
                .build();
        project = Project.builder().title("title").investor("investor").modulePower(500).build();
    }

    @Test
    @DisplayName("Should create excel file correctly when export materials to excel for installation")
    public void shouldCreateExcelFileCorrectlyWhenExportMaterialsToExcelForInstallation() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = materialsExporter.exportMaterialsToExcelForInstallation(installation, constructuionMaterials, electricalMaterials);

        assertTrue(byteArrayOutputStream.size() > 0);
    }
    @Test
    @DisplayName("Should create excel file correctly when export materials to excel for project")
    public void shouldCreateExcelFileCorrectlyWhenExportMaterialsToExcelForProject() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = materialsExporter.exportMaterialsToExcelForProject(installations, project, constructuionMaterials, electricalMaterials, 1, 100.0);

        assertTrue(byteArrayOutputStream.size() > 0);
    }

}
