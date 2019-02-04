package voting;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

/**
 * @author Vedant Shah & Khatchig Anteblian
 */
public class SignUp extends Application {
    //Creates ten TextFields where the voter will enter their personal information so they can register to vote.
    TextField phoneField, addressField, password, confirmPassword, emailField, cityField, nameField1, nameField3, postalcodeField, birthdateField;
    //Creates two new ChoiceBoxes, which are both of type String.
    ChoiceBox<String> gender, provinceTerritory;
    //Creates a new GridPane.
    GridPane gridPane = new GridPane();
    //Creates a static 1-D ArrayList of type String.
    static ArrayList<String[]> usernamePassword;
    @Override
    public void start(Stage signUpStage) {
        //The following lines of code sets the background of the GridPane and the vertical gap between two labels.
        gridPane.setStyle("-fx-background-color: #C0C0C0");
        gridPane.setVgap(15);

        //Creates a label for the title/header
        Label headerLabel = new Label("New Voter? Sign Up Here!");
        headerLabel.setFont(Font.font(30));
        headerLabel.setTextFill(Color.NAVY);
        headerLabel.setPadding(new Insets(0, 0, 0, 600));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        
        //Creates a label for the subtitle
        Label subTitle = new Label("Are you a new voter? Do you wish to vote in the 2019 Canadian Federal Election? Then you are at the right place! Maple Leaf Online Voting System is an initiative created the Canadian Federal Government that allows individuals such as you to vote online instead of "
                + "having to go to election centres. Please enter the required information below as an e-mail will be sent to you with the security code you must enter to login and vote! Good luck!");
        subTitle.setFont(Font.font(20));
        subTitle.setTextFill(Color.NAVY);
        subTitle.setWrapText(true);
        subTitle.setPadding(new Insets(0, 100, 0, 100));
        subTitle.setTextAlignment(TextAlignment.JUSTIFY);
        gridPane.add(subTitle, 0, 1);
        
        //Creates a new HBox.
        HBox hbox1 = new HBox();
        hbox1.setSpacing(10);

        //Label for the voter's fullname
        Label nameLabel1 = new Label("Full Name:");
        nameLabel1.setTextFill(Color.NAVY);
        nameLabel1.setFont(Font.font(20));
        nameLabel1.setPadding(new Insets(0, 0, 0, 350));
        hbox1.getChildren().add(nameLabel1);
        gridPane.add(hbox1, 0, 2);

        HBox nameHbox = new HBox();
        nameHbox.setSpacing(10);
        nameField1 = new TextField();
        nameField1.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        nameHbox.getChildren().add(nameField1);
        nameHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(nameHbox, 0, 2);

        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);

        Label nameLabel3 = new Label("Username:");
        nameLabel3.setTextFill(Color.NAVY);
        nameLabel3.setFont(Font.font(20));
        nameLabel3.setPadding(new Insets(0, 0, 0, 350));
        hbox2.getChildren().add(nameLabel3);
        gridPane.add(hbox2, 0, 3);

        HBox uNameHbox = new HBox();
        uNameHbox.setSpacing(10);
        nameField3 = new TextField();
        nameField3.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        uNameHbox.getChildren().add(nameField3);
        uNameHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(uNameHbox, 0, 3);
        

