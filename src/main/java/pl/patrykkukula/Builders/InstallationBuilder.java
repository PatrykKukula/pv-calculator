package pl.patrykkukula.Builders;
import pl.patrykkukula.Utils.MaterialsUtils.InstallationBuilderUtils;
import pl.patrykkukula.Menu.MenuOption;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.Inverter;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.GeneralUtils.FileManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.readInt;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.readText;

public class InstallationBuilder {
    private final InstallationBuilderUtils utils = new InstallationBuilderUtils();
    private final InstallationList installationList;

    public InstallationBuilder(InstallationList installationList) {
        this.installationList = installationList;
    }
    public InstallationList build(PvModule pvModule) {
        while (true) {
            try {
                int action = getMenuAction();
                switch (MenuOption.fromCode(action)) {
                    case CREATE -> createInstallation(pvModule);
                    case EXIT -> {
                        return installationList;
                    }
                    case VIEW -> viewInstallationList();
                    case SAVE -> saveFile();
                    case CLEAR -> clearInstallationList();
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieznana opcja menu. Wybierz opcję [1] - [4]");
            }
        }
    }
    private int getMenuAction() {
        while (true) {
            try {
                System.out.println("[1] - Dodaj instalację");
                System.out.println("[2] - Wyjdź do poprzedniego menu");
                System.out.println("[3] - Wyświetl listę instalacji");
                System.out.println("[4] - Zapisz listę do pliku");
                System.out.println("[5] - Wyczyść listę instalacji");
                return readInt();
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieprawidłowy format danych. Podaj liczbę [1] - [5]");
            }
        }
    }
    private void createInstallation(PvModule pvModule) {
        List<Integer> rowsAndModules;
        boolean notAccepted = true;
        while (notAccepted) {
            String surgeArrester = getValidSurgeArrester();
            String orientation = getValidOrientation();
            String constructionType = getValidConstructionType();
            int dcCable = getValidDcCable();
            int acCable = getValidAcCable();
            int strings = getValidStrings();
            rowsAndModules = utils.setRowsAndModules(pvModule);
            Installation installation = new Installation(acCable, dcCable, rowsAndModules, pvModule, orientation, strings, surgeArrester);
            installation.setType(constructionType);
            installation.setModel(utils.setModelType(constructionType,installation));
            installation.setInverter(new Inverter(installation));
            notAccepted = confirmInstallation(installation);
        }
    }
    private void viewInstallationList() {
        installationList.getAllInstallations();
    }
    private boolean confirmInstallation(Installation installation) {
        while (true) {
            System.out.println("Stworzono następującą instalację:");
            System.out.println(installation.getInstallationDetails());
            System.out.println("Jeśli się zgadza naciśnij [1]");
            System.out.println("Jeśli chcesz poprawić dane naciśnij [2]");
            int action = readInt();
            if (action == 1) {
                installationList.addInstallation(installation);
                return false;
            } else if (action == 2) {
                System.out.println("Dodaj instalację ponownie.");
                Installation.count -= 1;
                installation.getRowsAndModules().clear();
            }
            System.out.println("Nieprawidłowy wybór");
        }
    }
    private void saveFile() {
        if (installationList.getInstallationList().isEmpty()) {
            System.out.println("Lista instalacji jest pusta");
            return;
        }
        FileManager manager = new FileManager();
        ConstructionMaterialsBuilder constructionBuilder = new ConstructionMaterialsBuilder(installationList);
        ElectricMaterialsBuilder electricBuilder = new ElectricMaterialsBuilder();
        Map<String, Integer> constructionMaterials = constructionBuilder.buildConstructionMaterials();
        Map<String, Integer> electricMaterials = electricBuilder.buildElectricMaterials(installationList);
        String path = setPath();
        try {
            manager.saveFile(constructionMaterials, electricMaterials, installationList, path);
            System.out.println("Zapisano plik w lokalizacji " + path);
        } catch (IOException ex) {
            System.out.println(("Błąd w zapisie pliku. Nieprawidłowa ścieżka lub nazwa pliku."));
        }
    }
    private void clearInstallationList(){
        List<Installation> installations = installationList.getInstallationList();
        if (!installations.isEmpty()) {
            installations.clear();
            System.out.println("Pomyślnie usunięto listę instalacji");
            return;
        }
        System.out.println("Lista instalacji jest pusta");
    }
    private String setPath() {
        System.out.println("Podaj dokładną lokalizację pliku np. C:/Users/User/Desktop");
        String location = readText();
        System.out.println("Podaj nazwę pliku");
        String fileName = readText();
        return location + "/" + fileName + ".xlsx";
    }
    private String getValidSurgeArrester() {
        while (true) {
            System.out.println("Podaj typ ogranicznika przepięć");
            System.out.println("[1] - Typ 2");
            System.out.println("[2] - Typ 1+2");
            int type = readInt();
            if (type == 1) return "T2";
            else if (type == 2) return "T1+2";
            else System.out.println("Nieprawidłowy wybór");
        }
    }
    private int getValidStrings() {
        while (true) {
            System.out.println("Podaj liczbę łańcuchów [1-7]");
            int strings = readInt();
            if (strings > 0 && strings <= 7) return strings;
            else if (strings < 0) System.out.println("Liczba łańuchów nie może być ujemna");
            else System.out.println("Liczba łańcuchów jest zbyt duża");
        }
    }
    private String getValidConstructionType() {
        while (true) {
            showConstructionText();
            int constructionType = readInt();
            if (constructionType == 1) return "Mostki trapezowe";
            else if (constructionType == 2) return "Śruba dwugwintowa";
            else if (constructionType == 3) return "Dach płaski - śruba dwugwintowa";
            else if (constructionType == 4) return "Dach płaski - pręt gwintowany";
            else if (constructionType == 5) return "Hak vario";
            System.out.println("Nieprawidłowy wybór");
        }
    }
    private int getValidDcCable() {
        while (true) {
            System.out.println("Podaj długość trasy kablowej DC (od paneli do rozdzielnicy DC) [m]");
            int dcCable = readInt();
            if (dcCable >= 0) return dcCable;
            else System.out.println("Długość trasy kablowej nie może być mniejsza od 0");
        }
    }
    private int getValidAcCable() {
        while (true) {
            System.out.println("Podaj długość trasy kablowej AC (od falownika do miejsca wpięcia) [m]");
            int acCable = readInt();
            if (acCable >= 0) return acCable;
            System.out.println("Długość trasy kablowej nie może być mniejsza od 0");
        }
    }
    private String getValidOrientation() {
        while (true) {
            System.out.println("Podaj ułożenie paneli");
            System.out.println("[1] - pionowo");
            System.out.println("[2] - poziomo");
            int orientation = readInt();
            if (orientation == 1) return "pionowo";
            else if (orientation == 2) return "poziomo";
            System.out.println("Nieprawidłowy wybór");
        }
    }
    private void showConstructionText() {
        System.out.println("Wybierz rodzaj konstrukcji");
        System.out.println("[1] - Mostki trapezowe");
        System.out.println("[2] - Śruba dwugwintowa");
        System.out.println("[3] - Dach płaski - śruba dwugwintowa");
        System.out.println("[4] - Dach płaski - Dach płaski - pręt gwintowany");
        System.out.println("[5] - Hak vario");
    }
}



