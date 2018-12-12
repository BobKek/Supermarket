package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;

public class Model {
    ObservableList<String> names = FXCollections.observableArrayList("Bob", "Dana", "Ali", "kk", "dsoifjewifjewiofijowejiofewjiofjiowefjiowejiof", "123", "121321312");

    public static Connection createConnection(){
        Connection connection = null;

        try{
            try {
                Class.forName("com.mysql.jdbc.Driver");
            }catch(ClassNotFoundException e2){
                e2.printStackTrace();
            }

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SuperMarket", "bob", "123");

        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
