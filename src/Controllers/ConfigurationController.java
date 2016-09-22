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

/**
 * Created by edvaldojunior on 20/09/16.
 */
public class ConfigurationController implements ControlledScreen {

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

    private ScreensController myController;
    private Stage previousStage;

    private ArrayList<Process> processes;
    private AllocationType allocationType;
    private Process so;
    private int totalMemory;

    @FXML
    private void initialize() {
        typeBox.setItems(FXCollections.observableArrayList(
                "First fit", "Best fit", "Worst fit", "Next fit")
        );
        typeBox.setValue("First fit");
    }

    @FXML

    private void startSimulation() throws IOException {

        try {
            this.getData();
        } catch (NumberFormatException e) {
            System.out.println("Deu ruim em algum campo!");
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Memory Allocation Simulator");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Screens/sample.fxml"));
        Parent root = loader.load();

        previousStage.close();

        stage.setScene(new Scene(root, 830, 680));
        stage.show();

        Controller controller = loader.getController();

        SystemManager systemManager = new SystemManager(processes, this.allocationType, this.totalMemory, this.so, controller);
        systemManager.start();

    }

    private void getData() throws NumberFormatException {

        int minMemory = Integer.parseInt(this.m1Text.getText());
        int maxMemory = Integer.parseInt(this.m2Text.getText());
        int minCreationTime = Integer.parseInt(this.tc1Text.getText());
        int maxCreationTime = Integer.parseInt(this.tc2Text.getText());
        int minDuration = Integer.parseInt(this.td1Text.getText());
        int maxDuration = Integer.parseInt(this.td2Text.getText());
        int processesNumber = Integer.parseInt(this.nProcessText.getText());
        int soMemory = Integer.parseInt(this.sizeSOText.getText());
        this.totalMemory = Integer.parseInt(this.sizeMemoryText.getText());
        this.so = new Process(soMemory);

        this.processes = new ArrayList<>();
        for(int i = 0; i < processesNumber; i++) {
            this.processes.add(new Process(minCreationTime, maxCreationTime, minDuration, maxDuration, minMemory, maxMemory));
            System.out.println(this.processes.get(i).getId());
        }

        switch (typeBox.getValue().toString()) {
            case "First fit": this.allocationType = AllocationType.FIRST_FIT; break;
            case "Best fit" : this.allocationType = AllocationType.BEST_FIT;  break;
            case "Worst fit": this.allocationType = AllocationType.WORST_FIT; break;
            case "Next fit" : this.allocationType = AllocationType.NEXT_FIT;  break;
            default: Integer.parseInt("numberFormatException"); break;
        }
    }

    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

}