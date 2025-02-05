package pl.patrykkukula.Model.ConstructionModel.Abstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.TestInstallation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractProfileConstructionModelTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();
    AbstractProfileConstructionModel model;

    @BeforeEach
    void createModel(){
        model = new AbstractProfileConstructionModel(installation) {
            @Override
            protected void setAdditionalDetails() {
            }
        };
    }

    @Test
    public void shouldSetProfileCorrectly(){
        int expectedProfile = 79;

        assertEquals(expectedProfile,model.getProfile());
    }
    @Test
    public void shouldSetProfileJoinerCorrectly(){
        int expectedProfileJoiner = 12;

        assertEquals(expectedProfileJoiner,model.getProfileJoiner());
    }
}

