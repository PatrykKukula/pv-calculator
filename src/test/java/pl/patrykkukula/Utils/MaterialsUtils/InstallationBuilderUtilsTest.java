package pl.patrykkukula.Utils.MaterialsUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.TestInstallation;
import pl.patrykkukula.Utils.GeneralUtils.ScannerUtil;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.*;

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
    @AfterEach
    public void setSystemOut(){
        System.setOut(System.out);
    }

    @Test
    public void shouldSetInstallationTypeForTrapezeCorrectly() {
        installation.setType("Mostki trapezowe");
        AbstractConstructionModel trapezeModel = installationBuilderUtils.setModelType(installation.getType(), installation);
        installation.setModel(trapezeModel);
        assertEquals(Trapeze.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForDoubleThreadedCorrectly() {
        installation.setType("Śruba dwugwintowa");
        AbstractConstructionModel doubleThreadedModel = installationBuilderUtils.setModelType(installation.getType(), installation);
        installation.setModel(doubleThreadedModel);
        assertEquals(DoubleThreaded.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForFlatDoubleThreadedCorrectly() {
        installation.setType("Dach płaski - śruba dwugwintowa");
        AbstractConstructionModel flatDoubleThreadedModel = installationBuilderUtils.setModelType(installation.getType(), installation);
        installation.setModel(flatDoubleThreadedModel);
        assertEquals(FlatDoubleThreaded.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForFlatThreadedRodCorrectly() {
        installation.setType("Dach płaski - pręt gwintowany");
        AbstractConstructionModel flatThreadedRodModel = installationBuilderUtils.setModelType(installation.getType(), installation);
        installation.setModel(flatThreadedRodModel);
        assertEquals(FlatThreadedRod.class, installation.getModel().getClass());
    }

    @Test
    public void shouldSetInstallationTypeForVarioHookCorrectly() {
        installation.setType("Hak vario");
        AbstractConstructionModel varioHookModel = installationBuilderUtils.setModelType(installation.getType(), installation);
        installation.setModel(varioHookModel);
        assertEquals(VarioHook.class, installation.getModel().getClass());
    }

    @Test
    public void shouldThrowInputMismatchExceptionForInvalidType() {
        installation.setType("invalid model");
        assertThrows(InputMismatchException.class, () -> installationBuilderUtils.setModelType(installation.getType(), installation));
    }


    @Test
    public void shouldAddModulesCorrectly() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            when(readInt()).thenReturn(1)
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
            when(readInt()).thenReturn(1)
                    .thenReturn(3)
                    .thenReturn(1)
                    .thenReturn(25)
                    .thenReturn(2);
            List<Integer> actualRowsAndModules = installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertEquals(List.of(3, 25), actualRowsAndModules);
        }
    }
    @Test
    public void shouldPrintTextWhenInvalidInput() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            scannerMock.when(ScannerUtil::readInt).thenReturn(0,2,1,12,2);
            installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertTrue(outputStream.toString().contains("Nieznana opcja menu. Wybierz opcję [1] lub [2]"));
            assertTrue(outputStream.toString().contains("Musisz dodać przynajmniej 1 rząd paneli"));
        }
    }
    @Test
    public void shouldPrintTextWhenInstallationOver70Kw() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            scannerMock.when(ScannerUtil::readInt).thenReturn(1, 25,1,25,1,25,1,25,1,25,1,25,1,12,2);
            installationBuilderUtils.setRowsAndModules(new PvModule(500, 35, 1050, 2050));
            assertTrue(outputStream.toString().contains("Dodaj instalację o mocy < 70kW lub wyjdź do menu"));
        }
    }
}




