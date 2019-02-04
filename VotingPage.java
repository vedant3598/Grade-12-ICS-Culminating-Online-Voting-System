package voting;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
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
public class VotingPage extends Application{
    //The following four variables are required to connect to the server.
    static final int port = 8888;
    static final String host = "172.20.10.3";
    static final String dbasePath = "../../Database/";
    static DataOutputStream dos;
    
    public void start(Stage votingStage){
        //Creates a new GridPane
        GridPane gridPane = new GridPane();        
        gridPane.setStyle("-fx-background-color: #C0C0C0");
        gridPane.setVgap(15);

        //Creates a label for the title/header
        Label headerLabel = new Label("2019 Canada Federal Election Candidates");
        headerLabel.setFont(Font.font(30));
        headerLabel.setTextFill(Color.NAVY);
        headerLabel.setPadding(new Insets(0, 0, 0, 500));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        
        //Creates a label for the subtitle
        Label subTitle = new Label("Welcome voter! Below is a list of candidates running to be Canada's Prime Minister from 2019 to 2023. Please choose the candidate you "
                + "wish to vote for and proceed by clicking the 'Cast Vote' button below!");
        subTitle.setFont(Font.font(20));
        subTitle.setTextFill(Color.NAVY);
        subTitle.setWrapText(true);
        subTitle.setAlignment(Pos.CENTER);
        subTitle.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(subTitle, 0, 1);
        
        final ToggleGroup group = new ToggleGroup();
        
        Label emptyLabel = new Label("");
        gridPane.add(emptyLabel, 0, 2);
        
        //Creates a new HBox
        HBox hbox1 = new HBox();
        hbox1.setSpacing(10);  
        
        //Creates a label
        Label header = new Label("Candidates");
        header.setTextFill(Color.NAVY);
        header.setUnderline(true);
        header.setFont(Font.font(20));
        header.setPadding(new Insets(0, 25, 0, 700));
        hbox1.getChildren().addAll(header);
        gridPane.add(hbox1, 0, 3);
        
        //Creates a new HBox
        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);
        
        //Creates a label for candidate number 1
        Label candidate1Label = new Label("Liberal Party of Canada          ");
        candidate1Label.setTextFill(Color.NAVY);
        candidate1Label.setPadding(new Insets(0, 0, 0, 550));
        candidate1Label.setFont(Font.font(20));
        
        //Creates a radiobutton for candidate number 1
        RadioButton candidate1 = new RadioButton("Justin Trudeau");
        candidate1.setTextFill(Color.NAVY);
        candidate1.setFont(Font.font(20));
        candidate1.setUserData("Justin Trudeau");
        candidate1.setToggleGroup(group);
        hbox2.getChildren().addAll(candidate1Label, candidate1);
        gridPane.add(hbox2, 0, 4);

        HBox hbox3 = new HBox();
        hbox3.setSpacing(10);
        
        //Creates a label for candidate number 2
        Label candidate2Label = new Label("Conservative Party of Canada");
        candidate2Label.setTextFill(Color.NAVY);
        candidate2Label.setPadding(new Insets(0, 0, 0, 550));
        candidate2Label.setFont(Font.font(20));
        
        //Creates a radiobutton for candidate number 2
        RadioButton candidate2 = new RadioButton("Andrew Scheer");
        candidate2.setTextFill(Color.NAVY);
        candidate2.setFont(Font.font(20));
        candidate2.setUserData("Andrew Scheer");
        candidate2.setToggleGroup(group);
        hbox3.getChildren().addAll(candidate2Label, candidate2);
        gridPane.add(hbox3, 0, 5);
        
        HBox hbox4 = new HBox();
        hbox4.setSpacing(10);
        
        //Creates a label for candidate number 3
        Label candidate3Label = new Label("New Democratic Party           ");
        candidate3Label.setTextFill(Color.NAVY);
        candidate3Label.setPadding(new Insets(0, 0, 0, 550));
        candidate3Label.setFont(Font.font(20));
        
        //Creates a radiobutton for candidate number 3
        RadioButton candidate3 = new RadioButton("Jagmeet Singh");
        candidate3.setTextFill(Color.NAVY);
        candidate3.setFont(Font.font(20));
        candidate3.setUserData("Jagmeet Singh");
        candidate3.setToggleGroup(group);
        hbox4.getChildren().addAll(candidate3Label, candidate3);
        gridPane.add(hbox4, 0, 6);
        
