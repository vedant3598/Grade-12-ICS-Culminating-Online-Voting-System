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
import java.io.*;
import java.net.Socket;
import java.util.Optional;

/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
public class EnterCodePage {
        //The following four variables are required to connect to the server.
        static final int port = 8888;
        static final String host = "172.20.10.3";
        static DataInputStream dis;
        static DataOutputStream dos;
        
        public void start(Stage enterCodeStage) {

        //Creates a new GridPane.
        GridPane gridPane = new GridPane();
        gridPane.setVgap(15);
        
        //Creates a label for the title.
        Label title = new Label("Sumbit Code");
        title.setFont(new Font(25));
        title.setTextFill(Color.NAVY);
        title.setContentDisplay(ContentDisplay.TOP);
        title.setTextAlignment(TextAlignment.JUSTIFY);
        title.setPadding(new Insets(10, 0, 0, 150));
        gridPane.add(title, 0, 0);
        
        //Creates a new HBox.
        HBox hbox1 = new HBox();
        hbox1.setSpacing(10);
        
        //Creates a label for the subtitle
        Label subTitle = new Label("Please enter the code that was sent to your email address.");
        subTitle.setPadding(new Insets(0, 0, 0, 25));
        subTitle.setWrapText(true);
        hbox1.getChildren().add(subTitle);
        gridPane.add(hbox1, 0, 1);
        
        //Creates a new HBox.
        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);
        
        //Creates a label for the code of the voter.
        Label code = new Label("Code: ");
        code.setPadding(new Insets(0, 0, 0, 75));
        
        //This creates a new TextField that takes the security code from the user.
        TextField codeField = new TextField();
        hbox2.getChildren().addAll(code, codeField);
        gridPane.add(hbox2, 0, 2);

        //Creates a button that enables the user to sumbit their code and vote. The voter will only be able to vote once their code entered is verified.
        Button sumbitCode = new Button();
        sumbitCode.setText("Submit Code");
        sumbitCode.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
        sumbitCode.setPrefSize(120, 20);
        sumbitCode.setFont(Font.font(15));
        
        //The EventHandler below will take the voter to the voting page only if the voter's username and password match the username and password they created when they signed up. 
        sumbitCode.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String message = "code " + codeField.getText();
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
                    alert.setTitle("Authentication Succeeded!");
                    alert.setContentText("Thank You! You will now be directed to the voting page.");
                    Optional <ButtonType> button = alert.showAndWait();
                    Stage votingStage = new Stage();
                    VotingPage voting = new VotingPage();
                    voting.start(votingStage);
                    votingStage.show();
                    enterCodeStage.close();
                } else if (serverFeedback.equals("1")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Authentication Failed!");
                    alert.setContentText("You entered the wrong code! Please try again.");
                    Optional <ButtonType> button = alert.showAndWait();
                } else {
                    String[] rawData = serverFeedback.split(" ");
                    int[] data = new int[9];
                    for (int i=0; i<rawData.length; i++) {
                        data[i] = Integer.parseInt(rawData[i]);
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Authentication Succeeded!");
                    alert.setContentText("Welcome Back! You will now be directed to the statistics page.");
                    Optional <ButtonType> button = alert.showAndWait();
                    Stage graphStage = new Stage();
                    GraphsPage graph = new GraphsPage(data);
                    graph.start(graphStage);
                    graphStage.show();
                    enterCodeStage.close();
                }
            }
        });
        
        //Creates a new HBox.
        HBox hbox3 = new HBox();
        hbox3.setSpacing(10);
        
        //The following lines of code adds the sumbitCode button to the HBox, which then gets added to the GridPane.
        hbox3.getChildren().add(sumbitCode);
        hbox3.setPadding(new Insets(75, 75, 0, 150));
        gridPane.add(hbox3, 0, 2);
        
        //The following line of code sets the background of the GridPane.
        gridPane.setStyle("-fx-background-color: #C0C0C0");

        //The following lines of code creates a new Scene, sets the title of the scene, and displays it when this program is run.
        Scene scene = new Scene(gridPane, 420, 300);
        enterCodeStage.setTitle("Maple Leaf Online Voting System");
        enterCodeStage.setScene(scene);
        enterCodeStage.show();
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
        
    public static void main(String[] args) {
        launch(args);
    }    
}
