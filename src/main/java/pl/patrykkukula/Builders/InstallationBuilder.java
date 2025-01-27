package pl.patrykkukula.Builders;
import pl.patrykkukula.Exceptions.InstallationDataInputMismatchException;
import pl.patrykkukula.Exceptions.ModulesDataInputMismatchException;
import pl.patrykkukula.Exceptions.modulesQtyInputMismatchException;
import pl.patrykkukula.Menu.MenuOption;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.ConstructionModel.DoubleThread;
import pl.patrykkukula.Model.ConstructionModel.FlatDoubleThread;
import pl.patrykkukula.Model.ConstructionModel.FlatThreaderRod;
import pl.patrykkukula.Model.ConstructionModel.Trapeze;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.ScannerUtil;
import java.util.ArrayList;
import java.util.List;

public class InstallationBuilder {
    private  Installation installation;
    private  boolean addInstallation = true;
    private  InstallationList installationList = new InstallationList();

    public  InstallationList build(PvModule pvModule) {
        if (pvModule == null) {
            System.out.println("Błąd - nie zapisano danych paneli");
            return installationList;
        }
        while (addInstallation) {
            try {
                int action = getMenuAction();
                switch (MenuOption.fromCode(action)) {
                    case CREATE -> createInstallation(pvModule);
                    case EXIT -> addInstallation = false;
                    case VIEW -> viewInstallationList();
                }
            }
            catch (IllegalArgumentException ex){
                System.out.println("Nieznana opcja menu");
            }
        }
        return installationList;
    }

    private int getMenuAction(){
        System.out.println("[1] - Dodaj instalację");
        System.out.println("[2] - Wyjdź do poprzedniego menu");
        System.out.println("[3] - Wyświetl listę instalacji");
        return ScannerUtil.readInt();
    }
    private void createInstallation(PvModule pvModule){
        List<Integer> rowsAndModules;
        boolean notAccepted = true;
        while (notAccepted) {
            try {
                showConstructionText();
                String constructionType = ScannerUtil.readText();
                int dcCable = readDcCable();
                int acCable = readAcCable();
                String orientation = readOrientation();
                try {
                    validateInputData(acCable, dcCable, orientation);
                }
                catch (ModulesDataInputMismatchException ex){
                    System.out.println("Błąd wprowadzonych danych. Długości kabli nie mogą być ujemne");
                    System.out.println("Orientacja musi być poziomo lub pionowo");
                }
                rowsAndModules = setRowsAndModules();
                Installation installation = new Installation(acCable, dcCable, rowsAndModules, pvModule, orientation);
                installation.setTotalPower();
                installation.setType(constructionType);
                installation.setModel(setModelType(installation));

                notAccepted = confirmInstallation(installation);
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieprawidłowy format danych. Upewnij się, że podałeś właściwą wartość");
            }
        }
    }
    private <T extends AbstractConstructionModel> T setModelType(Installation installation){
        if (installation.getModel().getClass() == Trapeze.class) return (T)setModelTrapeze(installation);
        if (installation.getModel().getClass() == DoubleThread.class) return (T)setModelDoubleThread(installation);
        if (installation.getModel().getClass() == FlatDoubleThread.class) return (T)setModelFlatDoubleThread(installation);
        if (installation.getModel().getClass() == FlatThreaderRod.class) return (T)setModelFlatThreadedRod(installation);
        return null;
    }
    private Trapeze setModelTrapeze(Installation installation){
            Trapeze trapeze = new Trapeze(installation);
            trapeze.setDetails();
            return trapeze;
    }
    private DoubleThread setModelDoubleThread(Installation installation){
        DoubleThread doubleThread = new DoubleThread(installation);
        doubleThread.setDetails();
        return doubleThread;
    }
    private FlatDoubleThread setModelFlatDoubleThread(Installation installation){
        FlatDoubleThread flatDoubleThread = new FlatDoubleThread(installation);
        flatDoubleThread.setDetails();
        return flatDoubleThread;
    }
    private FlatThreaderRod setModelFlatThreadedRod(Installation installation){
        FlatThreaderRod flatThreaderRod = new FlatThreaderRod(installation);
        flatThreaderRod.setDetails();
        return flatThreaderRod;
    }


    private List<Integer> setRowsAndModules() {
        System.out.println("W następnych krokach możesz dodawać rzędy paneli. Jeśli wybierzesz" +
                " \"Dodaj  rząd\" będziesz musiał podać liczbę paneli w tym rzędzie. Musisz dodać przynajmniej 1 rząd paneli.");
        List<Integer> rowsAndModules = new ArrayList<>();
        boolean addRow = true;
        while (addRow) {
            try {
                System.out.println("[1] - Dodaj rząd");
                System.out.println("[2] - Idź dalej");
                int action = ScannerUtil.readInt();
                switch (action) {
                    case 1 -> {
                        System.out.println("Podaj liczbę paneli");
                        int modulesQty = ScannerUtil.readInt();
                        if (modulesQty <= 0) throw new modulesQtyInputMismatchException();
                        rowsAndModules.add(modulesQty);
                        System.out.println("Dodano rząd zawierający " + modulesQty + " modułów.");
                    }
                    case 2 -> addRow = false;
                    default -> throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieprawidłowy format danych. Upewnij się, że podałeś właściwą wartość");
            } catch (InstallationDataInputMismatchException ex) {
                System.out.println("Błędne dane wejściowe instalacji.");
                System.out.println("Długości kabli nie mogę być mniejsze od 0.");
                System.out.println("Orientacja musi być pozioma lub pionowa");
            } catch (modulesQtyInputMismatchException ex) {
                System.out.println("Liczba paneli musi być większa od 0");
            }
        }
        return rowsAndModules;
    }
    private int readDcCable(){
        System.out.println("Podaj długość trasy kablowej DC (od paneli do rozdzielnicy DC) [m]");
        return ScannerUtil.readInt();
    } private int readAcCable(){
        System.out.println("Podaj długość trasy kablowej AC (od falownika do miejsca wpięcia) [m]");
        return ScannerUtil.readInt();
    }
    private String readOrientation() {
        System.out.println("Podaj ułożenie paneli [poziomo]/[pionowo]");
        return ScannerUtil.readText();
    }

    private void showConstructionText(){
        System.out.println("Podaj rodzaj konstrukcji wsporczej. Skopiuj i wklej lub wpisz ręcznie pełną nazwę.");
        System.out.println("Mostki trapezowe");
        System.out.println("Śruba dwugwintowa");
        System.out.println("Dach płaski - śruba dwugwintowa");
        System.out.println("Dach płaski - Dach płaski - pręt gwintowany");
    }
    private void validateInputData(int acCable, int dcCable, String orientation){
        if (dcCable <= 0 || acCable <= 0) throw new InstallationDataInputMismatchException();
        if (!orientation.equals("poziomo") && !orientation.equals("pionowo"))
            throw new InstallationDataInputMismatchException();
    }
    private void viewInstallationList(){
            if (installationList != null) installationList.getAllInstallations();
            else System.out.println("Lista instalacji jest pusta");

    }
    private boolean confirmInstallation(Installation installation){
        System.out.println("Stworzono następującą instalację");
        System.out.println(installation.getInstallationDetails());
        System.out.println("Jeśli się zgadza naciśnij [1]. Jeśli chcesz poprawić dane naciśnij[2]");
        int action = ScannerUtil.readInt();
        if (action == 1){
                installationList.addInstallation(installation);
                return false;
        }
        else {
            System.out.println("Dodaj instalację ponownie.");
            installation.getRowsAndModules().clear();
        }
        return true;
    }
}



