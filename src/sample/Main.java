package sample;

import Enums.AllocationType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Memory memory = new Memory(100, new Process(20));
        memory.printMemory();

        memory.allocateProcess(new Process(30), AllocationType.FIRST_FIT);
        memory.printMemory();

        memory.allocateProcess(new Process(20), AllocationType.FIRST_FIT);
        memory.printMemory();

        memory.allocateProcess(new Process(40), AllocationType.FIRST_FIT);
        memory.printMemory();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
