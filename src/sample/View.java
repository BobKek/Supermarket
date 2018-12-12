package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class View implements Initializable {
    GridPane grid = new GridPane();

    TextField username = new TextField("Enter username...");
    PasswordField password = new PasswordField();
    Button loginButton = new Button("Login");

    ListView<String> lv = new ListView<String>();
    ComboBox<String> sort = new ComboBox<String>(FXCollections.observableArrayList("ASC", "DSC"));

    @FXML
    public Button viewEmployeesButton;
    @FXML
    public Button button1;
    @FXML
    ListView<String> listView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
