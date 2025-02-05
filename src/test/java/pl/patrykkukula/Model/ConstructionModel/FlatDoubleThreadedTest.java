package pl.patrykkukula.Model.ConstructionModel;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlatDoubleThreadedTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldSetDoubleThreadedScrewCorrectly(){
        FlatDoubleThreaded flatDoubleThreaded = createFlatDoubleThreadedTest();
        int expectedDoubleThreadedScrew = 113;

        assertEquals(expectedDoubleThreadedScrew, flatDoubleThreaded.getDoubleThreadedScrew());
    }
    @Test
    public void shouldSetAngleBarCorrectly(){
        FlatDoubleThreaded flatDoubleThreaded = createFlatDoubleThreadedTest();
        int expectedAngleBar = 70;

        assertEquals(expectedAngleBar, flatDoubleThreaded.getAngleBar());
    }
    @Test
    public void shouldSetHexagonScrewAndNutCorrectly(){
        FlatDoubleThreaded flatDoubleThreaded = createFlatDoubleThreadedTest();
        int expectedHexagonScrewAndNut = 100;

        assertEquals(expectedHexagonScrewAndNut, flatDoubleThreaded.getHexagonScrew());
        assertEquals(expectedHexagonScrewAndNut, flatDoubleThreaded.getHexagonNut());
    }
    private FlatDoubleThreaded createFlatDoubleThreadedTest(){
        return new FlatDoubleThreaded(installation);
    }

}
