package pl.patrykkukula.Utils.GeneralUtils;
import java.util.Scanner;

public class ScannerUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readText(){
        return scanner.nextLine();
    }
    public static int readInt(){
        while(true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            }
            catch (NumberFormatException ex) {
                System.out.println("Nieprawidłowy format danych. Wprowadź poprawną liczbę");
            }
        }
    }
    public static void closeScanner(){
        scanner.close();
    }
}
