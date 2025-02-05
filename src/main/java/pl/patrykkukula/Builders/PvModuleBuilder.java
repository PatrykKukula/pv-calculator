package pl.patrykkukula.Builders;
import pl.patrykkukula.Model.PvModule;
import static pl.patrykkukula.Constants.ElectricConstants.*;
import static pl.patrykkukula.Utils.ScannerUtil.readInt;
public class PvModuleBuilder {

    String allowedFrame = "[30, 35 lub 40 mm]";

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
            System.out.println("Jeśli się zgadza naciśnij [1] aby przejść dalej. Jeśli chcesz poprawić dane wciśnij [2]");
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
            try {
                System.out.println("Podaj moc modułów [" + MIN_POWER + "-" + MAX_POWER + " W]");
                int power = readInt();
                if (power >= 370 && power <= 650) {
                    return power;
                }
                System.out.println("Moc paneli spoza zakresu");
            }
            catch (IllegalArgumentException ex){
                System.out.println("Błędnie wprowadzone dane. Podaj wartość z zakresu [" + MIN_POWER + "-" + MAX_POWER + " W]");
            }
        }
    }
    private int readValidFrame(){
        while (true){
            try{
            System.out.println("Podaj grubość ramy " + allowedFrame);
            int frame = readInt();
            if (frame == 30 || frame == 35 || frame == 40) {
                return frame;
            }
            System.out.println("Nieproprawna grubość ramy");
        }
            catch (IllegalArgumentException ex){
                System.out.println("Błędnie wprowadzone dane. Podaj wartość z zakresu " + allowedFrame);
            }
        }
    }
    private int readValidWidth(){
        while (true) {
            try{
            System.out.println("Podaj szerokość modułu [" + MIN_WIDTH + "-" + MAX_WIDTH + " mm]");
            int width = readInt();
            if (width >= 1050 && width <= 1320) {
                return width;
            }
            System.out.println("Szerokość spoza przedziału");
        }
            catch (IllegalArgumentException ex){
                System.out.println("Błędnie wprowadzone dane. Podaj wartość z zakresu [" + MIN_WIDTH + "-" + MAX_WIDTH +" mm]");}
        }
    }
    private int readValidLength(){
        while (true) {
            try {
                System.out.println("Podaj długość modułu [" + MIN_LENTGH + "-" + MAX_LENTGH + " mm]");
                int length = readInt();
                if (length >= 1697 && length <= 2350) {
                    return length;
                }
                System.out.println("Długość spoza przedziału");
            }
            catch (IllegalArgumentException ex){
                System.out.println("Błędnie wprowadzone dane. Podaj wartość z zakresu ["+ MIN_LENTGH + "-" + MAX_LENTGH + " mm]");
            }
        }
    }
}