        HBox hbox3 = new HBox();
        hbox3.setSpacing(10);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.NAVY);
        passwordLabel.setFont(Font.font(20));
        passwordLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox3.getChildren().add(passwordLabel);
        gridPane.add(hbox3, 0, 4);

        HBox passHbox = new HBox();
        passHbox.setSpacing(10);
        password =  new PasswordField();
        password.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        passHbox.getChildren().add(password);
        passHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(passHbox, 0, 4);

        HBox hbox4 = new HBox();
        hbox4.setSpacing(10);
 
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setTextFill(Color.NAVY);
        confirmPasswordLabel.setFont(Font.font(20));
        confirmPasswordLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox4.getChildren().add(confirmPasswordLabel);
        gridPane.add(hbox4, 0, 5);

        HBox confHbox = new HBox();
        confHbox.setSpacing(10);
        confirmPassword =  new PasswordField();
        confirmPassword.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        confHbox.getChildren().add(confirmPassword);
        confHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(confHbox, 0, 5);
 
        HBox hbox5 = new HBox();
        hbox5.setSpacing(10);

        Label emailLabel = new Label("Email ID:");
        emailLabel.setTextFill(Color.NAVY);
        emailLabel.setFont(Font.font(20));
        emailLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox5.getChildren().add(emailLabel);
        gridPane.add(hbox5, 0, 6);

        HBox mailHbox = new HBox();
        mailHbox.setSpacing(10);
        emailField = new TextField();
        emailField.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        mailHbox.getChildren().add(emailField);
        mailHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(mailHbox, 0, 6);
 
        HBox hbox6 = new HBox();
        hbox6.setSpacing(10);

        Label genderLabel = new Label("Gender:");
        genderLabel.setTextFill(Color.NAVY);
        genderLabel.setFont(Font.font(20));
        genderLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox6.getChildren().add(genderLabel);
        gridPane.add(hbox6, 0, 7);

        HBox genderHbox = new HBox();
        genderHbox.setSpacing(10);
        gender = new ChoiceBox<>();
        gender.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        gender.setValue("Select One");
        gender.getItems().addAll("Male", "Female", "Other");
        genderHbox.getChildren().add(gender);
        genderHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(genderHbox, 0, 7);
        
        HBox hbox7 = new HBox();
        hbox7.setSpacing(10);

        Label addressLabel = new Label("Home Address:");
        addressLabel.setTextFill(Color.NAVY);
        addressLabel.setFont(Font.font(20));
        addressLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox7.getChildren().add(addressLabel);
        gridPane.add(hbox7, 0, 8);

        HBox addrHbox = new HBox();
        addrHbox.setSpacing(10);
        addressField = new TextField();
        addressField.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        addrHbox.getChildren().add(addressField);
        addrHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(addrHbox, 0, 8);
        
        HBox hbox8 = new HBox();
        hbox8.setSpacing(10);

        Label postalcodeLabel = new Label("Postal Code:");
        postalcodeLabel.setTextFill(Color.NAVY);
        postalcodeLabel.setFont(Font.font(20));
        postalcodeLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox8.getChildren().add(postalcodeLabel);
        gridPane.add(hbox8, 0, 9);

        HBox pCodeHbox = new HBox();
        pCodeHbox.setSpacing(10);
        postalcodeField = new TextField();
        postalcodeField.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        pCodeHbox.getChildren().add(postalcodeField);
        pCodeHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(pCodeHbox, 0, 9);
        
        HBox hbox9 = new HBox();
        hbox9.setSpacing(10);

        Label cityLabel = new Label("City:");
        cityLabel.setTextFill(Color.NAVY);
        cityLabel.setFont(Font.font(20));
        cityLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox9.getChildren().add(cityLabel);
        gridPane.add(hbox9, 0, 10);

        HBox cityHbox = new HBox();
        cityHbox.setSpacing(10);
        cityField = new TextField();
        cityField.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        cityHbox.getChildren().add(cityField);
        cityHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(cityHbox, 0, 10);
        
        HBox hbox10 = new HBox();
        hbox10.setSpacing(10);

        Label provinceTerritoryLabel = new Label("Province/Territory:");
        provinceTerritoryLabel.setTextFill(Color.NAVY);
        provinceTerritoryLabel.setFont(Font.font(20));
        provinceTerritoryLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox10.getChildren().add(provinceTerritoryLabel);
        gridPane.add(hbox10, 0, 11);

        HBox provHbox = new HBox();
        provHbox.setSpacing(10);
        provinceTerritory = new ChoiceBox<>();
        provinceTerritory.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        provinceTerritory.setValue("Select One");
        provinceTerritory.getItems().addAll("Ontario", "Quebec", "British Columbia", "Nunavut", "Prince Eduard Island", "Newfoundland and Labrador", "Saskatchewan", "Manitoba", "Alberta", "Northwest Territories", "New Brunswick", "Nova Scota", "Yukon");
        provHbox.getChildren().add(provinceTerritory);
        provHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(provHbox, 0, 11);
         
        HBox hbox11 = new HBox();
        hbox11.setSpacing(10);

        Label phoneLabel = new Label("Primary Phone Number:");
        phoneLabel.setTextFill(Color.NAVY);
        phoneLabel.setFont(Font.font(20));
        phoneLabel.setPadding(new Insets(0, 0, 0, 350));
        hbox11.getChildren().add(phoneLabel);
        gridPane.add(hbox11, 0, 12);

        HBox phoneHbox = new HBox();
        phoneHbox.setSpacing(10);
        phoneField = new TextField();
        phoneField.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        phoneHbox.getChildren().add(phoneField);
        phoneHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(phoneHbox, 0, 12);

        HBox hbox12 = new HBox();
        hbox12.setSpacing(10);

        Label birthdate = new Label("Birthdate (dd/mm/yyyy):");
        birthdate.setTextFill(Color.NAVY);
        birthdate.setFont(Font.font(20));
        birthdate.setPadding(new Insets(0, 0, 0, 350));
        hbox12.getChildren().addAll(birthdate);
        gridPane.add(hbox12, 0, 13);

        HBox bDayHbox = new HBox();
        bDayHbox.setSpacing(10);
        birthdateField = new TextField();
        birthdateField.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;");
        bDayHbox.getChildren().add(birthdateField);
        bDayHbox.setPadding(new Insets(0, 0, 0, 700));
        gridPane.add(bDayHbox, 0, 13);

        HBox buttonHBox = new HBox();
        Button signUp = new Button();
        signUp.setText("Sign Up");

        buttonHBox.getChildren().add(signUp);
        buttonHBox.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(buttonHBox, 0.0);
        AnchorPane.setRightAnchor(buttonHBox, 0.0);
        buttonHBox.setAlignment(Pos.CENTER);
        signUp.setStyle("-fx-background-color: cornsilk; -fx-text-fill: black;"); 
        signUp.setPrefSize(100, 20);
        signUp.setFont(Font.font(15));

        signUp.setText("Sign Up");
        signUp.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Once button is pressed, validate all inputs and put them in
                // an array to check for empty input
                boolean emptyInput = false;
                String[] userInput = new String[12];
                userInput[1] = validateName1Input();
                userInput[0] = validateName3Input();
                userInput[2] = validatePasswordInput();
                userInput[11] = validateConfirmPasswordInput();
                userInput[9] = validatePhoneNumberInput();
                userInput[3] = validateEmailAddressInput();
                userInput[4] = validateGenderInput();
                userInput[5] = validateAddressInput();
                userInput[6] = validatePostalCodeInput();
                userInput[7] = validateCityInput();
                userInput[8] = validateProvinceTerritoryInput();
                userInput[10] = validateBirthDate();

                for (String i : userInput) {
                    if (i.isEmpty()) {
                        emptyInput = true;
                    }
                }

                if (!emptyInput) {
                    // Create a new voterClass object with the user input
                    UserClass voter = new VoterClass(
                                          userInput[0],
                                          userInput[1],
                                          userInput[2],
                                          userInput[3],
                                          userInput[4],
                                          userInput[5],
                                          userInput[6],
                                          userInput[7],
                                          userInput[8],
                                          userInput[9],
                                          userInput[10]
                                          );
                    // Send user info to server through voterClass
                    String serverFeedback = voter.sendUserInfo();
                    // If everything is ok, go to login page
                    if (serverFeedback.equals("0")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thank You!");
                        alert.setHeaderText(null);
                        alert.setContentText("Thank you for signing up. You may now login.");
                        Optional <ButtonType> action = alert.showAndWait();
                        Stage loginStage = new Stage();
                        Login loginPage = new Login();
                        loginPage.start(loginStage);
                        loginStage.show();
                        signUpStage.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        // if account already exists, go to login without
                        // creating new account
                        if (serverFeedback.equals("-1")) {
                            alert.setTitle("Account Already Exists!");
                            alert.setContentText("This account has already been created. Click ok to log in now.");
                            Optional <ButtonType> action = alert.showAndWait();
                            Stage loginStage = new Stage();
                            Login loginPage = new Login();
                            loginPage.start(loginStage);
                            loginStage.show();
                            signUpStage.close();
                        // If username is already taken, ask user for a new one.
                        } else if (serverFeedback.equals("1")) {
                            alert.setTitle("Username Already Taken!");
                            alert.setContentText("The given username is already taken. Please choose a new one.");
                            Optional <ButtonType> action = alert.showAndWait();
                        // If account information already exists, pervent
                        // a second signup
                        } else if (serverFeedback.equals("2")) {
                            alert.setTitle("Account Exists Under A Different Username!");
                            alert.setContentText("This account information already exists under a different username. A person Can only sign up to vote once.");
                            Optional <ButtonType> action = alert.showAndWait();
                            Stage loginStage = new Stage();
                            Login loginPage = new Login();
                            loginPage.start(loginStage);
                            loginStage.show();
                            signUpStage.close();
                        // In the case of an empty serverFeedback, report
                        // server error
                        } else if (serverFeedback.isEmpty()) {
                            alert.setTitle("Server Error!");
                            alert.setContentText("There was an error with the server. Please try again. If the error persists, log out and try again later.");
                            Optional <ButtonType> action = alert.showAndWait();
                        }
                    }
                }
            }
        });
        //The following line of code adds the HBox above with the button to the GridPane.
        gridPane.add(buttonHBox, 0, 14);

        //The following lines of code creates a new Scene, sets the title of the scene, and displays it when this program is run.
        Scene scene = new Scene(gridPane, 1500, 1000);
        signUpStage.setTitle("Maple Leaf Voting System");
        signUpStage.setScene(scene);
        signUpStage.show();
    }
    
    //This method outputs an alert when the voter signs up successfully. It also outputs an alert when the voter does not sign up sucessfully and informs the voter that they have not entered a field correctly.
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    //This method validates the voter's phone number to ensure the format they have entered is correct. Depending on if it is correct or not, it returns a message and displays it on the screen.
    private String validatePhoneNumberInput(){
        String message = "";
        Pattern p = Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
        Matcher m = p.matcher(phoneField.getText());
        if(m.find() && m.group().equals(phoneField.getText())){
            message += phoneField.getText();
            return message;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid phone number.");
            return message;
        }
    }
    
    //This method validates the voter's email address to ensure the format they have entered is correct. Depending on if it is correct or not, it returns a message and displays it on the screen.
    private String validateEmailAddressInput(){
        String message = "";
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9. ]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); 
        Matcher m = p.matcher(emailField.getText());
        if(m.find() && m.group().equals(emailField.getText())){
            message += emailField.getText();
            return message;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid email address.");
            return message;
        }
    }
    
    //This method validates the voter's password to ensure the format they have entered is correct. Depending on if it is correct or not, it returns a message and displays it on the screen.
    private String validatePasswordInput(){
        String message = "";
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})"); 
        Matcher m = p.matcher(password.getText());
        if(m.find() && m.group().equals(password.getText())){
            message += password.getText();
            return message;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid password.");
            return message;
        }
    }
    
    //This method validates the voter's password to confirm that the password they entered the first time matches the password they entered the second time. Depending on if it is correct or not, it returns a message and displays it on the screen.
    private String validateConfirmPasswordInput(){
        String message = "";
        if(confirmPassword.getText().equals(password.getText())){
            message += confirmPassword.getText();
            return message;
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter the password you entered above.");
            return message;
        }
    }
    
    //This method validates that the voter has created a new username. Depending on if they have created a new username or not, it returns a message and displays it on the screen.
    private String validateName1Input(){
        String message = "";
        if(nameField1.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your first name.");
            return message;
        }
        else
            message += nameField1.getText();
            return message;
    }
       
    //This method validates that the voter has entered their fullname. Depending on if they have entered their fullname or not, it returns a message and displays it on the screen.
    private String validateName3Input(){
        String message = "";
        if(nameField3.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your surname.");
            return message;
        }
        else
            message += nameField3.getText();
            return message;
    }
    
    //This method validates that the voter has entered their gender. Depending on if they have entered their gender or not, it returns a message and displays it on the screen.
    private String validateGenderInput(){
        String message = "";
        if(gender.getValue().equals("Select One")) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your gender.");
            return message;
        }
        else
            message += gender.getValue();
            return message;
    }
    
    //This method validates that the voter has entered their home address. Depending on if they have entered their home address or not, it returns a message and displays it on the screen.
    private String validateAddressInput(){
        String message = "";
        if(addressField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your address.");
            return message;
        }
        else
            message += addressField.getText();
            return message;
    }
    
    //This method validates that the voter has entered their home address's postal code. Depending on if they have entered their home address's postal code or not, it returns a message and displays it on the screen.
    private String validatePostalCodeInput(){
        String message = "";
        if(postalcodeField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your postal code.");
            return message;
        }
        else
            message += postalcodeField.getText();
            return message;
    }
    
    //This method validates that the voter has entered the city they live in. Depending on if they have entered the city they live in or not, it returns a message and displays it on the screen.
    private String validateCityInput(){
        String message = "";
        if(cityField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your city.");
            return message;
        }
        else
            message += cityField.getText();
            return message;
    }
    
    //This method validates that the voter has entered the province/territory they live in. Depending on if they have entered the province/territory they live in or not, it returns a message and displays it on the screen.
    private String validateProvinceTerritoryInput(){
        String message = "";
        if(provinceTerritory.getValue().equals("Select One")) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter your province/territory.");
            return message;
        }
        else
            message += provinceTerritory.getValue();
            return message;
    }
    
    /** This method validates the voter's birthdate to ensure the format they have entered is correct. Depending on if it is correct or not, it returns a message and displays it on the screen.
    *   It also checks if the numbers the user has entered are valid or not. It also checks if a year is a leap year or not (ex. "29/02/2007" – 2007 is not leap year, only has 28 days).
    */
    private String validateBirthDate(){
        String message = "";
        Pattern p = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
        Matcher m = p.matcher(birthdateField.getText());
        if(m.matches()){
            m.reset();
            if(m.find()){
                String day = m.group(1);
                String month = m.group(2);
                int year = Integer.parseInt(m.group(3));

                if (day.equals("31") && (month.equals("4") || month .equals("6") || month.equals("9") ||
                    month.equals("11") || month.equals("04") || month .equals("06") ||
                    month.equals("09"))){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                    "Form Error!", "Please enter a valid birthdate in the required form.");
                    return message;

                } 
                else if (month.equals("2") || month.equals("02")){
                    if(year % 4 == 0){
                        if(day.equals("30") || day.equals("31")){
                            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                            "Form Error!", "Please enter a valid birthdate in the required form.");
                            return message;
                        }
                        else{
                            message += day + "/" + month + "/" + m.group(3);
                            return message;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                            "Form Error!", "Please enter a valid birthdate in the required form.");
                            return message;
                        }
                        else{
                            message += day + "/" + month + "/" + m.group(3);
                            return message;
                        }
                    }
                }
                else{                
                    message += day + "/" + month + "/" + m.group(3);
                    return message;
                }
            }
            else{
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
                "Form Error!", "Please enter a valid birthdate in the required form.");
                return message;
            }         
        }
        else{
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), 
            "Form Error!", "Please enter a valid birthdate in the required form.");
            return message;
        }  
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
