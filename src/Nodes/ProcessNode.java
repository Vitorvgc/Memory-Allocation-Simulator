package Nodes;

import javafx.scene.shape.Rectangle;
import sample.Process;

/**
 * Created by Vítor on 21/09/2016.
 */
public class ProcessNode extends Rectangle {

    Process process;

    public ProcessNode(Process process, double height) {
        this.process = process;
        this.setWidth(200);
        this.setHeight(height);
    }

}
