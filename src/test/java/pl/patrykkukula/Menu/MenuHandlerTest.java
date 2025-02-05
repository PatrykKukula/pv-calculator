package pl.patrykkukula.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.patrykkukula.Utils.ScannerUtil;
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

    @Test
    public void shouldExitWhenUserChoosesExit() {
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            scannerMock.when(ScannerUtil::readInt).thenReturn(2);
            menuHandler.start();
            assertFalse(menuHandler.shouldContinue);
        }
        System.setOut(System.out);
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionWhenInvalidInputAction(){
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)){
            scannerMock.when(ScannerUtil::readInt).thenReturn(0)
                    .thenThrow(new NumberFormatException())
                    .thenReturn(2);
            menuHandler.start();

            assertTrue(outputStream.toString().contains("Nieprawidłowa opcja menu. Wybierz [1] lub [2]"));
            assertTrue(outputStream.toString().contains("Nieprawidłowy format danych. Upewnij się, że podałeś właściwą wartość"));
        }
    }
}
