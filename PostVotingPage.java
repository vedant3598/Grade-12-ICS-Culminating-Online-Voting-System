package voting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */
    public class PostVotingPage extends Application{

        public void start(Stage postVotingStage) {
            //Creates a new GridPane.
            GridPane gridPane = new GridPane();
            
            //Creates a label for the title/header.
            Label headerLabel = new Label("Thank You for Voting!");
            headerLabel.setMaxWidth(Double.MAX_VALUE);
            AnchorPane.setLeftAnchor(headerLabel, 0.0);
            AnchorPane.setRightAnchor(headerLabel, 0.0);
            headerLabel.setAlignment(Pos.CENTER);
            headerLabel.setFont(Font.font(30));
            headerLabel.setTextFill(Color.NAVY);
            gridPane.add(headerLabel, 0, 1);
            
            //Creates an empty label.
            Label emptyLabel1 = new Label("");
            gridPane.add(emptyLabel1, 0, 2);
            
            //Creates a new HBox.
            HBox hbox1 = new HBox();
            hbox1.setSpacing(10);
            
            //Creates a label for the first subtitle
            Label subTitle1 = new Label("   The \"simple act\" of voting – once a privilege conferred on those affluent enough to own land or pay taxes – has become a right of citizenship "
                    + "enjoyed by all but a very few Canadian adults. Canadians see voting not only as a treasured right but also as a civic obligation – a way of acting "
                    + "on our commitment to democratic principles and protecting our stake in Canada's political life.\n" + "");
            subTitle1.setTextAlignment(TextAlignment.JUSTIFY);
            subTitle1.setTextFill(Color.NAVY);
            subTitle1.setPadding(new Insets(0, 100, 0, 100));
            subTitle1.setWrapText(true);
            subTitle1.setFont(Font.font(20));
            hbox1.getChildren().add(subTitle1);
            gridPane.add(hbox1, 0, 3);
            
            //Creates an empty label.
            Label emptyLabel2 = new Label("");
            gridPane.add(emptyLabel2, 0, 4);
            
            //Creates a new HBox.
            HBox hbox2 = new HBox();
            hbox2.setSpacing(10);

            //Creates a label for the second subtitle
            Label subTitle2 = new Label("   The electorate (the body of people eligible to vote at an election) is defined by the constitution and by law – in "
                    + "the case of federal elections, the Canada Elections Act. The provisions determining eligibility are referred to collectively as the franchise"
                    + " – the conditions governing the right to vote.\n" + "");
            subTitle2.setTextAlignment(TextAlignment.JUSTIFY);
            subTitle2.setTextFill(Color.NAVY);
            subTitle2.setPadding(new Insets(0, 100, 0, 100));
            subTitle2.setWrapText(true);
            subTitle2.setFont(Font.font(20));
            hbox2.getChildren().add(subTitle2);
            gridPane.add(hbox2, 0, 5);
            
            //Creates an empty label.
            Label emptyLabel3 = new Label("");
            gridPane.add(emptyLabel3, 0, 6);
            
            //Creates a new HBox.
            HBox hbox3 = new HBox();
            hbox3.setSpacing(10);

            //Creates a label for the third subtitle
            Label subTitle3 = new Label("   Today, exercising the federal franchise means voting to elect a representative to sit in the House of Commons. Canada's Parliament consists "
                    + "of two chambers: the Senate, whose members are appointed by the Governor General on the advice of the Prime Minister and represent provinces or regions, and the "
                    + "House of Commons, whose members are elected at regular intervals by popular vote. For election purposes, the country is divided into electoral districts – also "
                    + "known as constituencies or ridings – each entitled to one seat in the Commons. The number of constituencies is adjusted every 10 years, following the census, to "
                    + "reflect changes in population numbers and distribution. Since Confederation, the number of constituencies (and seats) has risen from 181 to 308.\n" + "");
            subTitle3.setTextAlignment(TextAlignment.JUSTIFY);
            subTitle3.setTextFill(Color.NAVY);
            subTitle3.setPadding(new Insets(0, 100, 0, 100));
            subTitle3.setWrapText(true);
            subTitle3.setFont(Font.font(20));
            hbox3.getChildren().add(subTitle3);
            gridPane.add(hbox3, 0, 7);
            
            //Creates an empty label.
            Label emptyLabel4 = new Label("");
            gridPane.add(emptyLabel4, 0, 8);
            
            //Creates a new HBox.
            HBox hbox4 = new HBox();
            hbox4.setSpacing(10);
            
            //Creates a label for the fourth subtitle
            Label subTitle4 = new Label("Canada thanks YOU for voting this year!");
            subTitle4.setTextFill(Color.RED);
            subTitle4.setWrapText(true);
            subTitle4.setFont(Font.font(25));
            hbox4.getChildren().add(subTitle4);
            hbox4.setMaxWidth(Double.MAX_VALUE);
            AnchorPane.setLeftAnchor(subTitle4, 0.0);
            AnchorPane.setRightAnchor(subTitle4, 0.0);
            hbox4.setAlignment(Pos.CENTER);
            gridPane.add(hbox4, 0, 9);
            
            //Adds an image of the Canadian flag to the GUI
            Image img1 = new Image("file:CanadaImage.png");
            ImageView imgView1 = new ImageView(img1);
            imgView1.setX(350);
            gridPane.add(imgView1, 0, 10);
            
            //Creates a new HBox.
            HBox hbox6 = new HBox();
            //Creates a button that would take the user to the home page.
            Button homePage = new Button("Home");
            
            //The following lines of code adds the button to the HBox and then the GridPane, and sets the button and font size.
            hbox6.getChildren().add(homePage);
            hbox6.setPadding(new Insets(50, 0, 0, 50));
            homePage.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
            homePage.setPrefSize(100, 20);
            homePage.setFont(Font.font(15));
            gridPane.add(hbox6, 0, 0);            
            
            //The EventHandler below will take the voter to the home page once they click on it. 
            homePage.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        Stage primaryStage = new Stage();
                        WelcomePage welPage = new WelcomePage();
                        welPage.start(primaryStage);
                        primaryStage.show();
                        postVotingStage.close();
                }
            });
            
            //The line of code below sets the background colour of the GridPane.
            gridPane.setStyle("-fx-background-color: #C0C0C0");
            
            //The following lines of code creates a new Scene, sets the title of the scene, and displays it when this program is run.
            Scene scene = new Scene(gridPane, 1500, 1000);
            postVotingStage.setTitle("Maple Leaf Voting System");
            postVotingStage.setScene(scene);
            postVotingStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}