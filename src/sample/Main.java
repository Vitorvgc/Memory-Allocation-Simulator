package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import Controllers.ConfigurationController;
import javafx.scene.Scene;

public class Main extends Application {

    public static String screen1Id = "Main";
    public static String screen1File = "sample.fxml";
    public static String screen2Id = "screen2";
    public static String screen2File = "ConfigurationScreen.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/ConfigurationScreen.fxml"));

        Parent root = loader.load();

        ConfigurationController controller = loader.getController();
        controller.setPreviousStage(primaryStage);

        primaryStage.setTitle("Configuration");
        primaryStage.setScene(new Scene(root, 770, 680));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
