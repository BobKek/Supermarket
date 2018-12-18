package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //View view = new View();
    Model model = new Model();
    Connection connection = model.createConnection();

    @FXML
    public ListView<String> listView;

    @FXML
    public Button viewEmployeesButton;

    //Add employee variables
    @FXML
    public GridPane addEmployeeGridPane;
    @FXML
    public Button addEmployeeButton;
    @FXML
    public TextField empFirstnameTextField;
    @FXML
    public TextField empLastnameTextField;
    @FXML
    public DatePicker empBirthdatePicker;
    @FXML
    public TextField empPhoneTextField;
    @FXML
    public TextField empSalaryTextField;
    @FXML
    public ComboBox roleEmpComboBox;
    @FXML
    public ComboBox branchEmpComboBox;
    @FXML
    public Button submitAddButton;
    @FXML
    public Button clearEmpData;



    public String currentlyViewing = "";
    public Controller(){
        //updateViewFromModel();
    }
    /*
    public void updateViewFromModel(){
        for(int i = 0; i < model.names.size(); i++) {
            view.lv.getItems().add(model.names.get(i));
        }
        view.lv.setMaxSize(100,130);
        GridPane.setConstraints(view.sort, 0,0);
        view.sort.setValue("ASC");

        GridPane.setConstraints(view.lv, 0,1);
        view.grid.getChildren().addAll(view.lv, view.sort);
        view.sort.setOnAction(e -> {
            Comparator<String> comparator = Comparator.comparing(String::toLowerCase);

            if(view.sort.getSelectionModel().selectedItemProperty().getValue() == "ASC"){

                model.names.sort(comparator);
                view.lv.setItems(model.names);
                System.out.println("if statement");

            }
            else{
                comparator = comparator.reversed();
                model.names.sort(comparator);
                view.lv.setItems(model.names);
            }
        });

    }
    */

    @FXML
    public void viewEmployees() {
        addEmployeeGridPane.setVisible(false);
        listView.setVisible(true);
        submitAddButton.setVisible(false);
        clearEmpData.setVisible(false);
        if(currentlyViewing.equals(viewEmployeesButton.getText()))
            return;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Employee");

            try {
                while (resultSet.next()){
                    model.names.add(resultSet.getString(2));
                    currentlyViewing = viewEmployeesButton.getText();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }catch (Exception e2) {
            e2.printStackTrace();
        }
        listView.setItems(model.names);
    }

    @FXML
    public void addEmployeeView(){
        listView.setVisible(false);
        addEmployeeGridPane.setVisible(true);
        submitAddButton.setVisible(true);
        clearEmpData.setVisible(true);
    }

    @FXML
    public void addEmployeeHandler(){
        try {
            String query = "INSERT INTO Employee (first_name, last_name, phone, salary, employment_date, task, branchid) VALUES ( ?, ?, ?, ?, CURDATE(), ?, 10);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, empFirstnameTextField.getText());
            preparedStatement.setString(2, empLastnameTextField.getText());
            preparedStatement.setString(3, empPhoneTextField.getText());
            preparedStatement.setString(4, empSalaryTextField.getText());
            preparedStatement.setString(5, "dev");
            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void clearEmpDataHandler(){
        empFirstnameTextField.setText("");
        empLastnameTextField.setText("");
        //empBirthdatePicker.getEditor().clear();
        empBirthdatePicker.setValue(null);
        empPhoneTextField.setText("");
        empSalaryTextField.setText("");
        roleEmpComboBox.getSelectionModel().clearSelection();
        branchEmpComboBox.getSelectionModel().clearSelection();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewEmployees();
    }
}
