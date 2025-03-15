package pl.patrykkukula.Model;
import lombok.*;

@Getter
@AllArgsConstructor
public class PvModule {
    private int power;
    private int frame;
    private int width;
    private int length;

    public String getModuleDetails(){
      return   "Moc modułu: " + power + " W" + System.lineSeparator() +
                "Grubość ramki: " + frame + "mm" + System.lineSeparator() +
                "Szerokość modułu: " + width + "mm" + System.lineSeparator() +
                "Długość modułu: " + length + "mm" + System.lineSeparator();
    }
}
