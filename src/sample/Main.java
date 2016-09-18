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

        // TEST

        Process so = new Process(20);
        Process a = new Process(30);
        Process b = new Process(20);
        Process c = new Process(40);

        Memory memory = new Memory(100, so);

        memory.allocateProcess(a, AllocationType.WORST_FIT);
        memory.printMemory();

        memory.desallocateProcess(a);

        memory.allocateProcess(b, AllocationType.WORST_FIT);
        memory.printMemory();

        memory.allocateProcess(c, AllocationType.WORST_FIT);
        memory.printMemory();

        memory.desallocateProcess(b);

        memory.desallocateProcess(c);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
