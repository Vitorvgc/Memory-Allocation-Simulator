package Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by edvaldojunior on 20/09/16.
 */
public class ConfigurationController {

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

        //TODO: passar pro SystemManager
        Controller controller = loader.getController();


    }

    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }
}
