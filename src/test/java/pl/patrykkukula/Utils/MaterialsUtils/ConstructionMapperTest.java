package pl.patrykkukula.Utils.MaterialsUtils;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ConstructionMapperTest {
    private final ConstructionMapper mapper = new ConstructionMapper();
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldAddConstructionMaterialsWithTrapezeCorrectly(){
        installation.setModel(createTestTrapeze());
        Map<String, Integer> actualMaterials = mapper.addConstructionMaterials(installation, createTestTrapeze());

        assertTrue(actualMaterials.containsKey("Mostek trapezowy"));
        assertTrue(actualMaterials.containsKey("Wkręt farmerski"));
        assertTrue(actualMaterials.containsKey("Klema środkowa"));
    }
    @Test
    public void shouldAddConstructionMaterialsWithVarioHookCorrectly(){
        installation.setModel(createTestVarioHook());
        Map<String, Integer> actualMaterials = mapper.addConstructionMaterials(installation, createTestVarioHook());

        assertTrue(actualMaterials.containsKey("Hak Vario: "));
        assertTrue(actualMaterials.containsKey("Wkręt ciesielski 8x80"));
    }
    @Test
    public void shouldAddConstructionMaterialsWithDoubleThreadedCorrectly(){
        installation.setModel(createTestDoubleThreaded());
        Map<String, Integer> actualMaterials = mapper.addConstructionMaterials(installation, createTestDoubleThreaded());

        assertTrue(actualMaterials.containsKey("Śruba Dwugwintowa M10x250"));
        assertTrue(actualMaterials.containsKey("Adapter montażowy"));
    }
    @Test
    public void shouldAddConstructionMaterialsWithFlatDoubleThreadedCorrectly(){
        installation.setModel(createTestFlatDoubleThreaded());
        Map<String, Integer> actualMaterials = mapper.addConstructionMaterials(installation, createTestFlatDoubleThreaded());

        assertTrue(actualMaterials.containsKey("Śruba Dwugwintowa M10x250"));
        assertTrue(actualMaterials.containsKey("Śruba imbusowa 20 mm"));
        assertTrue(actualMaterials.containsKey("Wpust przesuwny"));
        assertTrue(actualMaterials.containsKey("Klema końcowa 30 mm"));
        assertTrue(actualMaterials.containsKey("Klema środkowa"));
    }
    @Test
    public void shouldAddConstructionMaterialsWithFlatThreadedRodCorrectly(){
        installation.setModel(createTestFlatThreadedRod());
        Map<String, Integer> actualMaterials = mapper.addConstructionMaterials(installation, createTestFlatThreadedRod());

        assertTrue(actualMaterials.containsKey("Pręt gwintowany 30 cm"));
        assertTrue(actualMaterials.containsKey("Podkładka epdm M10"));
        assertTrue(actualMaterials.containsKey("Kotwa chemiczna"));
        assertTrue(actualMaterials.containsKey("Koszyk do kotwy chemicznej"));
    }
    @Test
    public void shouldThrowNullPointerExceptionWhenInstallationIsNull(){
       NullPointerException ex = assertThrows(NullPointerException.class, () -> new AbstractConstructionModel(null) {
            @Override
            protected void setAdditionalDetails() {
            }
        });
        assertEquals("Instalacja nie może być nullem", ex.getMessage());
    }
    private Trapeze createTestTrapeze(){
        return new Trapeze(installation);
    }
    private VarioHook createTestVarioHook(){
        return new VarioHook(installation);
    }
    private DoubleThreaded createTestDoubleThreaded(){
        return new DoubleThreaded(installation);
    }
    private FlatDoubleThreaded createTestFlatDoubleThreaded(){
        return new FlatDoubleThreaded(installation);
    }
    private FlatThreadedRod createTestFlatThreadedRod(){
        return new FlatThreadedRod(installation);
    }
}

