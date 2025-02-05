package pl.patrykkukula.Model.ConstructionModel;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VarioHookTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldSetVarioHookCorrectly() {
        VarioHook varioHook = createTestVarioHook();
        int expectedVarioHook = 113;

        assertEquals(expectedVarioHook, varioHook.getVarioHook());
    }
    @Test
    public void shouldSetHexagonScrewCorrectly(){
        VarioHook varioHook = createTestVarioHook();
        int expectedHexagonScrew = 113;
        assertEquals(expectedHexagonScrew, varioHook.getHexagonScrew());
    }
    @Test
    public void shouldSetWoodScrewCorrectly(){
        VarioHook varioHook = createTestVarioHook();
        int expectedWoodScrew = 452;
        assertEquals(expectedWoodScrew, varioHook.getWoodScrew());
    }
    @Test
    public void shouldSetHexagonNutCorrectly(){
        VarioHook varioHook = createTestVarioHook();
        int expectedHexagonNut = 113;
        assertEquals(expectedHexagonNut, varioHook.getHexagonNut());
    }

    private VarioHook createTestVarioHook(){
        return new VarioHook(installation);
    }
}
