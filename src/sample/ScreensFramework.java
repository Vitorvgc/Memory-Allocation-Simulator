package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Edy Junior on 21/09/2016.
 */
public class ScreensFramework extends Application {

    public static String screen1Id = "Main";
    public static String screen1File = "sample.fxml";
    public static String screen2Id = "screen2";
    public static String screen2File = "ConfigurationScreen.fxml";

    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreensFramework.screen1Id, ScreensFramework.screen1File);
        mainContainer.loadScreen(ScreensFramework.screen2Id, ScreensFramework.screen2File);

        mainContainer.setScreen(ScreensFramework.screen2Id);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
