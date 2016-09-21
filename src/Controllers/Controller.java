package Controllers;

import Nodes.ProcessNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
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

    private ObservableList<Process> dataTable = FXCollections.observableArrayList();

    public Controller() {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    public void initialize() {
        allocateProcess(new Process(20), 0.5, 0.5);

        //this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().senhaPropertyProperty());
        this.tCreationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%d", cellData.getValue().getCreationTime())));
        this.tDurationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%d", cellData.getValue().getDuration())));
        this.memoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%d", cellData.getValue().getMemory())));

        processesTable.setItems(dataTable);

    }

    public void allocateProcess(Process process, double sizeProportion, double heightProportion) {

        ProcessNode node = new ProcessNode(process, sizeProportion * MEMORY_HEIGHT);

        node.setY(sizeProportion * MEMORY_HEIGHT);

        node.setFill(Paint.valueOf("#0000CC88"));

        this.memoryPane.getChildren().add(node);

    }

}
