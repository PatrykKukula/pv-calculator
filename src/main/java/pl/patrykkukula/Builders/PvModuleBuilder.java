package pl.patrykkukula.Builders;
import pl.patrykkukula.Model.PvModule;
import static pl.patrykkukula.Constants.ConstructionConstants.*;
import static pl.patrykkukula.Constants.ElectricConstants.MAX_POWER;
import static pl.patrykkukula.Constants.ElectricConstants.MIN_POWER;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.readInt;
public class PvModuleBuilder {

    public PvModule build() {
        while (true) {
            System.out.println("Podaj dane dotyczące modułów PV");
            int power = readValidPower();
            int frame = readValidFrame();
            int width = readValidWidth();
            int length = readValidLength();
            PvModule pvModule = new PvModule(power, frame, width, length);
            System.out.println("Podano następujące dane");
            System.out.println(pvModule.getModuleDetails());
            System.out.println("Jeśli się zgadza naciśnij [1] aby przejść dalej. Aby poprawić dane naciśnij [2]");
            if (confirmData()) {
                return pvModule;
            }
        }
        }
        private boolean confirmData() {
            while (true) {
                int action = readInt();
                if (action == 1) {
                    return true;
                }
                else if (action == 2) {
                    return false;
                }
                System.out.println("Nieprawidłowa opcja menu. Wybierz [1] lub [2]");
            }
        }
    private int readValidPower(){
        while (true) {
                System.out.println("Podaj moc modułów [" + MIN_POWER + "-" + MAX_POWER + " W]");
                int power = readInt();
                if (power >= MIN_POWER && power <= MAX_POWER) {
                    return power;
                }
                System.out.println("Moc paneli spoza zakresu");
        }
    }
    private int readValidFrame(){
        while (true){
            String allowedFrame = "[30, 35 lub 40 mm]";
            System.out.println("Podaj grubość ramy " + allowedFrame);
            int frame = readInt();
            if (frame == 30 || frame == 35 || frame == 40) {
                return frame;
            }
            System.out.println("Grubość ramy spoza zakresu");
        }
    }
    private int readValidWidth(){
        while (true) {

            System.out.println("Podaj szerokość modułu [" + MIN_WIDTH + "-" + MAX_WIDTH + " mm]");
            int width = readInt();
            if (width >= 1050 && width <= 1320) {
                return width;
            }
            System.out.println("Szerokość ramy spoza zakresu");
        }
    }
    private int readValidLength(){
        while (true) {
                System.out.println("Podaj długość modułu [" + MIN_LENGTH + "-" + MAX_LENGTH + " mm]");
                int length = readInt();
                if (length >= 1697 && length <= 2350) {
                    return length;
                }
                System.out.println("Długość ramy spoza zakresu");
        }
    }
}

