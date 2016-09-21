package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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
    private void startSimulation() {

        System.out.print("dsdsdsdsd\n");
        int validation = 0;
        if(!nProcessText.getText().isEmpty()) {
            validation++;
        }
        if(!sizeMemoryText.getText().isEmpty()) {
            validation++;
        }
        if(!sizeSOText.getText().isEmpty()) {
            validation++;
        }
        if(!m1Text.getText().isEmpty()) {
            validation++;
        }
        if(!m2Text.getText().isEmpty()) {
            validation++;
        }
        if(!tc1Text.getText().isEmpty()) {
            validation++;
        }
        if(!tc2Text.getText().isEmpty()) {
            validation++;
        }
        if(!td1Text.getText().isEmpty()) {
            validation++;
        }
        if(!td2Text.getText().isEmpty()) {
            validation++;
        }
        if(validation == numberOfElements) {

            myController.setScreen(Main.screen1Id);
            myController.setPrefWidth(900);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
