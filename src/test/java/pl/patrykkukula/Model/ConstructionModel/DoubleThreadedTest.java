package pl.patrykkukula.Model.ConstructionModel;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleThreadedTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldSetAdditionalDetailsCorrectly() {
        DoubleThreaded doubleThreaded = createTestDoubleThreaded();
        int expectedDetail = 113;

        assertEquals(expectedDetail, doubleThreaded.getDoubleThreadedScrew());
        assertEquals(expectedDetail, doubleThreaded.getAdapter());
        assertEquals(expectedDetail, doubleThreaded.getHexagonScrew());
        assertEquals(expectedDetail, doubleThreaded.getHexagonNut());
    }
    private DoubleThreaded createTestDoubleThreaded() {
        return new DoubleThreaded(installation);
    }
}
