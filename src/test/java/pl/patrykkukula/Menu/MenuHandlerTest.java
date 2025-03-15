package pl.patrykkukula.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.patrykkukula.Utils.GeneralUtils.ScannerUtil;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuHandlerTest {
    private MenuHandler menuHandler;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp(){
        menuHandler = new MenuHandler();
        System.setOut(new PrintStream(outputStream));
    }
    @AfterEach
    public void setSystemOut() {
        System.setOut(System.out);
        ScannerUtil.closeScanner();
    }
    @Test
    public void shouldExitWhenUserChoosesExit() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            scannerMock.when(ScannerUtil::readInt).thenReturn(2);
            menuHandler.start();
            assertFalse(menuHandler.shouldContinue);
        }
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionWhenInvalidInputAction(){
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)){
            scannerMock.when(ScannerUtil::readInt).thenReturn(0)
                    .thenReturn(2);
            menuHandler.start();

            assertTrue(outputStream.toString().contains("Nieprawidłowa opcja menu. Wybierz [1] lub [2]"));
        }
    }
}
