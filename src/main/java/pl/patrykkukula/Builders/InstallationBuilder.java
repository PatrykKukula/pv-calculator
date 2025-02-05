package pl.patrykkukula.Builders;
import pl.patrykkukula.Builders.BuilderUtils.InstallationBuilderUtils;
import pl.patrykkukula.Menu.MenuOption;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.Inverter;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.FileManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static pl.patrykkukula.Utils.ScannerUtil.readInt;
import static pl.patrykkukula.Utils.ScannerUtil.readText;


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
            return readInt();
        }
        catch (NumberFormatException ex){
            System.out.println("Nieprawidłowy format danych. Podaj liczbę [1] - [4]");}
    }
    }
    private void createInstallation(PvModule pvModule) {
        List<Integer> rowsAndModules;
        boolean notAccepted = true;
        while (notAccepted) {
            String surgeArrester = getValidSurgeArrester();
            String orientation = getValidOrientation();
            int constructionType = getValidConstructionType();
            int dcCable = getValidDcCable();
            int acCable = getValidAcCable();
            int strings = getValidStrings();
            rowsAndModules = utils.setRowsAndModules(pvModule);
            Installation installation = new Installation(acCable, dcCable, rowsAndModules, pvModule, orientation, strings, surgeArrester);
            installation.setType(constructionType);
            installation.setModel(utils.setModelType(installation));
            installation.setInverter(createInverter(installation));
            notAccepted = confirmInstallation(installation);
        }
    }
    private Inverter createInverter(Installation installation) {
        return new Inverter(installation);
    }
    private void showConstructionText() {
        System.out.println("Wybierz rodzaj konstrukcji");
        System.out.println("[1] - Mostki trapezowe");
        System.out.println("[2] - Śruba dwugwintowa");
        System.out.println("[3] - Dach płaski - śruba dwugwintowa");
        System.out.println("[4] - Dach płaski - Dach płaski - pręt gwintowany");
        System.out.println("[5] - Hak vario");
    }
    private void viewInstallationList() {
        if (!installationList.getInstallationList().isEmpty()) installationList.getAllInstallations();
        else System.out.println("Brak dodanych instalacji");
    }
    private boolean confirmInstallation(Installation installation) {
        while (true) {
            try{
            System.out.println("Stworzono następującą instalację");
            System.out.println(installation.getInstallationDetails());
            System.out.println("Jeśli się zgadza naciśnij [1]. Jeśli chcesz poprawić dane naciśnij [2]");
            int action = readInt();
            if (action == 1) {
                installationList.addInstallation(installation);
                return false;
            } else if (action == 2) {
                System.out.println("Dodaj instalację ponownie.");
                Installation.count -= 1;
                installation.getRowsAndModules().clear();
                return true;
            }
            System.out.println("Nieznana opcja menu. Wybierz [1] lub [2]");
        }
            catch (NumberFormatException ex){
                System.out.println("Niepoprawny format danych");
            }
        }
    }
    private void saveFile() {
        FileManager manager = new FileManager();
        MaterialListBuilder constructionBuilder = new MaterialListBuilder(installationList);
        ElectricListBuilder electricBuilder = new ElectricListBuilder();
        Map<String, Integer> constructionMaterials = constructionBuilder.printMaterialList();
        Map<String, Integer> electricMaterials = electricBuilder.buildElectricMaterials(installationList);
        String path = setPath();
        try {
            manager.saveFile(constructionMaterials, electricMaterials, installationList, path);
        } catch (IOException ex) {
            throw new RuntimeException("Błąd w zapisie pliku. Nieprawidłowa ścieżka lub nazwa pliku.");
        }
        System.out.println("Zapisano plik w lokalizacji " + path);
    }
    private String setPath() {
        System.out.println("Podaj lokalizację pliku np. C:/Users/User/Desktop");
        String location = readText();
        System.out.println("Podaj nazwę pliku");
        String fileName = readText();
        return location + "/" + fileName + ".xlsx";
    }
    // VALIDATING INPUT DATA
    private String getValidSurgeArrester() {
        while (true) {
            try {
                System.out.println("Podaj typ ogranicznika przepięć [T2] lub [T1+2]");
                String type = readText();
                if (type.equals("T2") || type.equals("T1+2")) return type;
                else System.out.println("Podano nieprawidłowy typ");
            }
            catch (NumberFormatException ex){
                System.out.println("Nieprawidłowy format danych");
            }
        }
    }
    private int getValidStrings() {
        while (true) {
            try {
                System.out.println("Podaj liczbę łańcuchów [1-6]");
                int strings = readInt();
                if (strings > 0 && strings <= 6) return strings;
                else if (strings < 0) System.out.println("Liczba łańuchów nie może być ujemna");
                else System.out.println("Liczba łańcuchów jest zbyt duża");
            }
            catch (NumberFormatException ex){
                System.out.println("Nieprawidłowy format danych");
            }
        }
    }
    private int getValidConstructionType() {
        while (true) {
            try{
            showConstructionText();
            int constructionType = readInt();
            if (constructionType == 1 || constructionType == 2 || constructionType == 3 || constructionType == 4 || constructionType == 5)
                return constructionType;
            System.out.println("Nieprawidłowy typ konstrukcji.");
        }
            catch (NumberFormatException ex){
                System.out.println("Nieprawidłowy format danych");}
        }
    }
    private int getValidDcCable() {
        while (true) {
            try{
            System.out.println("Podaj długość trasy kablowej DC (od paneli do rozdzielnicy DC) [m]");
            int dcCable = readInt();
            if (dcCable >= 0) return dcCable;
            System.out.println("Długość trasy kablowej nie może być mniejsza od 0");
        }     catch (NumberFormatException ex){
                System.out.println("Nieprawidłowy format danych");}
        }
    }
    private int getValidAcCable() {
        while (true) {
            try{
            System.out.println("Podaj długość trasy kablowej AC (od falownika do miejsca wpięcia) [m]");
            int acCable = readInt();
            if (acCable >= 0) return acCable;
            System.out.println("Długość trasy kablowej nie może być mniejsza od 0");
        }
            catch (NumberFormatException ex){
                System.out.println("Nieprawidłowy format danych");}
        }
    }
    private String getValidOrientation() {
        while (true) {
            try{
            System.out.println("Podaj ułożenie paneli [poziomo]/[pionowo]");
            String orientation = readText();
            if (orientation.equals("poziomo") || orientation.equals("pionowo")) return orientation;
            System.out.println("Nieprawidłowy kierunek. Wpisz [poziomo] lub [pionowo]");
        }
            catch (NumberFormatException ex){
                System.out.println("Nieprawidłowy format danych");}
        }
    }
}



