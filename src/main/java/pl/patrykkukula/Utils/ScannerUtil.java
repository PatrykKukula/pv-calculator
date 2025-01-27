package pl.patrykkukula.Utils;

import java.util.Scanner;

public class ScannerUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readText(){
        return scanner.nextLine();
    }
    public static int readInt(){
            return Integer.parseInt(scanner.nextLine());
    }
    public static void closeScanner(){
        scanner.close();
    }
}
