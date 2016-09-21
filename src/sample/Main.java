package sample;

<<<<<<< HEAD
import Controllers.ScreensController;
=======
import Controllers.ConfigurationController;
import Controllers.Controller;
import Enums.AllocationType;
>>>>>>> d31cdb9
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

//        Parent root = FXMLLoader.load(getClass().getResource("/Screens/ConfigurationScreen.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/ConfigurationScreen.fxml"));

        Parent root = loader.load();

        ConfigurationController controller = loader.getController();
        controller.setPreviousStage(primaryStage);

        primaryStage.setTitle("Configuration");
        primaryStage.setScene(new Scene(root, 770, 680));
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
