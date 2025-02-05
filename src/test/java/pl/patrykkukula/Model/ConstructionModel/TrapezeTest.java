package pl.patrykkukula.Model.ConstructionModel;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrapezeTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldSetAdditionalDetailsCorrectly(){
        Trapeze trapeze = new Trapeze(installation);
        int expectedTrapeze = 40;
        int expectedSelfDrillingScrew = 240;

        assertEquals(expectedTrapeze, trapeze.getTrapeze());
        assertEquals(expectedSelfDrillingScrew, trapeze.getSelfDrillingScrew());
    }
}
