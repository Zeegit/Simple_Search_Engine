package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "text.txt";

        for (int i = 0; i < args.length; i++) {
            if ("--data".equals(args[i]) && i + 1 < args.length) {
                fileName = args[i + 1];
            }
        }

        MenuInterface menu = new MenuInterface();
        menu.run(fileName);
    }
}
