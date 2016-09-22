package sample;

import Enums.Constants;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

/**
 * Created by edvaldojunior on 22/09/16.
 */
public class Clock extends Thread {

    private int time;
    private StringProperty timeProperty;
    //private Label timeLabel;

    public Clock() {

        System.out.print("Criou Clock\n");
        this.time = 0;
        this.timeProperty = new SimpleStringProperty(String.format("%d", time));
        //this.timeLabel = timeLabel;

//        this.timeProperty.addListener((observable, oldValue, newValue) -> {
//            if(newValue != null) {
//                this.timeLabel.setText(this.timeProperty.get());
//            }
//        });
    }

    @Override
    public void run() {
        //time = Integer.parseInt(timeProperty.get());
        long times[] = {System.currentTimeMillis(), System.currentTimeMillis()};
        for(;;) {
            long aux[] = {System.currentTimeMillis(), System.currentTimeMillis()};
            if(aux[0] - times[0] >= Constants.TIME_SECOND.getValue()) {
                time++;
                //timeProperty.setValue(String.valueOf(time++));
                times[0] = aux[0];
                System.out.print("Mudou para" + time + "\n");
            }
        }
    }

    public int getTime() {
        return this.time;
    }
}