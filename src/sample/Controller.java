package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    View view = new View();
    Model model = new Model();
    Connection connection = model.createConnection();

    public Controller(){
        //updateViewFromModel();
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //View Employees button
        System.out.println("init");
        view.viewEmployeesButton.setOnAction(e -> {
            try {
                System.out.println("test1");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Employee");
                System.out.println("test2");
                try {
                    while(resultSet.next())
                        System.out.println(resultSet.getString(2));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }catch (Exception e2) {
                e2.printStackTrace();
            }
            System.out.println("test3");
        });

    }
}
