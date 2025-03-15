package pl.patrykkukula.Utils.MaterialsUtils;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.*;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.PvModule;
import java.util.*;
import java.util.function.Function;

import static pl.patrykkukula.Constants.ConstructionConstants.CONVERT_UNIT_FROM_KILOS;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.readInt;

@NoArgsConstructor
public class InstallationBuilderUtils {
    private static final Map<String, Function<Installation, AbstractConstructionModel>> modelFactory = new HashMap<>();

    static {
        modelFactory.put("Mostki trapezowe", Trapeze::new);
        modelFactory.put("Śruba dwugwintowa", DoubleThreaded::new);
        modelFactory.put("Dach płaski - śruba dwugwintowa", FlatDoubleThreaded::new);
        modelFactory.put("Dach płaski - pręt gwintowany", FlatThreadedRod::new);
        modelFactory.put("Hak vario", VarioHook::new);
    }

    public AbstractConstructionModel setModelType(String type, Installation installation) {
        Function<Installation, AbstractConstructionModel> constructor = modelFactory.get(type);
        if (constructor == null) {
            throw new InputMismatchException("Nieoczekiwany błąd przy tworzeniu instalacji. Program zakończy działanie.");
        }
        return constructor.apply(installation);
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
                    default -> System.out.println("Nieznana opcja menu. Wybierz opcję [1] lub [2]");
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("Moc instalacji przekroczyła  70kW. Instalacje takiej mocy wymagają indywidualnego podejścia i kalkulator ich nie obejmuje.");
                System.out.println("Dodaj instalację o mocy < 70kW lub wyjdź do menu");
            }
        }
    }
    private int getValidModulesQuantity() {
        while (true) {
                System.out.println("Podaj liczbę paneli");
                int quantity = readInt();
                if (quantity >= 3 && quantity <= 25) return quantity;
                System.out.println("Liczba paneli musi wynosić od 3 do 25");
            }
        }
}
