package pl.patrykkukula.Builders.BuilderUtils;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.Inverter;
import pl.patrykkukula.Model.PvModule;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static pl.patrykkukula.Constants.ConstructionConstants.CONVERT_UNIT_FROM_KILOS;
import static pl.patrykkukula.Utils.ScannerUtil.readInt;

@SuppressWarnings("unchecked")
public class InstallationBuilderUtils {

    public InstallationBuilderUtils() {
    }

    public <T extends AbstractConstructionModel> T setModelType(Installation installation) {
        if (installation.getType().equals("Mostki trapezowe")) return (T) setModelTrapeze(installation);
        else if (installation.getType().equals("Śruba dwugwintowa")) return (T) setModelDoubleThread(installation);
        else if (installation.getType().equals("Dach płaski - śruba dwugwintowa")) return (T) setModelFlatDoubleThread(installation);
        else if (installation.getType().equals("Dach płaski - pręt gwintowany")) return (T) setModelFlatThreadedRod(installation);
        else if (installation.getType().equals("Hak vario")) return (T) setModelVarioHook(installation);
        return null;
    }

    public List<Integer> setRowsAndModules(PvModule pvModule) {
        System.out.println("W następnych krokach możesz dodawać rzędy paneli. Jeśli wybierzesz" +
                " \"Dodaj  rząd\" będziesz musiał podać liczbę paneli w tym rzędzie. Musisz dodać przynajmniej 1 rząd paneli.");
        List<Integer> rowsAndModules = new ArrayList<>();
        while (true) {
            try {
                System.out.println("[1] - Dodaj rząd");
                System.out.println("[2] - Idź dalej");
                int action = readInt();
                switch (action) {
                    case 1 -> {
                        int modulesQty = getValidModulesQuantity();
                        rowsAndModules.add(modulesQty);
                        int sum = rowsAndModules.stream()
                                .mapToInt(Integer::intValue)
                                .sum();
                        if (sum * pvModule.getPower() / CONVERT_UNIT_FROM_KILOS >= 70) {
                            rowsAndModules.clear();
                            throw new InputMismatchException();
                        } else System.out.println("Dodano rząd zawierający " + modulesQty + " modułów.");
                    }
                    case 2 -> {
                        if (!rowsAndModules.isEmpty()) return rowsAndModules;
                        else System.out.println("Musisz dodać przynajmniej 1 rząd paneli");
                    }
                    default -> throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieznana opcja menu. Wybierz opcję [1] lub [2]");
            } catch (InputMismatchException ex) {
                System.out.println("Moc instalacji przekroczyła  70kW. Kalkulator nie obejmuje instalacji o takiej mocy.");
                System.out.println("Dodaj instalację o mocy < 70kW lub wyjdź do z menu");
            }
        }
    }
    private int getValidModulesQuantity() {
        while (true) {
            try {
                System.out.println("Podaj liczbę paneli");
                int quantity = readInt();
                if (quantity >= 3 && quantity <= 25) return quantity;
                System.out.println("Liczba paneli musi wynosić od 3 do 25");
            } catch (NumberFormatException ex) {
                System.out.println("Nieprawidłowy format danych");
            }
        }
    }
    private Trapeze setModelTrapeze(Installation installation) {
        return new Trapeze(installation);
    }
    private VarioHook setModelVarioHook(Installation installation) {
        return new VarioHook(installation);
    }
    private DoubleThreaded setModelDoubleThread(Installation installation) {
        return new DoubleThreaded(installation);
    }
    private FlatDoubleThreaded setModelFlatDoubleThread(Installation installation) {
        return new FlatDoubleThreaded(installation);
    }
    private FlatThreadedRod setModelFlatThreadedRod(Installation installation) {
        return new FlatThreadedRod(installation);
    }
}
