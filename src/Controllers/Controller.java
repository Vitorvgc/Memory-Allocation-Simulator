package Controllers;

import Nodes.ProcessNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sample.Process;

import java.util.Optional;

public class Controller {

    private static int MEMORY_HEIGHT = 550;


    @FXML
    private Pane memoryPane;

    @FXML
    private TableView processesTable;

    @FXML
    private AnchorPane wantingPane;


    public void clicou() {
        System.out.print("SSSS\n");
    }

    public void newProcess() {

        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
    }

    @FXML
    public void initialize() {
        allocateProcess(new Process(20), 0.5, 0.5);
    }

    public void allocateProcess(Process process, double sizeProportion, double heightProportion) {

        ProcessNode node = new ProcessNode(process, sizeProportion * MEMORY_HEIGHT);

        node.setY(sizeProportion * MEMORY_HEIGHT);

        node.setFill(Paint.valueOf("#0000CC88"));

        this.memoryPane.getChildren().add(node);

    }

}
