package pl.patrykkukula.Menu;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Builders.InstallationBuilder;
import pl.patrykkukula.Builders.PvModuleBuilder;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.PvModule;
import static pl.patrykkukula.Menu.MenuOption.*;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.closeScanner;
import static pl.patrykkukula.Utils.GeneralUtils.ScannerUtil.readInt;

@NoArgsConstructor
public class MenuHandler {
    boolean shouldContinue = true;
    private PvModule pvModule;
    private InstallationList installationList = new InstallationList();

    public void start() {
        printWelcomeMessage();
        while (shouldContinue) {
                printMainMenu();
                int action = readInt();
                handleAction(action);
        }
        closeScanner();
    }
    private void printWelcomeMessage(){
        System.out.println("""
                .:: Witaj w programie do tworzenia list materiałowych instalacji PV ::.
                    Po podaniu wymaganych danych wejściowych program wyliczy
                     materiały konstrukcyjne i podstawowe materiały elektryczne.
                      Postępuj zgodnie z instrukcjami widocznymi na ekranie
            _________________________________________________________________________________
            """);
    }
    private void printMainMenu(){
        System.out.println("Wybierz, co chcesz zrobić");
        System.out.println("[1] - Stwórz nową listę materiałową");
        System.out.println("[2] - Zakończ");
    }
    private void handleAction(int action){
        try {
        switch (fromCode(action)) {
                case CREATE -> {
                    installationList.getInstallationList().clear();
                    PvModuleBuilder pvModuleBuilder = new PvModuleBuilder();
                    System.out.println("Podaj wymagane dane wejściowe wyświetlane w następnych wierszach");
                    System.out.println("Dane będą wykorzystane przy tworzeniu listy materiałowej");
                    System.out.println("Możesz dodać wiele instalacji. Gdy zakończysz dodawać instalacje, nie zapomnij zapisać listy do pliku");
                    System.out.println();
                    if (pvModule == null) {
                        pvModule = pvModuleBuilder.build();
                        if (pvModule != null) {
                            System.out.println("Moduł PV został poprawnie utworzony");
                        } else {
                            System.out.println("Nieoczekiwany błąd podczas tworzenia panela. Uruchom program ponownie");
                            System.exit(0);
                        }
                    }
                    InstallationBuilder installationBuilder = new InstallationBuilder(installationList);
                    installationList = installationBuilder.build(pvModule);
                }
                case EXIT -> shouldContinue = false;
            }
        }
        catch (IllegalArgumentException ex){
            System.out.println("Nieprawidłowa opcja menu. Wybierz [1] lub [2]");
        }
    }
}
