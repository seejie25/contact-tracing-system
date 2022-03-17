package javafx.src;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class registerLogin extends Application {

    private List<String> readCsv() {
        List<String> csvList = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("registration.csv"));
            String read = null;
            while ((read = br.readLine()) != null) {
                String[] splited = read.split(",");
                for (String item : splited) {
                    csvList.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Error.");
        }
        return csvList;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration / Login");
        GridPane gridPane = form();
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane form() {
        GridPane gridPane = new GridPane();

        Label header = new Label("Welcome!");
        header.setFont(Font.font("Arail", 20));
        gridPane.addRow(0, header);
        
        Label name = new Label("Name");
        Label phone = new Label("Phone Number");
        TextField inputName = new TextField();
        TextField inputPhone = new TextField();
        inputName.setPrefWidth(300);
        Button submitBut = new Button("Submit");
        Label done = new Label();

        gridPane.setPadding(new Insets(50, 50, 80, 50));    
        gridPane.addRow(1, name, inputName);
        gridPane.addRow(2, phone, inputPhone);
        gridPane.add(submitBut, 0,3,2,1);
        gridPane.add(done, 0,4,2,1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(20);
        GridPane.setHalignment(submitBut, HPos.CENTER);
        GridPane.setHalignment(done, HPos.CENTER);
        GridPane.setMargin(submitBut, new Insets(10, 0,0,0));

        submitBut.setOnAction(e -> {
            List<String> csvList = readCsv();
            int index = csvList.indexOf(inputName.getText());
            if (csvList.contains(inputName.getText()) && (csvList.get(index+1).equals(inputPhone.getText()))) {
                done.setText("Hi " + inputName.getText() + ", you have login successfully!");
            }
            else if (csvList.contains(inputName.getText()) && (!(csvList.get(index+1).equals(inputPhone.getText())))) {
                done.setText("Wrong phone number! Please try again.");
            }
            else if (inputName.getText().equals("") && inputPhone.getText().equals("")) {
                done.setText("Please enter your name and your phone number.");
            }
            else if (inputName.getText().equals("")) {
                done.setText("Please enter your name.");
            }
            else if (inputPhone.getText().equals("")) {
                done.setText("Please enter phone number.");
            }
            else if (!(inputPhone.getText().matches("[0-9]+"))) {
                done.setText("Invalid input! Only numbers are allowed for phone number field.");
            }
            else if (!(csvList.contains(inputName.getText())) && inputPhone.getText().matches("[0-9]+")) {
                done.setText("Hi " + inputName.getText() + ", you have registered successfully!");
                FileWriter fw;
                try {
                    fw = new FileWriter("registration.csv", true);
                    fw.write("\n" + inputName.getText() + "," + inputPhone.getText());
                    fw.close();
                    readCsv();
                } catch (IOException e1) {
                    System.out.println("Please enter valid input.");
                }
            }
        });
        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}