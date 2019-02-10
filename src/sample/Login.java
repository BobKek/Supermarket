package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.Model.Model;
import sample.Model.objects.Customer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public boolean isLoggedIn = false;
    //region Login
    @FXML
    public AnchorPane loginAnchorPane;
    @FXML
    public TextField usernameTextField;
    @FXML
    public TextField passwordTextField;
    @FXML
    public ComboBox<String> loginComboBox;
    @FXML
    public ProgressBar loggingInProgressBar;
    @FXML
    public Button loginButton;

    @FXML
    public void loginHandler(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Alert alert;
        if (loginComboBox.getValue() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please specify your position!");
            alert.showAndWait();
            return;
        }
        String side = loginComboBox.getValue();
        if (Model.getInstanceOfModel().verifyUser(username,password,side)){
            Customer customer = Model.getInstanceOfModel().getCustomer(username);
            loggingInProgressBar.setVisible(true);
            Float[] values = new Float[]{-1.0f, -0.9f, -0.8f, -0.7f, 0.6f, -0.5f, -0.4f, -0.3f, -0.2f, -0.1f, 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};

            for(int i=0; i< values.length; i++)
                loggingInProgressBar.setProgress(values[i]);
            alert = new Alert(Alert.AlertType.INFORMATION); //Alert to inform the user that a login is successful
            alert.setTitle("Success");
            alert.setHeaderText("Login successful!");
            alert.showAndWait();
            Main.mainStage.centerOnScreen();
            Main.mainStage.setWidth(1200);
            Main.mainStage.setHeight(800);
            if(side.contains("Customer")){
                Main.mainStage.getScene().setRoot(Main.customerRoot);
                Main.customerController.setLoggedInCustomer(customer);
                //Main.mainStage.setScene(new Scene(Main.customerRoot, 1200, 800));
                Main.mainStage.setTitle("SuperMarket");
            }
            else {
                Main.mainStage.getScene().setRoot(Main.managerRoot);
                //Main.mainStage.setScene(new Scene(Main.managerRoot, 1200, 800));
                Main.mainStage.setTitle("Manager Side");
            }
            isLoggedIn = true;
            loggingInProgressBar.setVisible(false);
        }
        else{
            alert = new Alert(Alert.AlertType.ERROR); //Error Alert to inform the user of wrong credentials
            alert.setTitle("Login Error");
            alert.setHeaderText("Username and password do not match!");
            alert.setContentText("Try again");
            alert.showAndWait();
        }
        usernameTextField.setText("");
        passwordTextField.setText("");
        loginComboBox.getSelectionModel().clearSelection();
        loginComboBox.setPromptText("-- Select --");
    }
    //endregion

    public void closeProgram(){
        Main.mainStage.setOnCloseRequest(e ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");

            ButtonType cancel = new ButtonType("Cancel");
            ButtonType exit = new ButtonType("Exit");
            alert.getButtonTypes().setAll(cancel, exit);

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == exit){
                Platform.exit();
            }
            e.consume();
        });
    }

    @FXML
    public void exitHandler(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");

        ButtonType cancel = new ButtonType("Cancel");
        ButtonType exit = new ButtonType("Exit");
        alert.getButtonTypes().setAll(cancel, exit);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == exit){
            Platform.exit();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginComboBox.setItems(FXCollections.observableArrayList("Manager" , "Customer"));
        closeProgram();
    }
}
