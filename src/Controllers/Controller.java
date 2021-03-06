package Controllers;

import Nodes.ProcessNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Process;

import java.util.*;

/**
 * Controller that manages the attributes of the main screen.
 */
public class Controller {

    private static int MEMORY_HEIGHT = 550;

    private ArrayList<ProcessNode> processes;
    //private ProcessNode so;

    @FXML
    private Pane memoryPane;
    @FXML
    private TableView <Process> processesTable;
    @FXML
    private AnchorPane waitingPane;
    @FXML
    private Label clockLabel;
    @FXML
    private TableColumn<Process, String> idColumn;
    @FXML
    private TableColumn<Process, String> tCreationColumn;
    @FXML
    private TableColumn<Process, String> tRealCreationColumn;
    @FXML
    private TableColumn<Process, String> tDurationColumn;
    @FXML
    private TableColumn<Process, String> memoryColumn;
    @FXML
    private TableColumn<Process, String> tWaitColumn;
    @FXML
    private TableColumn<Process, String> tEndColumn;
    @FXML
    private TableColumn<Process, String> tAllocationColumn;
    @FXML
    private Label memoryUsedLabel;
    @FXML
    private Label averageWaitingTimeLabel;

    private ObservableList<Process> dataTable = FXCollections.observableArrayList();

    private StringProperty memoryUsedProperty;
    private StringProperty averageWaitingTimeProperty;

    @FXML
    public void initialize() {

        this.processes = new ArrayList<>();

        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
        this.memoryColumn.setCellValueFactory(cellData -> cellData.getValue().getMemoryProperty());
        this.tCreationColumn.setCellValueFactory(cellData -> cellData.getValue().getTCreationProperty());
        this.tRealCreationColumn.setCellValueFactory(cellData -> cellData.getValue().getTRealCreationProperty());
        this.tAllocationColumn.setCellValueFactory(cellData -> cellData.getValue().getTAllocationProperty());
        this.tDurationColumn.setCellValueFactory(cellData -> cellData.getValue().getTDurationProperty());
        this.tEndColumn.setCellValueFactory(cellData -> cellData.getValue().getTEndProperty());
        this.tWaitColumn.setCellValueFactory(cellData -> cellData.getValue().getTWaitProperty());

        for(TableColumn col : this.processesTable.getColumns()) {
            col.setStyle("-fx-alignment: CENTER;");
        }
        this.tCreationColumn.setStyle("-fx-alignment: CENTER;");
        this.tRealCreationColumn.setStyle("-fx-alignment: CENTER;");

        this.memoryUsedProperty = new SimpleStringProperty();
        this.memoryUsedProperty.addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                Platform.runLater(() -> this.memoryUsedLabel.setText(newValue));
            }
        });
        this.memoryUsedProperty.setValue("0 ( 0 % )");

        this.averageWaitingTimeProperty = new SimpleStringProperty();
        this.averageWaitingTimeProperty.addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                Platform.runLater(() -> this.averageWaitingTimeLabel.setText(newValue + " s"));
            }
        });


        processesTable.setItems(dataTable);
    }

    public void allocateProcess(Process process, double sizeProportion, double heightProportion) {

        ProcessNode node = new ProcessNode(process, sizeProportion * MEMORY_HEIGHT);

        node.getSprite().setY(heightProportion * MEMORY_HEIGHT);
        node.getIdText().setLayoutY((sizeProportion/2 + heightProportion) * MEMORY_HEIGHT - 5);

        Platform.runLater(() -> this.processes.add(node));
        Platform.runLater(() -> memoryPane.getChildren().add(node));


    }

    public void desallocateProcess(Process process) {

        List safeProcesses = Collections.synchronizedList(this.processes);

        try {
            synchronized (safeProcesses) {
                for (Object safeProcess : safeProcesses) {
                    ProcessNode p = safeProcesses.isEmpty() ? null : (ProcessNode) safeProcess;
                    if (p == null) break;
                    if (p.getProcess().getId() == process.getId()) {
                        Platform.runLater(() -> this.memoryPane.getChildren().remove(p));
                        this.processes.remove(p);
                        break;
                    }
                }
            }
        } catch(ConcurrentModificationException e) {
            // do nothing
        }
    }

    public void updateQueue(ArrayList<Process> processes) {

        Platform.runLater(() -> this.waitingPane.getChildren().clear());

        this.waitingPane.setLayoutX(processes.size() * 30);

        for(int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);

            ProcessNode node = new ProcessNode(process, 10);
            node.toMiniVisualization();
            node.setTranslateY(8);
            node.setTranslateX(10 + i * (10 + node.getSprite().getWidth()));

            Platform.runLater(() -> this.waitingPane.getChildren().add(node));

        }
    }

    public void setProcesses(ArrayList<Process> totalProcesses) {
//        for(Process process : totalProcesses) {
//            dataTable.add(process);
//        }
        dataTable.addAll(totalProcesses);
    }

    public Label getClockLabel() {
        return clockLabel;
    }

    public StringProperty getMemoryUsedProperty() { return memoryUsedProperty; }

    public StringProperty getAverageWaitingTimeProperty() { return averageWaitingTimeProperty; }

//    public TableView<Process> getProcessesTable() {
//        return processesTable;
//    }
}
