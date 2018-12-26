package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mainStage;
    public static Parent loginRoot;
    public static Parent managerRoot;
    public static Parent customerRoot;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        loginRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
        managerRoot = FXMLLoader.load(getClass().getResource("manager.fxml"));
        customerRoot = FXMLLoader.load(getClass().getResource("customer.fxml"));
        mainStage.setResizable(false);


        Scene scene = new Scene(loginRoot, 800, 600);
        mainStage.setTitle("Login");
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        //scene.getStylesheets().add("sample/style.css");
        mainStage.setScene(scene);

        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
