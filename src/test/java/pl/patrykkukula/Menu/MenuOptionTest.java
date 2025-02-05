package pl.patrykkukula.Menu;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MenuOptionTest {

    @Test
    public void shouldReturnCorrectOption(){
        assertEquals(MenuOption.CREATE,MenuOption.fromCode(1));
        assertEquals(MenuOption.EXIT,MenuOption.fromCode(2));
        assertEquals(MenuOption.VIEW,MenuOption.fromCode(3));
        assertEquals(MenuOption.SAVE,MenuOption.fromCode(4));
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidCode(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> MenuOption.fromCode(-1));
        assertEquals("Nieznany kod: -1", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> MenuOption.fromCode(0));
        assertEquals("Nieznany kod: 0", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> MenuOption.fromCode(5));
        assertEquals("Nieznany kod: 5", ex.getMessage());
    }
}
