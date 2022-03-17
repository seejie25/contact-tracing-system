import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * A class used to update particular cell in CSV file
 */
public class updateCSV {
    
    List<String[]> cells = new ArrayList<String[]>();

     /**
      * This constructor reads csv file row by row
      * and read every row as a string type.
      * Then break the string and add to a list.
      * 
      * @param csvFile file name
      */
    public updateCSV(File csvFile) {
        try (Scanner input = new Scanner(csvFile)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                cells.add(line.split(","));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("The system cannot find the file specified.");
        }
    }

    /**
     * Use setter to set the value in specified row and column.
     * 
     * @param row row number in csv file
     * @param col column number in csv file 
     * @param status the new status to be updated
     * @return return the value of row, col, status
     */
    public updateCSV set(int row, int col, String status) {
        (cells.get(row))[col] = status;
        return this;
    }

    /**
     * A method to save updated status into csv file.
     * 
     * @param csvFile file name
     * @throws FileNotFoundException fail to open file
     */
    public void save(File csvFile) throws FileNotFoundException {
        try (PrintWriter write = new PrintWriter(csvFile)) {
            for (String[] row : cells) {
                for (String cell : row) {
                    if (cell != row[0]) {
                        write.print(",");
                    }
                    write.print(cell);
                }
                write.println();
            }
        }
    }
}