import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    public static void mainMenu() throws IOException, ParseException {
        System.out.println("\n------------------------------------");
        System.out.println("              Main Menu             ");
        System.out.println("------------------------------------");
        System.out.println("| 1. Customer                      |");
        System.out.println("| 2. Shop                          |");
        System.out.println("| 3. Admin                         |");
        System.out.println("| 4. Exit Program                  |");
        System.out.println("------------------------------------");

        System.out.print("What is your role? ");
        int selection = 0;
        selection = Main.getIntInput(selection, 4);

        switch (selection) {
            case 1: {
                customerMainMenu();
            }
                break;
            case 2: {
                shopMainMenu();
            }
                break;
            case 3: {
                adminMainMenu();
            }
                break;
            case 4: {
                exitMenu();
            }
        }
    }

    public static void customerMainMenu() throws IOException, ParseException {
       System.out.println("\n------------------------------------");
        System.out.println("         Customer Main Menu         ");
        System.out.println("------------------------------------");
        System.out.println("| 1. Register an Account           |");
        System.out.println("| 2. Sign In                       |");
        System.out.println("| 3. Back to Main Menu             |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 3);

        switch (selection) {
            case 1: {
                Customer.register();
                Main.continueKey();
                customerMainMenu();
            }
                break;
            case 2: {
                Customer.signIn();
            }
                break;
            case 3: {
                mainMenu();
            }
                break;
        }
    }

    public static void afterSignIn(String Name, String Phone) throws IOException, ParseException {
        System.out.println("\n------------------------------------");
        System.out.println("| 1. Check In                      |");
        System.out.println("| 2. View Status and History       |");
        System.out.println("| 3. Back to Customer Main Menu    |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 3);

        switch (selection) {
            case 1: {
                checkInMenu(Name, Phone);
            }
                break;
            case 2: {
                Customer.viewHistory();
                Main.continueKey();
                afterSignIn(Name, Phone);
            }
                break;
            case 3: {
                customerMainMenu();
            }
                break;
        }
    }

    private static void checkInMenu(String Name, String Phone) throws IOException, ParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.println("\n------------------------------------");
        System.out.println("              Check In              ");
        System.out.println("------------------------------------");
        System.out.println("| 1. AEON                          |");
        System.out.println("| 2. Tesco                         |");
        System.out.println("| 3. KFC                           |");
        System.out.println("| 4. McDonald's                    |");
        System.out.println("| 5. Back to Previous Page         |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 5);

        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);

        if (selection > 0 && selection < 5) {
            String[] newData = new String[9];
                newData[0] = dateFormatter.format(LocalDate.now());
                newData[1] = timeFormatter.format(LocalTime.now());
                newData[2] = Name;
                newData[3] = Phone;
                newData[4] = "Normal";
                newData[5] = csvList.get(selection-1).get(5);
                newData[6] = csvList.get(selection-1).get(6);
                newData[7] = csvList.get(selection-1).get(7);
                newData[8] = csvList.get(selection-1).get(8);

                FileWriter fw = new FileWriter("visitHistory.csv", true); // boolean append = true in order to add data to
                                                                        // the end of csv file instead of beginning
                fw.write("\n" + newData[0] + "," + newData[1] + "," + newData[2] + "," + newData[3] + "," + newData[4]
                        + "," + newData[5] + "," + newData[6] + "," + newData[7] + "," + newData[8]);
                fw.close();

                System.out.println("Successfully checked in!");
                Main.continueKey();
                checkInMenu(Name, Phone);
        }
        else if (selection == 5) {
            afterSignIn(Name, Phone);
        }
    }

    private static void shopMainMenu() throws IOException, ParseException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);

        System.out.println("\n------------------------------------");
        System.out.println("           Shop Main Menu           ");
        System.out.println("------------------------------------");
        System.out.println("| 1. AEON                          |");
        System.out.println("| 2. Tesco                         |");
        System.out.println("| 3. KFC                           |");
        System.out.println("| 4. McDonald's                    |");
        System.out.println("| 5. Back to Main Menu             |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 5);

        if (selection > 0 && selection < 5) {
            System.out.format("\n%-15s%-15s%-10s%-15s\n", "Name", "Phone", "Status", "Manager");
            System.out.format("%-15s%-15s%-10s%-15s\n", csvList.get(selection-1).get(5), csvList.get(selection-1).get(7),
                        csvList.get(selection-1).get(8), csvList.get(selection-1).get(6));
            Main.continueKey();
            shopMainMenu();
        }
        else if (selection == 5) {
            mainMenu();
        }
    }

    public static void adminMainMenu() throws IOException, ParseException {
        System.out.println("\n------------------------------------");
        System.out.println("           Admin Main Menu          ");
        System.out.println("------------------------------------");
        System.out.println("| 1. Master Visit History          |");
        System.out.println("| 2. List of Customers             |");
        System.out.println("| 3. List of Shops                 |");
        System.out.println("| 4. Back to Main Menu             |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 4);

        switch (selection) {
            case 1: {
                Admin.masterVisit();
                Main.continueKey();
                Menu.randomMenu();
            }
                break;
            case 2: {
                Admin.listOfCustomers();
            }
                break;
            case 3: {
                Admin.listOfShop();
            }
                break;
            case 4: {
                mainMenu();
            }
        }
    }

    public static void randomMenu() throws IOException, ParseException {
        System.out.println("\n------------------------------------");
        System.out.println("| 1. Add 30 Random Visits          |");
        System.out.println("| 2. Back to Admin Main Menu       |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 2);

        switch (selection) {
            case 1: {
                Admin.randomGenerator();
                Main.continueKey();
                adminMainMenu();
            }
                break;
            case 2: {
                adminMainMenu();
            }
                break;
        }
    }

    public static void flagMenu() throws IOException, ParseException {
        System.out.println("\n------------------------------------");
        System.out.println("| 1. Flag a Customer               |");
        System.out.println("| 2. Back to Admin Main Menu       |");
        System.out.println("------------------------------------");

        int selection = 0;
        selection = Main.getIntInput(selection, 2);

        switch (selection) {
            case 1: {
                Admin.flag();
                Main.continueKey();
                Menu.adminMainMenu();
            } break;
            case 2: {
                adminMainMenu();
            } break;
        }
    }

    public static void exitMenu() {
        System.out.println("\n------------------------------------");
        System.out.println("|            THANK YOU!            |");
        System.out.println("------------------------------------");
        Main.continueKey();
        System.exit(0); 
    }
}