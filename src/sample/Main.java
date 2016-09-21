package sample;

import Controllers.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String screen1Id = "Main";
    public static String screen1File = "sample.fxml";
    public static String screen2Id = "screen2";
    public static String screen2File = "ConfigurationScreen.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.screen1Id, Main.screen1File);
        mainContainer.loadScreen(Main.screen2Id, Main.screen2File);

        mainContainer.setScreen(Main.screen2Id);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("ConfigurationScreen.fxml"));
////        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 726, 643));
//        primaryStage.show();

//        TEST

//        Process so = new Process(20);
//        Process a = new Process(30);
//        Process b = new Process(20);
//        Process c = new Process(40);
//
//        Memory memory = new Memory(100, so);
//
//        memory.allocateProcess(a, AllocationType.WORST_FIT);
//        memory.printMemory();
//
//        memory.desallocateProcess(a);
//
//        memory.allocateProcess(b, AllocationType.WORST_FIT);
//        memory.printMemory();
//
//        memory.allocateProcess(c, AllocationType.WORST_FIT);
//        memory.printMemory();
//
//        memory.desallocateProcess(b);
//
//        memory.desallocateProcess(c);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
