package pl.patrykkukula.Builders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.GeneralUtils.ScannerUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class InstallationBuilderTest {
    InstallationList installationList;
    InstallationBuilder builder;
    PvModule pvModule = new PvModule(500, 35, 1050, 2050);

    @BeforeEach
    public void setUp() {
        installationList = new InstallationList();
        builder = new InstallationBuilder(installationList);
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            when(ScannerUtil.readInt()).thenReturn(1)
                    .thenReturn(1) // construction type
                    .thenReturn(10) // dc cable
                    .thenReturn(15) // ac cable
                    .thenReturn(2) // strings
                    .thenReturn(1) // create row
                    .thenReturn(12) // add panels
                    .thenReturn(2) // exit rows menu
                    .thenReturn(1)// confirm installation
                    .thenReturn(2) //exit menu
                    .thenReturn(2); //close pprogram
            when(ScannerUtil.readText()).thenReturn("T2", "pionowo");
            builder.build(pvModule);
        }
    }

    @Test
    public void shouldCreateInstallationSuccessfully() {
        assertEquals(1, installationList.getInstallationList().size());
    }
}


