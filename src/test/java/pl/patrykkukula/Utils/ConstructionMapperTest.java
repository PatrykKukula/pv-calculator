package pl.patrykkukula.Utils;
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
    public void shouldPrintMaterialsWithTrapezeCorrectly(){
        Map<String, Integer> actualMaterials = mapper.printMaterials(installation, createTestTrapeze());

        assertTrue(actualMaterials.containsKey("Mostek trapezowy"));
        assertTrue(actualMaterials.containsKey("Wkręt farmerski"));
        assertTrue(actualMaterials.containsKey("Klema środkowa"));
    }
    @Test
    public void shouldPrintMaterialsWithVarioHookCorrectly(){
        Map<String, Integer> actualMaterials = mapper.printMaterials(installation, createTestVarioHook());

        assertTrue(actualMaterials.containsKey("Hak Vario: "));
        assertTrue(actualMaterials.containsKey("Wkręt ciesielski 8x80"));
    }
    @Test
    public void shouldPrintMaterialsWithDoubleThreadedCorrectly(){
        Map<String, Integer> actualMaterials = mapper.printMaterials(installation, createTestDoubleThreaded());

        assertTrue(actualMaterials.containsKey("Śruba Dwugwintowa M10x250"));
        assertTrue(actualMaterials.containsKey("Adapter montażowy"));
    }
    @Test
    public void shouldPrintMaterialsWithFlatDoubleThreadedCorrectly(){
        Map<String, Integer> actualMaterials = mapper.printMaterials(installation, createTestFlatDoubleThreaded());

        assertTrue(actualMaterials.containsKey("Śruba Dwugwintowa M10x250"));
        assertTrue(actualMaterials.containsKey("Śruba imbusowa 20 mm"));
        assertTrue(actualMaterials.containsKey("Wpust przesuwny"));
        assertTrue(actualMaterials.containsKey("Klema końcowa 30 mm"));
        assertTrue(actualMaterials.containsKey("Klema środkowa"));
    }
    @Test
    public void shouldPrintMaterialsWithFlatThreadedRodCorrectly(){
        Map<String, Integer> actualMaterials = mapper.printMaterials(installation, createTestFlatThreadedRod());

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
        assertEquals("Instalacja nie może być null", ex.getMessage());
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionForUnknownType(){
        AbstractConstructionModel unknownType = new AbstractConstructionModel() {
            @Override
            protected void setAdditionalDetails() {
            }
        };
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> mapper.printMaterials(installation, unknownType));
        assertEquals("Nieznany typ instalacji: " + unknownType.getClass(), ex.getMessage());
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

