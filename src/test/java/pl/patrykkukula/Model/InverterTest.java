package pl.patrykkukula.Model;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InverterTest {

    @Test
    public void shouldSetCorrectPowerBasedOnInstallation() {
        Inverter inverter = setTestInverter();
        assertEquals(5, inverter.getPower(), "Powinno przypisać moc inwertera 5kW" );
    }
    @Test
    public void shouldSetCorrectCurrentBasedOnInstallation() {
        Inverter inverter = setTestInverter();
        double expectedCurrent = 7.68;
        assertEquals(expectedCurrent, inverter.getCurrent(), "Powinno obliczyć poprawnie prąd invertera");
    }
    @Test
    public void shouldThrowExceptionWhenInstallationIsNull(){
        Exception exception = assertThrows(NullPointerException.class, () -> new Inverter(null));
        assertTrue(exception.getMessage().contains("Instalacja nie może być nullem"), "powinnien rzucić wyjątek dla null");
    }
    private Inverter setTestInverter() {
        List<Integer> modules = List.of(10);
        Installation installation = new Installation(10, 10, modules,
                new PvModule(495, 35, 1050, 2050), "poziomo", 2, "T2");
        return new Inverter(installation);
    }
}
