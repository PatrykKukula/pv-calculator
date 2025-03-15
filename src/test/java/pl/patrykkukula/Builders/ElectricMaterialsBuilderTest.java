package pl.patrykkukula.Builders;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.Inverter;
import pl.patrykkukula.Model.PvModule;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
public class ElectricMaterialsBuilderTest {

    @Test
    public void shouldBuildCorrectElectricMaterials(){
        List<Integer> modules = List.of(10);
        Installation installation = new Installation(10,10, modules,
                new PvModule(495,35,1050,2050), "poziomo",2,"T2");
        installation.setInverter(new Inverter(installation));
        InstallationList installationList = new InstallationList(List.of(installation));
        ElectricMaterialsBuilder electricListBuilder = new ElectricMaterialsBuilder();

        Map<String, Integer> expectedMaterials = getExcpectedMaterials();
        Map<String, Integer> actualMaterials = electricListBuilder.buildElectricMaterials(installationList);

        assertNotNull(actualMaterials);
        assertEquals(expectedMaterials, actualMaterials);
    }
    @Test
    public void shouldReturnCorrectDcSwitchboardSize(){
        ElectricMaterialsBuilder electricListBuilder = new ElectricMaterialsBuilder();
        Installation installation = new Installation();
        installation.setStrings(3);
        int size = electricListBuilder.setDcSwitchboardSize(installation);

        assertEquals(18, size);
    }
    private static Map<String, Integer> getExcpectedMaterials() {
        Map<String, Integer> expectedMaterials = new LinkedHashMap<>();
        expectedMaterials.put("Falownik fotowoltaiczny 5 kW", 1);
        expectedMaterials.put("Kabel AC 5x2.5 mm2", 10);
        expectedMaterials.put("Kabel DC 4 mm2", 10);
        expectedMaterials.put("Zabezpieczenie nadprądowe typ B10", 1);
        expectedMaterials.put("Zabezpieczenie nadprądowe typ C10", 1);
        expectedMaterials.put("Wyłącznik RCD 10/0,1A",1);
        expectedMaterials.put("Ogranicznik przepieć AC 4P T2",1);
        expectedMaterials.put("Ogranicznik przepięć DC typ T2",2);
        expectedMaterials.put("Rozłącznik bezpiecznikowy DC 2p",2);
        expectedMaterials.put("Wkładka bezpiecznikowa gG 15A", 4);
        expectedMaterials.put("Rozdzielnica AC 12p", 1);
        expectedMaterials.put("Rozdzielnica DC 12p", 1);
        return expectedMaterials;
    }
}