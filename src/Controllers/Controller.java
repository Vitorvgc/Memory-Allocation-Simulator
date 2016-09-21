package Controllers;

import Nodes.ProcessNode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import sample.Process;

import java.util.ArrayList;


public class Controller implements ControlledScreen {

    private static int MEMORY_HEIGHT = 550;

    private ArrayList<ProcessNode> processes;

    @FXML
    private Pane memoryPane;

    @FXML
    private TableView processesTable;

    @FXML
    private AnchorPane wantingPane;

    ScreensController myController;

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void initialize() {

        this.processes = new ArrayList<>();
    }

    public void allocateProcess(Process process, double sizeProportion, double heightProportion) {

        ProcessNode node = new ProcessNode(process, sizeProportion * MEMORY_HEIGHT);

        node.setY(sizeProportion * MEMORY_HEIGHT);

        node.setFill(Paint.valueOf("#0000CC88"));

        Platform.runLater(() -> memoryPane.getChildren().add(node));
        
    }

    public void desallocateProcess(Process process) {

        for(ProcessNode p : processes)
            if(p.getProcess() == process)
                this.memoryPane.getChildren().remove(p);

    }

}
