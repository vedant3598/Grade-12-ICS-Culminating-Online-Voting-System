package voting;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Vedant Shah & Khatchig Anteblian
 */

public class GraphsPage extends Application {
    //Variables for the gender tyoes.
    final static String male = "Male";
    final static String female = "Female";
    final static String other = "Other";

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final StackedBarChart<String, Number> sbc =
            new StackedBarChart<>(xAxis, yAxis);
    final XYChart.Series<String, Number> series1 =
            new XYChart.Series<>();
    final XYChart.Series<String, Number> series2 =
            new XYChart.Series<>();
    final XYChart.Series<String, Number> series3 =
            new XYChart.Series<>();
    
    // Array holds the data that will go into the graphs
    int[] data;

    //This method gets data in the form on integers and puts it into the variable "data".
    public GraphsPage(int[] data) {
        this.data = data;
    }
    
    public void start(Stage graphStage) {
        //Creates a new GridPane
        GridPane gridPane = new GridPane();
        
        //Creates a label for the title/header
        Label title = new Label("2019 Canadian Federal Election Statistics");
        title.setFont(Font.font(30));
        title.setTextFill(Color.NAVY);
        title.setPadding(new Insets(0, 0, 0, 300));
        gridPane.add(title, 0, 0);
            
        //Creates an empty label
        Label emptyLabel1 = new Label("");
        gridPane.add(emptyLabel1, 0, 1);
        
        //The following lines of code sets the graph, and the labels that are found in a bar graph
        sbc.setTitle("Voter Turnout");
        xAxis.setLabel("Genders");
        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList(male, female, other)));
        yAxis.setLabel("Number of Voters");
        series1.setName("Male");
        series1.getData().add(new XYChart.Data<>(male, this.data[6]));
        series2.setName("Female");
        series2.getData().add(new XYChart.Data<>(female, this.data[7]));
        series3.setName("Other");
        series3.getData().add(new XYChart.Data<>(other, this.data[8]));
        sbc.getData().addAll(series1, series2, series3);
        sbc.setPrefSize(700, 500);
        sbc.setPadding(new Insets(0, 0, 0, 250));
        gridPane.add(sbc, 0, 2);
        
        //Creates an empty label
        Label emptyLabel2 = new Label("");
        gridPane.add(emptyLabel2, 0, 3);
        
        //Creates an empty label
        Label emptyLabel3 = new Label("");
        gridPane.add(emptyLabel3, 0, 4);
        
        //The following lines of code sets the graph, and the labels that are found in a pie chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Justin Trudeau", this.data[0]),
                new PieChart.Data("Andrew Scheer", this.data[1]),
                new PieChart.Data("Jagmeet Singh", this.data[2]),
                new PieChart.Data("Mario Beaulieu", this.data[3]),
                new PieChart.Data("Elizabeth May", this.data[4]),
                new PieChart.Data("Maxime Bernier", this.data[5]));
        final PieChart chart = new PieChart(pieChartData);
        chart.setPadding(new Insets(0, 0, 0, 250));
        chart.setTitle("Candidates");
        
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        //This finds the percentage of how many votes a candidate obtained
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                     }
                });
        }
        
        gridPane.add(chart, 0, 5);
        
        //Creates a new HBox
        HBox hbox6 = new HBox();
        //Creates a button for the voter to go to the home page
        Button homePage = new Button("Home");

        //The following lines of code adds styles to the button
        hbox6.getChildren().add(homePage);
        hbox6.setPadding(new Insets(0, 0, 0, 25));
        homePage.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
        homePage.setPrefSize(100, 20);
        homePage.setFont(Font.font(18));
        gridPane.add(hbox6, 0, 0);            

        //The EventHandler below will take the voter to WelcomePage.java only.
        homePage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    Stage primaryStage = new Stage();
                    WelcomePage welPage = new WelcomePage();
                    welPage.start(primaryStage);
                    primaryStage.show();
                    graphStage.close();
            }
        });        
        
        gridPane.setStyle("-fx-background-color: #C0C0C0");
        
        //Creates a new Scene
        Scene scene = new Scene(gridPane, 1200, 1000);
        graphStage.setTitle("Maple Leaf Voting System");
        graphStage.setScene(scene);
        graphStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
