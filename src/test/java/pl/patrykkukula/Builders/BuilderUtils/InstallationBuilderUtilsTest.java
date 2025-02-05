package pl.patrykkukula.Builders.BuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.TestInstallation;
import pl.patrykkukula.Utils.ScannerUtil;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class InstallationBuilderUtilsTest {
    TestInstallation testInstallation;
    Installation installation;
    InstallationBuilderUtils installationBuilderUtils;


    @BeforeEach
    public void setUp() {
        installationBuilderUtils = new InstallationBuilderUtils();
        testInstallation = new TestInstallation();
        installation = testInstallation.createTestInstallation();
    }

    @Test
    public void shouldSetInstallationTypeForTrapezeCorrectly() {
        installation.setType(1);
        AbstractConstructionModel trapezeModel = installationBuilderUtils.setModelType(installation);
        installation.setModel(trapezeModel);
        assertEquals(Trapeze.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForDoubleThreadedCorrectly() {
        installation.setType(2);
        AbstractConstructionModel doubleThreadedModel = installationBuilderUtils.setModelType(installation);
        installation.setModel(doubleThreadedModel);
        assertEquals(DoubleThreaded.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForFlatDoubleThreadedCorrectly() {
        installation.setType(3);
        AbstractConstructionModel flatDoubleThreadedModel = installationBuilderUtils.setModelType(installation);
        installation.setModel(flatDoubleThreadedModel);
        assertEquals(FlatDoubleThreaded.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForFlatThreadedRodCorrectly() {
        installation.setType(4);
        AbstractConstructionModel flatThreadedRodModel = installationBuilderUtils.setModelType(installation);
        installation.setModel(flatThreadedRodModel);
        assertEquals(FlatThreadedRod.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForVarioHookCorrectly() {
        installation.setType(5);
        AbstractConstructionModel varioHookModel = installationBuilderUtils.setModelType(installation);
        installation.setModel(varioHookModel);
        assertEquals(VarioHook.class, installation.getModel().getClass());
    }

    @Test
    public void shouldAddModulesCorrectly() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            when(ScannerUtil.readInt()).thenReturn(1)
                    .thenReturn(12)
                    .thenReturn(1)
                    .thenReturn(10)
                    .thenReturn(2);
            List<Integer> actualRowsAndModules = installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertEquals(List.of(12, 10), actualRowsAndModules);
        }
    }
    @Test
    public void shouldAddMinAndMaxModulesCorrectly() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            when(ScannerUtil.readInt()).thenReturn(1)
                    .thenReturn(3)
                    .thenReturn(1)
                    .thenReturn(25)
                    .thenReturn(2);
            List<Integer> actualRowsAndModules = installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertEquals(List.of(3, 25), actualRowsAndModules);
        }
    }
    @Test
    public void shouldThrowIllegalArgumentCorrectly() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            when(ScannerUtil.readInt()).thenReturn(0,2,1,12,2);
            installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertTrue(outputStream.toString().contains("Nieznana opcja menu. Wybierz opcję [1] lub [2]"));
            assertTrue(outputStream.toString().contains("Musisz dodać przynajmniej 1 rząd paneli"));
        }
    }
    @Test
    public void shouldThrowInputMismatchExceptionCorrectly() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            when(ScannerUtil.readInt()).thenReturn(1, 25,1,25,1,25,1,25,1,25,1,25,1,12,2);
            installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertTrue(outputStream.toString().contains("Dodaj instalację o mocy < 70kW lub wyjdź do z menu"));
            System.setOut(System.out);
        }
    }
}




