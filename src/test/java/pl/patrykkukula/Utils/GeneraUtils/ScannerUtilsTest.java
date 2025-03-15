package pl.patrykkukula.Utils.GeneraUtils;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pl.patrykkukula.Menu.MenuHandler;
import pl.patrykkukula.Utils.GeneralUtils.ScannerUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ScannerUtilsTest {
    private final MenuHandler menuHandler = new MenuHandler();

    @Test
    public void shouldThrowNumberFormatExceptionWhenStringIsPassedToReadInt() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (MockedStatic<ScannerUtil> scannerMock = mockStatic(ScannerUtil.class)) {
            scannerMock.when(ScannerUtil::readInt).thenAnswer(invocation -> {
                System.out.println("Nieprawidłowy format danych. Wprowadź poprawną liczbę");
                return 2;
            });
            System.setOut(new PrintStream(outputStream));
            menuHandler.start();

            assertTrue(outputStream.toString().contains("Nieprawidłowy format danych. Wprowadź poprawną liczbę"));

            System.setOut(System.out);
        }
    }
}
