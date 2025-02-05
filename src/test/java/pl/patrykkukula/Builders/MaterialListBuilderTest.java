package pl.patrykkukula.Builders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.ConstructionModel.Trapeze;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.TestInstallation;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialListBuilderTest {
    private MaterialListBuilder builder;
    private InstallationList installationList;
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @BeforeEach
    void setUp() {
        installationList = new InstallationList();
        builder = new MaterialListBuilder(installationList);
    }

    @Test
    public void shouldThrowRuntimeExceptionIfMaterialListIsEmpty() {
        RuntimeException ex = assertThrows(RuntimeException.class, builder::printMaterialList);
        assertEquals("Lista instalacji jest pusta", ex.getMessage());
    }
    @Test
    public void shouldCreateMaterialListBuilderWithValidInstallations() {assertEquals(installationList, builder.getInstallationList());
    }
    @Test
    public void shouldPrintMaterialListForSingleInstallationCorrectly(){
        Map<String, Integer> actualMaterials = setTestMaterialList();
        assertNotNull(actualMaterials);
        assertFalse(actualMaterials.isEmpty());
        assertTrue(actualMaterials.containsKey("Mostek trapezowy") && actualMaterials.containsKey("Wkręt farmerski"));
    }
    @Test
    public void shouldAddMaterialsForMultipleInstallationsCorrectly(){
        Map<String, Integer> actualMaterials = setTestMaterialListForMultipleInstallations();
        for (String key : actualMaterials.keySet()) {
            assertEquals(0, actualMaterials.get(key) % 3);
        }

    }

    private Map<String,Integer> setTestMaterialList(){
        Trapeze trapeze = new Trapeze(installation);
        installation.setModel(trapeze);
        installationList.addInstallation(installation);
        return builder.printMaterialList();
    }
    private Map<String,Integer> setTestMaterialListForMultipleInstallations(){
        Trapeze trapeze = new Trapeze(installation);
        installation.setModel(trapeze);
        installationList.addInstallation(installation);
        installationList.addInstallation(installation);
        installationList.addInstallation(installation);
        return builder.printMaterialList();
    }
}
