import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Menu.mainMenu();
    }

    public static void readHistoryFromFile(List<List<String>>csvList) throws IOException {
        // read all data from visitHistory.csv into a list
        BufferedReader br = new BufferedReader(new FileReader("visitHistory.csv"));
            String read = null;
            while ((read = br.readLine()) != null) {
                if (read.trim().isEmpty()) { // ignore empty rows in csv file
                    continue;
                }
                String[] splited = read.split(",");
                List<String> csvColumns = new ArrayList<String>();
                for (String item : splited) {
                    csvColumns.add(item);
                }
                csvList.add(csvColumns);
            }
    }

    public static Integer getIntInput(Integer selection, Integer max) {
        Scanner input = new Scanner(System.in);
        do {
            try {
                System.out.print("Enter a selection between 1-" + max + ": ");
                selection = input.nextInt();
                if (selection < 1 || selection > max) {
                    System.out.println("Invalid input! Please try again.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please try again.\n");
            }
            input.nextLine();
        } while (selection < 1 || selection > max);
        return selection;
    }

    public static void continueKey() {
        System.out.print("\nPress Enter key to continue.");
        try {
            System.in.read();
        }
        catch (Exception e) {}
    }
}