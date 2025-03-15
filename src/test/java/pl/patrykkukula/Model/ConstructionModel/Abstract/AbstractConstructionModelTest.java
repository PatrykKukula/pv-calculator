package pl.patrykkukula.Model.ConstructionModel.Abstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractConstructionModelTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();
    private AbstractConstructionModel model;

    @BeforeEach
    void setUp() {
        model = new AbstractConstructionModel(installation) {
            @Override
            protected void setAdditionalDetails() {
            }
        };
    }
    @Test
    public void shouldSetBasicDataCorrectly(){
        int expectedValue = 40;

        assertEquals(expectedValue, model.getAllenScrew());
        assertEquals(expectedValue, model.getSlidingKey());
    }
    @Test
    public void shouldSetClampsCorrectly(){
        int expectedEndClampsValue = 8;
        int expectedMidClampsValue = 32;

        assertEquals(expectedEndClampsValue, model.getEndClamp());
        assertEquals(expectedMidClampsValue, model.getMidClamp());
    }
    @Test
    public void shouldThrowExceptionWhenInstallationIsNull(){
        NullPointerException ex = assertThrows(NullPointerException.class, () -> new AbstractConstructionModel(null) {
            @Override
            protected void setAdditionalDetails() {
            }
        });
        assertEquals("Instalacja nie może być nullem", ex.getMessage());
    }
    @Test
    public void shouldCreateAbstractConstructionModelWithValidInstallation(){
        assertNotNull(model.installation);
        assertEquals(installation, model.getInstallation());
    }
}
