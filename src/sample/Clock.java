package sample;

import Enums.Constants;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Created by edvaldojunior on 22/09/16.
 */
public class Clock extends Thread {

    private int time;
    private Label timeLabel;

    public Clock(Label timeLabel) {

        System.out.print("Criou Clock\n");
        this.time = 0;
        this.timeLabel = timeLabel;
    }

    @Override
    public void run() {

        long times[] = {System.currentTimeMillis(), System.currentTimeMillis()};
        for(;;) {
            long aux[] = {System.currentTimeMillis(), System.currentTimeMillis()};
            if(aux[0] - times[0] >= Constants.TIME_SECOND.getValue()) {
                time++;
                Platform.runLater(() -> timeLabel.setText(String.format("%d", time)));
                times[0] = aux[0];
            }
        }
    }

    public int getTime() {
        return this.time;
    }
}