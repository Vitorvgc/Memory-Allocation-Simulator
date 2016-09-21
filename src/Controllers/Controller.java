package Controllers;

import Nodes.ProcessNode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import sample.Process;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Semaphore;


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

        node.setY(heightProportion * MEMORY_HEIGHT);

        node.setFill(Paint.valueOf("#0000CC88"));

        this.processes.add(node);

        //System.out.println(node.getProcess());

        Platform.runLater(() -> memoryPane.getChildren().add(node));

    }

    public void desallocateProcess(Process process) {

        //System.out.printf("Process: ");
        //System.out.println(process);


        /** problemas com concorrencia **/
        /** http://blog.caelum.com.br/concurrentmodificationexception-e-os-fail-fast-iterators/ **/

        for (Iterator<ProcessNode> it = this.processes.iterator(); it.hasNext(); ) {
            ProcessNode p = it.next();
            if (p.getProcess().getId() == process.getId()) {
                Platform.runLater(() -> this.memoryPane.getChildren().remove(p));
                this.processes.remove(p);
                //System.out.println("morreu");
            }
        }


        /*
        for(ProcessNode p : processes) {
            //System.out.print(p.getProcess());
            if (p.getProcess().getId() == process.getId()) {
                Platform.runLater(() -> this.memoryPane.getChildren().remove(p));
                this.processes.remove(p);
                //System.out.println("morreu");
            }
        }
        */

    }

}
