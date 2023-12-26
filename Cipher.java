//Khloud Homiid Alsofyani - 1906275 - CPCS425 - Encrypted Files Application project

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.List;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class Cipher extends Application {

    // labels for GUI messages ...
    private Label messageLabel, messageLabe2;

//Encryption method .............
    public static String Encryption(String line) {
        // Step 1: Remove leading and trailing whitespace
        line = line.trim();

        // Step 2: Convert to uppercase
        line = line.toUpperCase();

        // Step 3: Move first half to last half
        int length = line.length();
        int halfLength = length / 2;
        String firstHalf = line.substring(0, halfLength + length % 2);
        String lastHalf = line.substring(halfLength + length % 2);
        line = lastHalf + firstHalf;

        // Step 4: Swap first 2 characters with last 2 characters
        char[] chars = line.toCharArray();
        char temp = chars[0];
        chars[0] = chars[length - 2];
        chars[length - 2] = temp;
        temp = chars[1];
        chars[1] = chars[length - 1];
        chars[length - 1] = temp;
        line = new String(chars);

        // Step 5: Swap characters around the middle
        int mid = length / 2;
        temp = chars[mid - 2];
        chars[mid - 2] = chars[mid];
        chars[mid] = temp;
        temp = chars[mid - 1];
        chars[mid - 1] = chars[mid + 1];
        chars[mid + 1] = temp;
        line = new String(chars);

        // Step 6: Perform character substitutions
        line = line.replace("A", "@")
                .replace("E", "=")
                .replace("I", "!")
                .replace("J", "?")
                .replace("O", "*")
                .replace("P", "#")
                .replace("R", "&")
                .replace("S", "$")
                .replace("T", "+")
                .replace("V", "^")
                .replace("X", "%")
                .replace(" ", "_");

        return line;
    }

//if the user select Encrypt...........
    private void EncryptionFile(BufferedReader inStream, PrintWriter outStream) throws IOException {
        String line;
        while ((line = inStream.readLine()) != null) {
            // Write the encrypted line to the output stream
            outStream.println(Encryption(line));
        }
    }

//Decryption method .............
    private String Decryption(String line) {
        // Step 1: Convert all letters to uppercase
        line = line.toUpperCase();

        // Step 2: Move the first half of the string to be the last half
        int length = line.length();
        int halfLength = length / 2;
        String lastHalf = line.substring(0, halfLength);
        String firstHalf = line.substring(halfLength);
        if (length % 2 != 0) {
            firstHalf = firstHalf + line.charAt(length - 1);
        }
        line = firstHalf + lastHalf;

        // Step 3: Swap the first 2 characters with the last 2 characters
        char[] chars = line.toCharArray();
        char temp = chars[0];
        chars[0] = chars[length - 2];
        chars[length - 2] = temp;
        temp = chars[1];
        chars[1] = chars[length - 1];
        chars[length - 1] = temp;
        line = new String(chars);

        // Step 4: Swap the two characters immediately to the right of the middle with the two characters that immediately precede them
        int middle = length / 2;
        temp = chars[middle - 2];
        chars[middle - 2] = chars[middle];
        chars[middle] = temp;
        temp = chars[middle - 1];
        chars[middle - 1] = chars[middle + 1];
        chars[middle + 1] = temp;
        line = new String(chars);

        // Step 4: Remove leading and trailing whitespace
        line = line.trim();

        // Step 5: Perform character substitutions
        line = line.replace("@", "A")
                .replace("=", "E")
                .replace("!", "I")
                .replace("?", "J")
                .replace("*", "O")
                .replace("#", "P")
                .replace("&", "R")
                .replace("$", "S")
                .replace("+", "T")
                .replace("^", "V")
                .replace("%", "X")
                .replace("_", " ");

        return line.toLowerCase();
    }

//if the user select Decrypt
    private void DecryptionFile(BufferedReader inStream, PrintWriter outStream) throws IOException {
        String line;
        while ((line = inStream.readLine()) != null) {
            // display the decrypted line to the output stream
            outStream.println(Decryption(line));
        }
    }

//if the user select Exit
    private void exit() {
        showAlert("Bye!", "-fx-background-color: #DEBE5E;", 400, 200);
        System.exit(0);
    }

    //GUI design ............
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Encrypted Files Application");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-background-color: #E4DDD4; -fx-padding: 60;");

        messageLabe2 = new Label("Welcome to Encrypted Files Application");
        messageLabe2.setStyle("-fx-text-fill: black;");
        messageLabel = new Label("Please Select Button");
        messageLabel.setStyle("-fx-text-fill: black;");

        Button encryptFile = new Button("1- Encrypt Files");
        encryptFile.setStyle("-fx-background-color: #72B7BC; -fx-text-fill: white;");
        encryptFile.setOnAction(e -> handleButtonAction(1));

        Button decryptFile = new Button("2- Decrypt Files");
        decryptFile.setStyle("-fx-background-color: #D89B8B; -fx-text-fill: white;");
        decryptFile.setOnAction(e -> handleButtonAction(2));

        Button exit = new Button("3- Exit");
        exit.setStyle("-fx-background-color: #DEBE5E; -fx-text-fill: white;");
        exit.setOnAction(e -> exit());

        vbox.getChildren().addAll(messageLabe2, messageLabel, encryptFile, decryptFile, exit);

        Scene scene = new Scene(vbox, 500, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    // Open window for choosing input file .............
    private void handleButtonAction(int choice) {
        FileChooser fileChooser = new FileChooser();

        // Choose input file
        fileChooser.setTitle("Select an Input File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        Window primaryStage = null;

        List<java.io.File> selectedInputFiles = fileChooser.showOpenMultipleDialog(primaryStage);

        if (selectedInputFiles != null && !selectedInputFiles.isEmpty()) {
            for (java.io.File selectedInputFile : selectedInputFiles) {
                try {
                    BufferedReader inStream = new BufferedReader(new FileReader(selectedInputFile));

// Choose the destination of the output file
                    fileChooser.setTitle("Select the destination");
                    fileChooser.getExtensionFilters().clear(); // Clear previous filters
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

// Set the default file name for the output files
                    String defaultFileName;
                    if (choice == 1) {
                        defaultFileName = "Cipher.txt";
                    } else if (choice == 2) {
                        defaultFileName = "Decrypt.txt";
                    } else {
                        defaultFileName = "Default.txt";
                    }

                    fileChooser.setInitialFileName(defaultFileName);

                    java.io.File selectedOutputFile = fileChooser.showSaveDialog(primaryStage);
                    if (selectedOutputFile != null) {
                        PrintWriter outStream = new PrintWriter(new FileWriter(selectedOutputFile));

                        // The user's choice
                        if (choice == 1) {
                            EncryptionFile(inStream, outStream);
                            showAlert("The file was encrypted", "-fx-background-color: #72B7BC;", 400, 200);
                        } else if (choice == 2) {
                            DecryptionFile(inStream, outStream);
                            showAlert("The file was decrypted", "-fx-background-color: #D89B8B;", 400, 200);
                        }
                        if (choice == 3) {
                            exit();
                        }

                        inStream.close();
                        outStream.close();
                    } else {
                        showAlert("No output file selected.", "-fx-background-color: #DEBE5E;", 400, 200);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("No input file selected.", "-fx-background-color: #DEBE5E;", 400, 200);
        }
    }

    // information Gui windows after compelete the process ..............
    private void showAlert(String message, String style, int par, int par1) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Information");
        dialog.setHeaderText(null);

        // Set the style directly to the dialog
        dialog.getDialogPane().setStyle(style);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: black;"); // Set text color if needed

        // Adjust the size of the DialogPane content
        dialog.getDialogPane().setContent(label);
        dialog.getDialogPane().setMinSize(400, 200);

        // Handle the result of the dialog, if necessary
        dialog.setResultConverter(dialogButton -> null);

        dialog.showAndWait();
    }

    // main ...................
    public static void main(String[] args) {
        launch(args);
    }
}
