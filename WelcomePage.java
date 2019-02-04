package voting;

import java.io.File;
import java.io.FileNotFoundException;
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
import java.io.FileInputStream; 
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import java.io.*;
import java.net.Socket;


/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class WelcomePage extends Application {
    //The following four variables are required to connect to the server.
    static final int port = 8888;
    static final String host = "172.20.10.3";
    static DataInputStream dis;
    static DataOutputStream dos;

    @Override
    public void start(Stage primaryStage) {
        //Creates a new GridPane.
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(0, 10, 0, 10));
        
        //Creates a new HBox.
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        
        //Creates a label for the title/header.
        Label title = new Label("Maple Leaf Voting System");
        title.setFont(new Font(50));
        title.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(title, 0.0);
        AnchorPane.setRightAnchor(title, 0.0);
        title.setAlignment(Pos.CENTER);
        title.setTextFill(Color.NAVY);
        title.setContentDisplay(ContentDisplay.TOP);
        title.setTextAlignment(TextAlignment.JUSTIFY);
        gridPane.add(title, 0, 2);

        //Creates a label for the text that introduces the voter to the online voting system.
        Label welcomeText = new Label();
        welcomeText.setText("Maple Leaf Online Voting enables voters to cast their vote privately and easily from "
                + "any location and on any device with Internet access (PC, tablet, smartphone, etc.), ensuring maximum election engagement by enabling remote "
                + "and disabled voters to participate on equal terms. Voter privacy, election integrity, end-to-end security, vote correctness and full verifiability "
                + "(individual and universal) are guaranteed via advanced cryptographic protocols. This enables election officials to assure citizens "
                + "that their votes remain cast-as-intended, recorded-as-cast and counted-as-recorded. In addition to the added accessibility and security, operational "
                + "efficiencies result from significantly reduced costs and the delivery of more timely and accurate results.");
        welcomeText.setTextAlignment(TextAlignment.JUSTIFY);
        welcomeText.setContentDisplay(ContentDisplay.TEXT_ONLY);
        welcomeText.setWrapText(true);
        welcomeText.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        welcomeText.setPadding(new Insets(100, 100, 0, 100));
        gridPane.add(welcomeText, 0, 3);
        
        //This creates a button that enables the voter to login if they have previously created an account.
        Button loginBtn = new Button("Login");
        //The EventHandler below will take the voter to the log in page once they click on it. 
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage loginStage = new Stage();
                Login login = new Login();
                login.start(loginStage);
                loginStage.show();
                primaryStage.close();
            }
        });
        loginBtn.setPrefSize(100, 20);
        loginBtn.setFont(Font.font(15));

        //This creates a button that enables the voter to sign up if they not have previously created an account.
        Button signUpBtn = new Button("Sign Up");
        //The EventHandler below will take the voter to the sign up page once they click on it. 
        signUpBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage signUpStage = new Stage();
                SignUp signUp = new SignUp();
                signUp.start(signUpStage);
                signUpStage.show();
                primaryStage.close();
            }
        });
        signUpBtn.setPrefSize(100, 0);
        signUpBtn.setFont(Font.font(15));

        //This creates a button that takes the voter to the graphs page.
        Button countVoteBtn = new Button("Get Votes");
        //The EventHandler below will show the voter the statistics of the 2019 Canadian Federal Election.
        countVoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String[] rawData = new String[9];
                int[] data = new int[9];
                try (Socket socket = connectToServer(port, host)) {
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream()); 
                    dos.writeUTF("count"); 
                    dos.flush(); 
                    rawData = dis.readUTF().split(" ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i=0; i<rawData.length; i++) {
                    data[i] = Integer.parseInt(rawData[i]);
                }
                Stage graphStage = new Stage();
                GraphsPage graphs = new GraphsPage(data);
                graphs.start(graphStage);
                graphStage.show();
                primaryStage.close();
            }
        });
        countVoteBtn.setPrefSize(100, 0);
        countVoteBtn.setFont(Font.font(15));
        
        //The lines of code below sets the style of the buttons and adds to the HBox, and then to the GridPane.
        loginBtn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
        signUpBtn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
        countVoteBtn.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        hbox.getChildren().addAll(loginBtn, signUpBtn, countVoteBtn);
        hbox.setPadding(new Insets(100, 0, 100, 0));
        hbox.setMaxWidth(Double.MAX_VALUE);
        hbox.setAlignment(Pos.CENTER);
        gridPane.add(hbox, 0, 4);

        //This following lines of code aligns the GridPane and sets the background colour for the GridPane.
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setBackground(Background.EMPTY);
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        //The following lines of code creates a new Scene, sets the title of the scene, and displays it when this program is run.
        Scene scene = new Scene(gridPane, 1500, 1000);
        primaryStage.setTitle("Welcome to Maple Leaf Voting System");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //This method searches the IP address and port number of the host and connect to it
    public static Socket connectToServer(int port, String host) {
        Socket socket;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            socket = null;
        }
        return socket;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
