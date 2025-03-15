package pl.patrykkukula.Model;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.TestInstallation;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

public class InstallationTest {
    private final TestInstallation testInstallation = new TestInstallation();
    private final Installation installation = testInstallation.createTestInstallation();

    @Test
    public void shouldSetTotalPowerWhenCreated(){
        double actualTotalPower = installation.getTotalPower();
        double expectedTotalPower = 18 * 0.48;
        assertEquals(expectedTotalPower, actualTotalPower, 0.01);
    }
    @Test
    public void shouldGetModulesQtyCorrectly(){
        int actualModulesQty = installation.getModulesQty();
        assertEquals(18, actualModulesQty);
    }
    @Test
    public void shouldThrowExceptionWhenNoModulesAdded(){
        Installation installation = new Installation();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, installation::getModulesQty);
        assertEquals("Do instalacji nie dodano żadnych modułów", ex.getMessage());
    }
    @Test
    public void shouldCalculateProfileLengthCorrectly(){
        double actualValue = installation.calculateProfileLength();
        double expectedValue = round(((18 * 2.05 * 2 * 1.07) * 100 )/100);
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void shouldCalculateEndClampCorrectly(){
        int actualValue = installation.calculateEndClamp();
        assertEquals(8, actualValue);
    }
    @Test
    public void shouldCalculateTotalEdgeCorrectly(){
        int actualValue = installation.calculateTotalEdge();
        assertEquals(40, actualValue);
    }
    @Test
    public void shouldCalculateAngleBarLengthCorrectly(){
        int actualValue = installation.calculateAngleBarLength();
        int expectedValue = (int)Math.ceil(20 * 3.5);
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void shouldReturnAcCableCrossSectionCorrectly(){
        double actualValue = installation.getAcCableCrossSection();
        double expectedValue = 10.0;
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void shouldSetInstallationTypeCorrectly(){
        installation.setType("Mostki trapezowe");
        assertEquals("Mostki trapezowe", installation.getType());
        installation.setType("Dach płaski - śruba dwugwintowa");
        assertEquals("Dach płaski - śruba dwugwintowa", installation.getType());
        installation.setType("Hak Vario");
        assertEquals("Hak Vario", installation.getType());
    }
    @Test
    public void shouldReturnInstallationDetails(){
        String details = installation.getInstallationDetails();
        assertTrue(details.contains("Typ instalacji: "));
        assertTrue(details.contains("Moc instalacji: "));
    }
}
