package pl.patrykkukula.Menu;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.patrykkukula.Menu.MenuOption.*;

public class MenuOptionTest {

    @Test
    public void shouldReturnCorrectOption(){
        assertEquals(CREATE, fromCode(1));
        assertEquals(EXIT, fromCode(2));
        assertEquals(VIEW, fromCode(3));
        assertEquals(SAVE, fromCode(4));
        assertEquals(CLEAR, fromCode(5));
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidCode(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> fromCode(-1));
        assertEquals("Nieznany kod: -1", ex.getMessage());
        ex = assertThrows(IllegalArgumentException.class, () -> fromCode(0));
        assertEquals("Nieznany kod: 0", ex.getMessage());
        ex = assertThrows(IllegalArgumentException.class, () -> fromCode(6));
        assertEquals("Nieznany kod: 6", ex.getMessage());
    }
}
