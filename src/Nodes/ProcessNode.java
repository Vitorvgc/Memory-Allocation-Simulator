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

    private static String colors[] = {"#0000FFCC", "#FF0000CC", "#00FF00CC", "0xFFFF00CC", "0xFF00FFCC", "0x00FFFFCC"};

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
        this.sprite.setFill(Paint.valueOf(this.process.getId() == 0 ? "0x666666" : colors[(this.process.getId() - 1) % colors.length]));
        this.sprite.setStroke(Paint.valueOf("#00000088"));
        this.sprite.setStrokeWidth(2);
    }

    private void initIdText(double height) {
        this.idText = new Label(String.format("P%d", this.process.getId()));
        if(this.process.getId() == 0) {
            this.idText.setText("SO");
            this.idText.setTextFill(Paint.valueOf("0xFFFFFF"));
        }
        this.idText.setStyle("-fx-font-weight: bold;");
        this.idText.setLayoutX(95);
        this.idText.setAlignment(Pos.CENTER);
    }

    public void toMiniVisualization() {

        this.sprite.setWidth(35);
        this.sprite.setHeight(35);
        this.idText.setLayoutX(9);
        this.idText.setLayoutY(9);
    }

    public Process getProcess() {
        return this.process;
    }

    public Rectangle getSprite() { return this.sprite; }

    public Label getIdText() { return this.idText; }

}
