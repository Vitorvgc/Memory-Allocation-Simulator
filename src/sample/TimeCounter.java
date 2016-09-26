package sample;

import Enums.Constants;
import javafx.beans.property.StringProperty;

/**
 * Created by Edy Junior on 22/09/2016.
 *
 * Thread that representes a countdown, used in main
 * screen's table to update the processes duration
 */
public class TimeCounter extends Thread {

    private StringProperty timeProperty;

    public TimeCounter(StringProperty timeProperty) {

        this.timeProperty = timeProperty;
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
