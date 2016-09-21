package Controllers;

import Nodes.ProcessNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import sample.Process;
import sample.SystemManager;


public class Controller implements ControlledScreen {

    private static int MEMORY_HEIGHT = 550;


    @FXML
    private Pane memoryPane;

    @FXML
    private TableView <Process> processesTable;

    @FXML
    private AnchorPane wantingPane;

    ScreensController myController;

    ObservableList<Process> dataTable;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void initialize() {
        allocateProcess(new Process(20), 0.5, 0.5);
        dataTable = FXCollections.observableArrayList();

    }

    public void allocateProcess(Process process, double sizeProportion, double heightProportion) {

        ProcessNode node = new ProcessNode(process, sizeProportion * MEMORY_HEIGHT);

        node.setY(sizeProportion * MEMORY_HEIGHT);

        node.setFill(Paint.valueOf("#0000CC88"));

        this.memoryPane.getChildren().add(node);

    }

}
