package voting;

import javafx.scene.paint.Color;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.util.Optional;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;


/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class Login extends Application {
    //The following four variables are required to connect to the server.
    static final int port = 8888;
    static final String host = "172.20.10.3";
    static DataInputStream dis;
    static DataOutputStream dos;

    @Override
    public void start(Stage loginStage) {
        //Creates a new GridPane.
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        
        //Creates a label for the title/header
        Label title = new Label("Login");
        title.setFont(new Font(25));
        title.setTextFill(Color.NAVY);
        title.setContentDisplay(ContentDisplay.TOP);
        title.setTextAlignment(TextAlignment.JUSTIFY);
        title.setPadding(new Insets(10, 0, 0, 270));
        gridPane.add(title, 0, 0);
        
        //Creates a new HBox.
        HBox hbox1 = new HBox();
        hbox1.setSpacing(10);
        
        //Creates a new HBox.
        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);
        
        //Creates a new HBox.
        HBox hbox3 = new HBox();
        hbox3.setSpacing(10);
        
        //Creates a label for the username of the voter.
        Label username = new Label("Username: ");
        username.setPadding(new Insets(0, 0, 0, 175));
        
        //Creates a TextField where the user can input their username.
        TextField usernameField = new TextField();
        hbox1.getChildren().addAll(username, usernameField);
        gridPane.add(hbox1, 0, 2);
        
        //Creates a label for the password of the voter.
        Label password = new Label("Password: ");
        password.setPadding(new Insets(0, 0, 0, 175));
        gridPane.add(password, 0, 3);
        
        //Creates a PasswordField where the user can input their password.
        PasswordField passwordField = new PasswordField();
        hbox2.getChildren().addAll(password, passwordField);
        gridPane.add(hbox2, 0, 4);

        //Creates a button that enables the user to login and vote. The voter will only be able to vote once their username and password is verified.
        Button login = new Button();
        login.setText("Login");
        login.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
        login.setPrefSize(100, 20);
        login.setFont(Font.font(15));
        
        //The EventHandler below will take the voter to EnterCodePage.java only if the voter's username and password match the username and password they created when they signed up. 
        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String message = hash(usernameField.getText()) + " " + hash(passwordField.getText());
                String serverFeedback = "";
                try (Socket socket = connectToServer(port, host)) {
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(message);
                    dos.flush();
                    serverFeedback = dis.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (serverFeedback.equals("0")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Check Your Email!");
                    alert.setContentText("We have sent a six digit code to your email. Please enter that code for authentication.");
                    Optional <ButtonType> button = alert.showAndWait();

                    Stage enterCodeStage = new Stage();
                    EnterCodePage enterCode = new EnterCodePage();
                    enterCode.start(enterCodeStage);
                    enterCodeStage.show();
                    loginStage.close();
                } else if (serverFeedback.equals("1")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Incorrect Information!");
                    alert.setContentText("Username or Password is incorrect! Please try again.");
                    Optional<ButtonType> button = alert.showAndWait();
                }
            }
        });

        //Adds the login button to the HBox, which then gets added to the GridPane.
        hbox3.getChildren().add(login);
        hbox3.setPadding(new Insets(75, 75, 0, 250));
        gridPane.add(hbox3, 0, 5);
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        //The following lines of code creates a new Scene, sets the title of the scene, and displays it when this program is run.
        Scene scene = new Scene(gridPane, 600, 300);
        loginStage.setTitle("Maple Leaf Online Voting System");
        loginStage.setScene(scene);
        loginStage.show();
    }

    //The method below searches the IP address and port number of the host and connects to it
    public static Socket connectToServer(int port, String host) {
        // Search the IP address and port number of the host and connect to it
        Socket socket;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            socket = null;
        }
        return socket;
    }

    //This method hashes the voter's information using SHA-256, a cryptographic hashing algorithm.
    public static String hash(String text) {
        String hex = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            hex = String.format("%064x", new BigInteger(1, digest));
      
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hex;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}