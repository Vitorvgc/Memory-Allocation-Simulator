package Nodes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import sample.Process;

/**
 * Created by Vítor on 21/09/2016.
 */
public class ProcessNode extends AnchorPane {

    private static int colorId = 0;
    private static String colors[] = {"#0000CC88", "#CC000088", "#00CC0088"};

    private Process process;

    private Rectangle sprite;
    private Label idText;

    public ProcessNode(Process process, double height) {
        this.process = process;

        this.initSprite(height);
        this.initIdText(height);

        this.getChildren().add(this.sprite);
        this.getChildren().add(this.idText);
    }

    private void initSprite(double height) {
        this.sprite = new Rectangle();
        this.sprite.setWidth(200);
        this.sprite.setHeight(height);
        this.sprite.setFill(Paint.valueOf(colors[colorId]));
        this.sprite.setStroke(Paint.valueOf("#666666"));

        colorId = (colorId + 1) % colors.length;
    }

    private void initIdText(double height) {
        this.idText = new Label(String.format("P%d", this.process.getId()));
        this.idText.setLayoutX(95);
        this.idText.setAlignment(Pos.CENTER);
    }

    public Process getProcess() {
        return this.process;
    }

    public Rectangle getSprite() { return this.sprite; }

    public Label getIdText() { return this.idText; }

}
