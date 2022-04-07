package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SearchEngine {

    private final Scanner consoleScan;
    private Map<String, Set<Integer>> indexData;
    private Map<Integer, String> sourceData;
    private final String filePath;
    private SearchStrategy strategy;

    public SearchEngine(String[] arguments) {
        this.filePath = getPath(arguments);
        consoleScan = new Scanner(System.in);
    }

    private String getPath(String[] arguments) {
        String argument = "--data";
        if (argument.equals(arguments[0])) {
            return arguments[1];
        } else {
            throw new RuntimeException("No file name");
        }
    }

    public void start() {
        indexData = createData();
        showMenu();
    }

    private void showMenu() {
        int num = 0;
        do {
            printMenu();
            try {
                num = consoleScan.nextInt();
                consoleScan.nextLine();
                chooseMenu(num);
            } catch (Exception e) {
                System.out.println("\nIncorrect option! Try again.");
            }
        } while (num > 0);
    }

    private void chooseMenu(int number) {
        switch (number) {
            case 0:
                System.out.println("\nBye!");
                break;
            case 1:
                findData();
                break;
            case 2:
                printData();
                break;
            default:
                System.out.println("\nIncorrect option! Try again.");
        }
    }

    private void printMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    private void findData() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        strategy = getSearchStrategy(consoleScan.nextLine());
        System.out.println("\nEnter a name or email to search all suitable people.");
        String requestData = consoleScan.nextLine().trim().toLowerCase();
        printData(strategy.getRequestData(requestData, indexData, sourceData));
    }

    private SearchStrategy getSearchStrategy(String strategy) {
        switch(strategy) {
            case("ANY"):
                return new AnyStrategy();
            case("NONE"):
                return new NoneStrategy();
            default:
                return new AllStrategy();
        }
    }

    private Map<String, Set<Integer>> createData() {
        sourceData = getData(filePath);
        Map<String, Set<Integer>> mapData = new HashMap<>();
        for (int i = 0; i < sourceData.size(); i++) {
            String[] lines = sourceData.get(i).toLowerCase().split(" ");
            for (String s : lines) {
                if (mapData.containsKey(s)) {
                    mapData.get(s).add(i);
                } else {
                    Set<Integer> set = new HashSet<>();
                    set.add(i);
                    mapData.put(s, set);
                }
            }
        }
        return mapData;
    }

    private void printData(Set<String> requestData) {
        if (!requestData.isEmpty()) {
            System.out.println(requestData.size() + " persons found:");
            for (String data : requestData) {
                System.out.println(data);
            }
        } else {
            System.out.println("No matching people found.");
        }
    }

    private void printData() {
        System.out.println("\n=== List of people ===");
        for (int i = 0; i < sourceData.size(); i++) {
            System.out.println(sourceData.get(i));
        }
    }

    public Map<Integer, String> getData(String path) {
        File file = new File(path);
        Map<Integer, String> map = new HashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            int index = 0;
            while (scanner.hasNext()) {
                map.put(index, scanner.nextLine().trim());
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
