package sample;

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
//        Parent root = FXMLLoader.load(getClass().getResource("ConfigurationScreen.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../Screens/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 726, 643));
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
