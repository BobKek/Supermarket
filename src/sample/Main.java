package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.CustomerController;

public class Main extends Application {

    public static Stage mainStage;
    public static Parent loginRoot;
    public static Parent managerRoot;
    public static Parent customerRoot;
    public static CustomerController customerController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        loginRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
        managerRoot = FXMLLoader.load(getClass().getResource("manager.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer.fxml"));
        customerRoot = loader.load();
        customerController = loader.getController();

        mainStage.setResizable(false);
        Scene scene = new Scene(loginRoot, 1200, 800);
        mainStage.centerOnScreen();

        mainStage.setTitle("Login");
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.getStylesheets().add("sample/style.css");
        mainStage.setScene(scene);

        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
