package sample;

import Enums.Constants;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.ConstraintsBase;

/**
 * Created by Edy Junior on 22/09/2016.
 */
public class TimeCounter extends Thread {

    private int time;
    private StringProperty timeProperty;

    public TimeCounter(StringProperty timeProperty) {

        this.timeProperty = timeProperty;
        this.time = Integer.parseInt(timeProperty.get());
    }

    @Override
    public void run() {
        for(int counter = Integer.parseInt(timeProperty.get()); counter > 0;) {
            try {
                sleep(Constants.TIME_SECOND.getValue());
                timeProperty.setValue(String.valueOf(--counter));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
