package Controllers;

import Nodes.ProcessNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import sample.Process;
import sample.SystemManager;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.Semaphore;


public class Controller implements ControlledScreen {

    private static int MEMORY_HEIGHT = 550;

    private ArrayList<ProcessNode> processes;

    private ArrayList<Process> totalProcesses;

    @FXML
    private Pane memoryPane;
    @FXML
    private TableView <Process> processesTable;
    @FXML
    private AnchorPane wantingPane;
    @FXML
    private TableColumn<Process, String> idColumn;
    @FXML
    private TableColumn<Process, String> tCreationColumn;
    @FXML
    private TableColumn<Process, String> tDurationColumn;
    @FXML
    private TableColumn<Process, String> memoryColumn;
    @FXML
    private TableColumn<Process, String> tWaitColumn;
    @FXML
    private TableColumn<Process, String> tAlocationColumn;
    @FXML
    private TableColumn<Process, String> tEndColumn;

//    private final StringProperty idProperty, tCreationProperty, tDurationProperty, tEndProperty,
//                                 memoryProperty, tWaitProperty, tAlocationProperty;

    ScreensController myController;

    private ObservableList<Process> dataTable;

    public Controller() {

        //dataTable = FXCollections.observableArrayList(SystemManager.get)
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void initialize() {

        //allocateProcess(new Process(20), 0.5, 0.5);
        this.processes = new ArrayList<>();

        //this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().senhaPropertyProperty());
        this.tCreationColumn.setCellValueFactory(cellData -> cellData.getValue().getTCreationProperty());
        this.tDurationColumn.setCellValueFactory(cellData -> cellData.getValue().getTDurationProperty());
        this.memoryColumn.setCellValueFactory(cellData -> cellData.getValue().getMemoryProperty());

        processesTable.setItems(dataTable);
    }

    public void allocateProcess(Process process, double sizeProportion, double heightProportion) {

        ProcessNode node = new ProcessNode(process, sizeProportion * MEMORY_HEIGHT);

        node.setY(heightProportion * MEMORY_HEIGHT);

        node.setFill(Paint.valueOf("#0000CC88"));

        Platform.runLater(() -> this.processes.add(node));

        Platform.runLater(() -> memoryPane.getChildren().add(node));

    }

    public void desallocateProcess(Process process) {

        List safeProcesses = Collections.synchronizedList(this.processes);

        synchronized (safeProcesses) {
            for(Iterator it = safeProcesses.iterator(); it.hasNext();) {
                ProcessNode p = safeProcesses.isEmpty() ? null : (ProcessNode) it.next();
                if(p == null) break;
                if (p.getProcess().getId() == process.getId()) {
                    Platform.runLater(() -> this.memoryPane.getChildren().remove(p));
                    this.processes.remove(p);
                }
            }
        }
    }

    public void setTotalProcesses(ArrayList<Process> totalProcesses) {
        this.totalProcesses = totalProcesses;
        dataTable = FXCollections.observableArrayList(this.totalProcesses);
        System.out.print("Processos = " + dataTable);
    }

}
