package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import Controllers.ConfigurationController;
import javafx.scene.Scene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/ConfigurationScreen.fxml"));

        Parent root = loader.load();

        ConfigurationController controller = loader.getController();
        controller.setPreviousStage(primaryStage);

        primaryStage.setTitle("Configuração");
        primaryStage.setScene(new Scene(root, 530, 600));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
