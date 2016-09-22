package Controllers;

import Enums.AllocationType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.SystemManager;
import sample.Process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edvaldojunior on 20/09/16.
 */
public class ConfigurationController implements ControlledScreen {

    Stage previousStage;

    @FXML
    private ChoiceBox typeBox;
    @FXML
    private TextField nProcessText;
    @FXML
    private TextField sizeMemoryText;
    @FXML
    private TextField sizeSOText;
    @FXML
    private TextField m1Text;
    @FXML
    private TextField m2Text;
    @FXML
    private TextField tc1Text;
    @FXML
    private TextField tc2Text;
    @FXML
    private TextField td1Text;
    @FXML
    private TextField td2Text;
    @FXML
    private Button startButton;

    private int numberOfElements = 9;

    ScreensController myController;

    @FXML
    private void initialize() {
        typeBox.setItems(FXCollections.observableArrayList(
                "First fit", "Best fit", "Worst fit", "Next-fit")
        );
        typeBox.setValue("First fit");
    }

    @FXML

    private void startSimulation() throws IOException {

        Stage stage = new Stage();
        stage.setTitle("Simulation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/sample.fxml"));
        Parent root = loader.load();

        previousStage.close();

        stage.setScene(new Scene(root, 770, 680));
        stage.show();


        Process a = new Process(5,5,5,5,25,25);
        Process b = new Process(2,2,5,5,15,15);
        Process c = new Process(3,3,5,5,10,10);
        Process d = new Process(20,20,5,5,40,40);

        ArrayList<Process> processes = new ArrayList<>();
        processes.add(a);
        processes.add(b);
        processes.add(c);
        processes.add(d);

        Controller controller = loader.getController();

        SystemManager systemManager = new SystemManager(processes, AllocationType.FIRST_FIT, 100, new Process(20), controller);

        controller.setTotalProcesses(systemManager.getProcesses());

        systemManager.start();

    }

    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
