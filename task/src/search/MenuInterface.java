package search;

import java.util.Scanner;

interface FindingStrategy {
     public String[] findPeoples(Peoples p, String text);
}

class FindingStrategy_ALL implements FindingStrategy {
    @Override
    public String[] findPeoples(Peoples p, String text) {
        return p.getSelectedPeoples_ALL(text);
    }
}

class FindingStrategy_ANY implements FindingStrategy {
    @Override
    public String[] findPeoples(Peoples p, String text) {
        return p.getSelectedPeoples_ANY(text);
    }
}

class FindingStrategy_NONE implements FindingStrategy {
    @Override
    public String[] findPeoples(Peoples p, String text) {
        return p.getSelectedPeoples_NONE(text);
    }
}

class FindingMetod {
    private FindingStrategy strategy;

    public void setMethod(FindingStrategy strategy) {
        this.strategy = strategy;
    }

    public String[] find(Peoples p, String text) {
        return this.strategy.findPeoples(p, text);
    }
}

public class MenuInterface {
    Peoples p;
    Scanner scanner;


    public MenuInterface() {
        p = new Peoples();
        scanner = new Scanner(System.in);
    }



    void run(String fileName) {
        p.loadFromFile(fileName);
        boolean isExit = false;
        String menu;
        do {
            menu = menu();
            switch (menu) {
                case "1":
                    printSelectedPeople();
                    break;
                case "2":
                    printAllPeople();
                    break;
                case "0":
                    isExit = true;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }

        } while (!isExit);

        System.out.println("Bye!");
    }

    String menu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
        return scanner.nextLine();
    }

    void printAllPeople() {
        System.out.println();
        System.out.println("=== List of people ===");
        String[] all = p.getAllPeoples();
        for (int i = 0; i < all.length; i++) {
            System.out.println(all[i]);
        }
        System.out.println();
    }

    void printSelectedPeople() {
        System.out.println();

        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = scanner.nextLine().toUpperCase();

        FindingMetod fmetod = new FindingMetod();

        switch (strategy) {
            case "ALL":
                fmetod.setMethod(new FindingStrategy_ALL());
                break;
            case "ANY":
                fmetod.setMethod(new FindingStrategy_ANY());
                break;
            case "NONE":
                fmetod.setMethod(new FindingStrategy_NONE());
                break;
            default:
                break;
        }

        System.out.println("Enter a name or email to search all suitable people.");
        String text = scanner.nextLine().toUpperCase();

        String[] all = fmetod.find(p, text);
        if (all != null) {
            System.out.println(all.length + " persons found:");
            for (int i = 0; i < all.length; i++) {
                System.out.println(all[i]);
            }
        } else {
            System.out.println("No matching people found.");

        }
        System.out.println();
    }
}
