package sample;

import Controllers.ConfigurationController;
import Controllers.Controller;
import Enums.AllocationType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/Screens/ConfigurationScreen.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/ConfigurationScreen.fxml"));

        Parent root = loader.load();

        ConfigurationController controller = loader.getController();
        controller.setPreviousStage(primaryStage);

        primaryStage.setTitle("Configuration");
        primaryStage.setScene(new Scene(root, 770, 680));
        primaryStage.show();

        // SystemManager test

        Process a = new Process(5,5,5,5,10,10);
        Process b = new Process(10,10,5,5,15,15);
        Process c = new Process(2,2,5,5,20,20);

        ArrayList<Process> processes = new ArrayList<>();
        processes.add(a);
        processes.add(b);
        processes.add(c);

        SystemManager sys = new SystemManager(processes, AllocationType.FIRST_FIT, 100, new Process(20));

        sys.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
