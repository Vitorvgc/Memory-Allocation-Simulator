import javafx.beans.property.StringProperty;

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
        int counter = Integer.parseInt(timeProperty.get());
        long times[] = {System.currentTimeMillis(), System.currentTimeMillis()};
        for(int i = 0; counter > 0;) {
            long aux[] = {System.currentTimeMillis(), System.currentTimeMillis()};
            if(aux[0] - times[0] >= 1000) {
                timeProperty.setValue(String.valueOf(--counter));
                times[0] = aux[0];
            }
        }
    }

}
