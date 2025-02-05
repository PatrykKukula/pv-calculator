package pl.patrykkukula.Model.ConstructionModel;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlatThreadedRodTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldSetAngleBarCorrectly() {
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedAngleBar = 70;

        assertEquals(expectedAngleBar, flatThreadedRod.getAngleBar());
    }
    @Test
    public void shouldSetThreadedRodCorrectly() {
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedThreadedRod = 40;
        assertEquals(expectedThreadedRod, flatThreadedRod.getThreadedRod());
    }
    @Test
    public void shouldSetHexagonScrewCorrectly(){
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedHexagonScrew = 100;
        assertEquals(expectedHexagonScrew, flatThreadedRod.getHexagonScrew());
    }
    @Test
    public void shouldSetEpdmCorrectly() {
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedHexagonNut = 180;
        assertEquals(expectedHexagonNut, flatThreadedRod.getHexagonNut());
    }
    @Test
    public void shouldSetHexagonNutCorrectly() {
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedEpdm = 40;
        assertEquals(expectedEpdm, flatThreadedRod.getEpdm());
    }
    @Test
    public void shouldSetChemicalAnchorCorrectly() {
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedChemicalAnchor = 7;
        assertEquals(expectedChemicalAnchor, flatThreadedRod.getChemicalAnchor());
    }
    @Test
    public void shouldSetSleeveCorrectly() {
        FlatThreadedRod flatThreadedRod = createTestFlatThreadedRod();
        int expectedSleeve = 40;
        assertEquals(expectedSleeve, flatThreadedRod.getSleeve());
    }
    private FlatThreadedRod createTestFlatThreadedRod() {
        return new FlatThreadedRod(installation);
    }
}
