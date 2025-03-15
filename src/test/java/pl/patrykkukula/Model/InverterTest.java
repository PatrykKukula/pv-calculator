package pl.patrykkukula.Model;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InverterTest {

    @Test
    public void shouldSetCorrectPowerBasedOnInstallation() {
        Inverter inverter = setTestInverter();
        assertEquals(5, inverter.getInverterPower(), "Moc inwertera powinna wynosić 5kW" );
    }
    @Test
    public void shouldSetCorrectCurrentBasedOnInstallation() {
        Inverter inverter = setTestInverter();
        double expectedCurrent = 7.68;
        assertEquals(expectedCurrent, inverter.getCurrent(), "Prąd inwertera dla danych testowych powinien wynosić 7.68A");
    }
    @Test
    public void shouldThrowNullPointerExceptionWhenInstallationIsNull(){
        Exception exception = assertThrows(NullPointerException.class, () -> new Inverter(null));
        assertTrue(exception.getMessage().contains("Instalacja nie może być nullem"));
    }
    private Inverter setTestInverter() {
        List<Integer> rowsAndModules = List.of(10);
        Installation installation = new Installation(10, 10, rowsAndModules,
                new PvModule(495, 35, 1050, 2050), "poziomo", 2, "T2");
        return new Inverter(installation);
    }
}
