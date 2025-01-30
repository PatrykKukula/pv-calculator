package pl.patrykkukula.Menu;

import java.util.Arrays;

public enum MenuOption {
    CREATE(1),
    EXIT(2),
    VIEW(3),
    SAVE(4);

    private final int code;

    MenuOption(int code) {
        this.code = code;
    }

    public static MenuOption fromCode(int code) {
        return Arrays.stream(values())
                .filter(option -> option.code == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
