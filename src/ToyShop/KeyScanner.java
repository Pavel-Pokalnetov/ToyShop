package ToyShop;

import java.util.Scanner;

public class KeyScanner {
    public static Scanner scanner = new Scanner(System.in);

    public static String getText() {
        return scanner.nextLine();
    }

    public static String getText(String s) {
        System.out.print(s);
        return getText();
    }
}
