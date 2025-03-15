package pl.patrykkukula.Model;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class InstallationListTest {

    @Test
    public void shouldAddInstallationToListCorrectly(){
        Installation installation = setTestInstallation();
        InstallationList installationList = new InstallationList(new ArrayList<>());
        installationList.addInstallation(installation);
        assertTrue(installationList.getInstallationList().contains(installation));
    }
    @Test
    public void shouldPrintAllInstallationWhenListIsNotEmpty(){
        InstallationList installationList = new InstallationList();
        installationList.addInstallation(setTestInstallation());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
        installationList.getAllInstallations();
        String output = stream.toString().trim();
        assertTrue(output.contains("Liczba instalacji: "));
        assertTrue(output.contains("Lp. 1"));
        assertFalse(output.contains("Lp. 2"));
        System.setOut(System.out);
    }
    @Test
    public void installationListShouldBeEmptyWhenCreated(){
        InstallationList installationList = new InstallationList();
        assertTrue(installationList.getInstallationList().isEmpty());
    }
    @Test
    public void shouldPrintMessageWhenListIsEmpty(){
        InstallationList installationList = new InstallationList();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
        installationList.getAllInstallations();
        String output = stream.toString().trim();
        assertTrue(output.contains("Lista instalacji jest pusta"));
        System.setOut(System.out);
    }
    private Installation setTestInstallation() {
        List<Integer> modules = List.of(10);
        return new Installation(10, 10, modules,
                new PvModule(495, 35, 1050, 2050), "poziomo", 2, "T2");
    }
}
