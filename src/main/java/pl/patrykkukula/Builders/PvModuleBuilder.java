package pl.patrykkukula.Builders;
import pl.patrykkukula.Exceptions.ModulesDataInputMismatchException;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.ScannerUtil;
public class PvModuleBuilder {
    private static boolean notCorrect = true;


    public PvModule build() {
        while (notCorrect) {
            try {
                System.out.println("Podaj dane dotyczące modułów PV");
                System.out.println("Podaj moc modułów [370-650 W]");
                int power = ScannerUtil.readInt();
                if (power < 370 || power > 650) throw new ModulesDataInputMismatchException();
                System.out.println("Podaj grubość ramy [30, 35 lub 40 mm]");
                int frame = ScannerUtil.readInt();
                if (frame != 30 && frame != 35 && frame != 40) throw new ModulesDataInputMismatchException();
                System.out.println("Podaj szerokość modułu [1050-1320 mm]");
                int width = ScannerUtil.readInt();
                System.out.println("Podaj długość modułu [1697-2350 mm]");
                int lenght = ScannerUtil.readInt();
                if (width < 1050 || width > 1320 || lenght < 1697 || lenght > 2350)
                    throw new ModulesDataInputMismatchException();
                PvModule pvModule = new PvModule(power, frame, width, lenght);
                System.out.println("Podano poniższe parametry panela. Jeśli się zgadza naciśnij [1], aby przejść dalej." +
                        " Jeśli chcesz poprawić dane wciśnij [2]");
                System.out.println(pvModule.getModuleDetails());
                int action = ScannerUtil.readInt();
                if (action == 1) {
                    notCorrect = false;
                    return pvModule;
                } else if (action == 2) {
                    System.out.println("Wprowadź dane ponownie");
                    notCorrect = true;
                } else throw new IllegalArgumentException();
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieznana opcja menu. Wybierz [1] lub [2]");
            } catch (ModulesDataInputMismatchException ex) {
                System.out.println("Podano błędne dane wejściowe. Podaj parametry z zakresu w nawiasie []:");
                System.out.println("Moc z przedziału [370-650W]");
                System.out.println("Grubość ramki [30, 35 lub 40mm]");
                System.out.println("Szerokość modułu z przedziału [1050-1320mm]");
                System.out.println("Wysokość modułu z przedziału [1697-2350mm]");
            }
        }
        return null;
    }
}