        HBox hbox5 = new HBox();
        hbox5.setSpacing(10);

        //Creates a label for candidate number 4
        Label candidate4Label = new Label("Bloc Québécois                      ");
        candidate4Label.setTextFill(Color.NAVY);
        candidate4Label.setPadding(new Insets(0, 0, 0, 550));
        candidate4Label.setFont(Font.font(20));
        
        //Creates a radiobutton for candidate number 4
        RadioButton candidate4 = new RadioButton("Mario Beaulieu");
        candidate4.setTextFill(Color.NAVY);
        candidate4.setFont(Font.font(20));
        candidate4.setUserData("Mario Beaulieu");
        candidate4.setToggleGroup(group);
        hbox5.getChildren().addAll(candidate4Label, candidate4);
        gridPane.add(hbox5, 0, 7);
        
        HBox hbox6 = new HBox();
        hbox6.setSpacing(10);

        //Creates a label for candidate number 5
        Label candidate5Label = new Label("Green Party of Canada           ");
        candidate5Label.setTextFill(Color.NAVY);
        candidate5Label.setPadding(new Insets(0, 0, 0, 550));
        candidate5Label.setFont(Font.font(20));
        
        //Creates a radiobutton for candidate number 5
        RadioButton candidate5 = new RadioButton("Elizabeth May");
        candidate5.setTextFill(Color.NAVY);
        candidate5.setFont(Font.font(20));
        candidate5.setUserData("Elizabeth May");
        candidate5.setToggleGroup(group);
        hbox6.getChildren().addAll(candidate5Label, candidate5);
        gridPane.add(hbox6, 0, 8);
//        
        HBox hbox7 = new HBox();
        hbox7.setSpacing(10);
        
        //Creates a label for candidate number 6
        Label candidate6Label = new Label("People's Party of Canada       ");
        candidate6Label.setTextFill(Color.NAVY);
        candidate6Label.setPadding(new Insets(0, 0, 0, 550));
        candidate6Label.setFont(Font.font(20));
        
        //Creates a radiobutton for candidate number 6
        RadioButton candidate6 = new RadioButton("Maxime Bernier");
        candidate6.setTextFill(Color.NAVY);
        candidate6.setFont(Font.font(20));
        candidate6.setUserData("Maxime Bernier");

        candidate6.setToggleGroup(group);
        hbox7.getChildren().addAll(candidate6Label, candidate6);
        gridPane.add(hbox7, 0, 9);
        
        //Creates a button that the voter clicks on to sumbit their 
        HBox buttonHBox = new HBox();
        Button castVote = new Button("Cast Vote");        

        //The following few lines of code adds the button to the HBox above, and adds of design to the button
        buttonHBox.getChildren().add(castVote);
        buttonHBox.setPadding(new Insets(0, 0, 0, 700));
        castVote.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
        castVote.setPrefSize(120, 20);
        castVote.setFont(Font.font(18));
        gridPane.add(buttonHBox, 0, 10);
        
        ButtonType confirmVoteYes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType confirmVoteNo = new ButtonType("No", ButtonBar.ButtonData.OK_DONE);
        
        //This EventHandler below asks the user to confirm their vote, and then takes them to PostVotingPage.java.
        castVote.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println(group.getSelectedToggle().getUserData().toString());
                if (group.getSelectedToggle() != null) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Vote");
                    alert.setHeaderText(null);
                    alert.setContentText("Is this your final choice?");
                    Optional <ButtonType> action = alert.showAndWait();
                 
                    if(action.get() == ButtonType.OK){
                        String message = hash(group.getSelectedToggle().getUserData().toString());
                        try (Socket socket = connectToServer(port, host)) {
                            dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF(message);
                            dos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage postVotingStage = new Stage();
                        PostVotingPage postVoting = new PostVotingPage();
                        postVoting.start(postVotingStage);
                        postVotingStage.show();
                        votingStage.close();
                    }
                }
            }
        });
        
        //The following lines of code creates a new Scene, sets the title of the scene, and displays it when this program is run.
        Scene scene = new Scene(gridPane, 1500, 1000);
        votingStage.setTitle("Maple Leaf Voting System");
        votingStage.setScene(scene);
        votingStage.show();
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

    public static void main(String[] args) {
        launch(args);
    }

    //This method outputs an alert when the voter sumbits their vote.
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

