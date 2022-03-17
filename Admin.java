import java.io.*;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Admin {

    public static void masterVisit() throws IOException, ParseException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);

        System.out.println("\nMaster Visit History:\n");
        System.out.format("%-7s%-15s%-15s%-25s%-15s\n", "NO", "Date", "Time", "Customer", "Shop");
        
        sort();
    }

    public static void listOfCustomers() throws IOException, ParseException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);
        List<String> customerList =  new ArrayList<String>();
        Set<List<String>> customerSet = new HashSet<>();

        System.out.println("\nList of Customers:\n");
        System.out.format("%-7s%-20s%-15s%-15s\n", "NO", "Name", "Phone", "Status");

        try {
            for (List<String> item : csvList) {
                customerList.add(item.get(2));
                customerList.add(item.get(3));
                customerList.add(item.get(4));
            }

            int listSize = 3; // initialize every sub list's size and convert list into nested list
            for (int i=0; i<customerList.size(); i+=listSize) {
                customerSet.add(customerList.subList(i, Math.min(i + listSize, customerList.size())));
            }

            int no = 1; // remove duplicated customer
            for (List<String> subItem : customerSet) {
                System.out.format("%-7s%-20s%-15s%-15s\n", no, subItem.get(0), subItem.get(1), subItem.get(2));
                no++;
            }
            
            Main.continueKey();
            Menu.flagMenu();
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("\nError: Unable to read file. Make sure there is no empty row in your CSV file.");
            Main.continueKey();
            Menu.exitMenu();
        }
    }

    public static void listOfShop() throws IOException, ParseException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);
        List<String> shopList =  new ArrayList<String>();
        Set<List<String>> shopSet = new HashSet<>();

        System.out.println("\nList of Shops:\n");
        System.out.format("%-7s%-15s%-15s%-20s%-15s\n", "NO", "Name", "Phone", "Manager", "Status");

        try {
            for (List<String> item : csvList) {
                shopList.add(item.get(5));
                shopList.add(item.get(7));
                shopList.add(item.get(6));
                shopList.add(item.get(8));
            }
                
            int listSize = 4; // initialize every sub list's size and convert list into nested list
            for (int i=0; i<shopList.size(); i+=listSize) {
                shopSet.add(shopList.subList(i, Math.min(i + listSize, shopList.size())));
            }

            int no = 1; // remove duplicated shop
            for (List<String> subItem : shopSet) {
                System.out.format("%-7s%-15s%-15s%-20s%-15s\n", no, subItem.get(0), subItem.get(1), subItem.get(2), subItem.get(3));
                no++;
            }

            Main.continueKey();
            Menu.adminMainMenu();
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("\nError: Unable to read file. Make sure there is no empty row in your CSV file.");
            Main.continueKey();
            Menu.exitMenu();
        }
    }

    private static void sort() throws IOException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);
    
        try {
            // ======================== SORT BY DATE ========================
            int minIndexDate;
            for (int i = 0; i < csvList.size()-1; i++) {
                minIndexDate = i;
                for (int j = i + 1; j < csvList.size(); j++) {
                    // j < minIndexDate
                    int ans = csvList.get(minIndexDate).get(0).compareTo(csvList.get(j).get(0));
                    if (ans > 0) {
                        minIndexDate = j; // update the index
                    }
                }
                List<String> tempCsvList = csvList.get(minIndexDate);
                csvList.set(minIndexDate, csvList.get(i));
                csvList.set(i, tempCsvList);
            }
    
            // ======================== SORT BY TIME ========================
            int minIndex;
            for (int i = 0; i < csvList.size()-1; i++) {
                minIndex = i;
                for (int j = i + 1; j < csvList.size(); j++) {
                    // j < minIndex
                    if (csvList.get(minIndex).get(0).equals(csvList.get(j).get(0))){
                        int ans = csvList.get(minIndex).get(1).compareTo(csvList.get(j).get(1));
                        if (ans > 0) {
                            minIndex = j; // update the index
                        }
                        List<String> tempCsvList = csvList.get(minIndex);
                        csvList.set(minIndex, csvList.get(i));
                        csvList.set(i, tempCsvList);
                    }       
                }
            }
            int num = 1;
            for (List<String> item : csvList) {
                System.out.format("%-7s%-15s%-15s%-25s%-15s\n", num, item.get(0), item.get(1), item.get(2), item.get(5));
                num++;
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("\nError: Unable to read file. Make sure there is no empty row in your CSV file.");
            Main.continueKey();
            Menu.exitMenu();
        }
    }

    public static void randomGenerator() throws IOException {
        Random random = new Random();
        String randomDate, randomTime, randomVisitor, randomVisitorPhone, randomShop, defaultStatus, randomShopManager, randomShopPhone;
        List<String> randomHistory = new ArrayList<String>();
        List<List<String>> randomSubList = new ArrayList<>();

        for (int i=0; i<30; i++) { // loop 30 times for generating 30 history
            // ======================== RANDOM DATE ========================
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // date format
            LocalDate ytdDate = LocalDate.now().plusDays(-1); // use minus one to get yesterday's date from local date
            String formattedYtd = ytdDate.format(dateFormatter); // format date
            String formattedTdy = LocalDate.now().format(dateFormatter); // format date
            String[] dateList = {formattedYtd, formattedTdy}; // store yesterday's date & today's date into an array
            randomDate = dateList[random.nextInt(dateList.length)]; // randomly generate an index number to randomly select a date from ^array

            // ======================== RANDOM TIME ========================
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // time format
            LocalTime time = LocalTime.MIN.plusSeconds(random.nextLong());
            randomTime = time.format(timeFormatter);

            // ======================== RANDOM VISITOR ========================
            // create a string containing 10 visitors with name and repesctive phone number
            String visitorStr = "David Chang,0193859372,Jessica Yong,0162849302,Yong Jia Xin,0175038471," +
                                "Aamani Sharaf,0126385936,Avinash Rattan,0147391830,Wong Jun Jie,0109284526," + 
                                "Peter Clarke,0198374618,Tan Xin Yi,0164728394,Eric Nam,0183748102,Johnny Suh,0146273819";
            List<String> visitorList = Arrays.asList(visitorStr.split(",")); // split ^string at "," and convert into a list
            List<Integer> nameIdxList = Arrays.asList(0,2,4,6,8,10,12,14,16,18); // element in list indicates index of visitor's name
            int visitorIdx = random.nextInt(nameIdxList.size()); // randomly select a visitor's index number
            randomVisitor = visitorList.get(nameIdxList.get(visitorIdx)); // read the name of visitor from randomly selected index number
            randomVisitorPhone = visitorList.get((nameIdxList.get(visitorIdx))+1); // read the visitor's phone number by +1 to its name's index
            
            // ======================== DEFAULT STATUS ========================
            defaultStatus = "Normal"; // every customer & shop comes with default status = normal

            // ======================== RANDOM SHOP ========================
            // randomly select a shop by using the same way as randomly select a visitor
            String shopStr = "McDonald's,Chris Kempczinski,0322798799,Tesco,Ken Murphy,1300131313," +
                            "KFC,Roger Eaton,0322760701,AEON,Motoya Okada,0392072005"; 
            List<String> shopList = Arrays.asList(shopStr.split(","));
            List<Integer> shopIdxList = Arrays.asList(0,3,6,9);
            int shopIdx = random.nextInt(shopIdxList.size());
            randomShop = shopList.get(shopIdxList.get(shopIdx));
            randomShopManager = shopList.get((shopIdxList.get(shopIdx))+1);
            randomShopPhone = shopList.get((shopIdxList.get(shopIdx))+2);

            // add all random generated data into a final list 
            randomHistory.add(randomDate);
            randomHistory.add(randomTime);
            randomHistory.add(randomVisitor);
            randomHistory.add(randomVisitorPhone);
            randomHistory.add(defaultStatus);
            randomHistory.add(randomShop);
            randomHistory.add(randomShopManager);
            randomHistory.add(randomShopPhone);
            randomHistory.add(defaultStatus);
        }

        int listSize = 9; // initialize the size of list
        for (int i=0; i<randomHistory.size(); i+=listSize) {
            randomSubList.add(randomHistory.subList(i, Math.min(i + listSize, randomHistory.size()))); 
            // when reach the maximum size of the list, create another list
        }

        for (List<String> subItem : randomSubList) {
            FileWriter fw = new FileWriter("visitHistory.csv", true); // add new line into csv file
            fw.write("\n" + subItem.get(0) + "," + subItem.get(1) + "," + subItem.get(2) + "," + 
                    subItem.get(3) + "," + subItem.get(4) + "," + subItem.get(5) + "," + subItem.get(6) 
                    + "," + subItem.get(7) + "," + subItem.get(8));
            fw.close();
        }

        System.out.println("30 random visits have been successfully generated!");
    }

    public static void flag() throws IOException, ParseException {
        List<List<String>> csvList = new ArrayList<List<String>>();
        Main.readHistoryFromFile(csvList);

        File csvFile = new File("visitHistory.csv"); // the file to be updated

        // porompt input for choosing flag which customer
        Scanner input = new Scanner(System.in);
        System.out.println("Type \"0\" to exit.");
        System.out.print("Please enter the name of customer (case sensitive): ");
        String flagCus = input.nextLine(); // flagCus = the customer that will be flagged

        String date = "", time = "", shop = "";
        List<String> closeCus = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();

        for (List<String> item : csvList) {
            nameList.add(item.get(2)); // add all customer's names into a namelist
        }

        if (nameList.contains(flagCus)) { // if customer exists, continue
            for (int i=0; i<csvList.size(); i++) {
                if (flagCus.equals(csvList.get(i).get(2))) {
                    date = csvList.get(i).get(0);
                    time = csvList.get(i).get(1);
                    shop = csvList.get(i).get(5);

                    for (int j=0; j<csvList.size(); j++) {
                        if (csvList.get(j).get(5).equals(shop)) { // change shop's status to case
                            new updateCSV(csvFile).set(j, 8, "Case").save(csvFile); 
                        }
                        if (csvList.get(j).get(0).equals(date) && csvList.get(j).get(5).equals(shop)) { // if both date and shop are same with selected customer
                            int flagCusHour = Integer.parseInt(time.substring(0, 2)); // get HH from time string and convert into integer type (selected customer)
                            int flagCusMin = Integer.parseInt(time.substring(3, 5)); // get mm from time string and convert into integer type (selected customer)
                            int otherCusHour = Integer.parseInt(csvList.get(j).get(1).substring(0, 2)); // ^^same with above but for other customer who visited same shop
                            int otherCusMin = Integer.parseInt(csvList.get(j).get(1).substring(3, 5)); // ^^same with above but for other customer who visited same shop
                            int minusTime = flagCusHour - otherCusHour; // minus hour
                            int minusMin = flagCusMin - otherCusMin; // minus minutes
                            if (minusTime <= 1 && minusTime >= -1 && minusMin >= 0) { // if other customer's time is within one-hour-range
                                if (csvList.get(j).get(2).equals(flagCus)) { // change status for selected customer to "case"
                                    new updateCSV(csvFile).set(j, 4, "Case").save(csvFile); 
                                }
                                else { // change status for one-hour-range customer to "close"
                                    closeCus.add(csvList.get(j).get(2)); // get close customer's name and store into a list 
                                }
                            }
                        }
                    }
                }
            }
            for (String name : closeCus) {
                for (List<String> item:csvList) {
                    if (item.get(2).equals(name)) {
                        new updateCSV(csvFile).set((csvList.indexOf(item)), 4, "Close").save(csvFile); // change close customer's status to close
                    }
                }
            }
            System.out.println("Status has been successfully updated!");
        } 
        else if (flagCus.equals("0")){
            System.out.println("\nExit from Flag.");
            Menu.adminMainMenu();
        }
        
        else { // if customer does not exists
            System.out.println(flagCus + " does not exist.");
            Main.continueKey();
            Menu.flagMenu();
        }
    }
}