import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class Customer {
    public static String cusPhone;
    public static String cusName;

    public static void register() throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        cusPhone = "";
        boolean validInput = false;

        System.out.println("\n------------------------------------");
        System.out.println("               Register               ");
        System.out.println("------------------------------------");
        System.out.println("Type \"0\" to exit.");
        System.out.print("Enter your name: ");
        cusName = input.nextLine(); // get user's name as a string

        if (!cusName.equals("0")) {
            do {
                System.out.print("Enter your phone number: ");
                cusPhone = input.nextLine(); // store input as a string in order to read zero-started-integer
                
                if (cusPhone.equals("0")) {
                    System.out.println("\nExit from Registration.");
                    Menu.customerMainMenu();
                }
                else if (cusPhone.matches("[0-9]+")) { // to check if cusPhone contains only digits
                    validInput = true;
                }
                else {
                    System.out.println("Invalid input! Only numbers are allowed.\n");
                }
            } while (!validInput);
        }
        else {
            System.out.println("\nExit from Registration.");
            Menu.customerMainMenu();
        }

        System.out.println("\nSuccesfully registered!");

        // write data into a csv file for login purpose
        FileWriter fw = new FileWriter("registration.csv", true); //boolean append = true in order to add data to the end of csv file instead of beginning   
        fw.write("\n" + cusName + "," + cusPhone);    
        fw.close(); 
    }

    public static void signIn() throws IOException, ParseException {
        List<String> newCusCsv = Files.readAllLines(Paths.get("registration.csv")); // open csv file, one register = one element
        
        Scanner input = new Scanner(System.in);
        cusPhone = "";
        boolean validInput = false;

        System.out.println("\n------------------------------------");
        System.out.println("               Sign In              ");
        System.out.println("------------------------------------");
        System.out.println("Type \"0\" to exit.");
        System.out.print("Enter your name: ");
        cusName = input.nextLine(); // get user's name as a string

        if (!cusName.equals("0")) {
            do {
                System.out.print("Enter your phone number: ");
                cusPhone = input.nextLine(); // store input as a string in order to read zero-started-integer
                if (cusPhone.equals("0")) {
                    System.out.println("\nExit from Sign In.");
                    Menu.customerMainMenu();
                }
                else if (cusPhone.matches("[0-9]+")) { // to check if cusPhone contains only digits
                    validInput = true;
                }
                else {
                    System.out.println("Invalid input! Only numbers are allowed.\n");
                }
            } while (!validInput);
        }
        else {
            System.out.println("\nExit from Sign In.");
            Menu.customerMainMenu();
        }

        String loginData = cusName + "," + cusPhone; // combine two inputs into a string

        boolean found = false; // to check if the user already register or not
        for (int i=0; i<newCusCsv.size(); i++) {
            if (loginData.equals(newCusCsv.get(i))) {
                found = true;
            }
        }

        if (found) {
            System.out.println("\nSuccesfully login!");
            Main.continueKey();
            Menu.afterSignIn(cusName, cusPhone);
        }
        else {
            System.out.println("\nLogin failed! Please try again.");
            Main.continueKey();
            Menu.customerMainMenu();
        }
    }

    public static void viewHistory() throws IOException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);

        String status = "";
        int num = 1;
        System.out.println("\nHistory:\n");
        System.out.format("%-7s%-15s%-15s%-15s\n", "NO", "Date", "Time", "Shop");
        for (int i=0; i<csvList.size(); i++) {
            if (csvList.get(i).get(2).equals(cusName)){
                System.out.format("%-7s%-15s%-15s%-15s\n", num, csvList.get(i).get(0), csvList.get(i).get(1), csvList.get(i).get(5));
                status = csvList.get(i).get(4);
                num++;
            }
        }
        if (!(status.equals("Case")) && !(status.equals("Close")))
            System.out.println("\nCurrent status: Normal");
        else 
            System.out.println("\nCurrent status: " + status);
    }
}