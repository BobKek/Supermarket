package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    @FXML
    public ImageView logoutButton;
    @FXML
    public void logoutHandler(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK){
            return;
        }
        Main.mainStage.getScene().setRoot(Main.loginRoot);
        Main.mainStage.setTitle("Login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
