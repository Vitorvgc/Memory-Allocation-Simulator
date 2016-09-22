package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.min;

/**
 * Created by Vítor on 17/09/2016.
 */
public class Process {

    private static int globalId = 0;

    private int relativeCreationTime;
    private int realCreationTime;
    private int duration;
    private int memory;
    private int id;


    private StringProperty idProperty, tRelativeCreationProperty, tRealCreationProperty, tDurationProperty, memoryProperty,
                           tEndProperty, tWaitProperty, tAllocationProperty;

    public Process(int memory) {
        this.relativeCreationTime = 0;
        this.duration = Integer.MAX_VALUE;
        this.memory = memory;
        this.id = 0;

        idProperty = new SimpleStringProperty("0");
        tRelativeCreationProperty = new SimpleStringProperty("0");
        tRealCreationProperty = new SimpleStringProperty("-");
        tDurationProperty = new SimpleStringProperty("0");
        memoryProperty = new SimpleStringProperty("0");
        tEndProperty = new SimpleStringProperty("-");
        tWaitProperty = new SimpleStringProperty("-");
        tAllocationProperty = new SimpleStringProperty("-");
    }

    public Process(int minCreationTime, int maxCreationTime, int minDuration, int maxDuration, int minMemory, int maxMemory) {

        Random random = new Random();

        this.relativeCreationTime =  random.nextInt(maxCreationTime - minCreationTime + 1) + minCreationTime;
        this.duration = random.nextInt(maxDuration - minDuration + 1) + minDuration;
        this.memory = random.nextInt(maxMemory - minMemory + 1) + minMemory;
        this.id = ++globalId;

        idProperty = new SimpleStringProperty(String.format("%d", id));
        tRelativeCreationProperty = new SimpleStringProperty(String.format("%d", relativeCreationTime));
        tRealCreationProperty = new SimpleStringProperty(String.format("%d", realCreationTime));
        tDurationProperty = new SimpleStringProperty(String.format("%d", duration));
        memoryProperty = new SimpleStringProperty(String.format("%d", memory));
        tEndProperty = new SimpleStringProperty("-");
        tWaitProperty = new SimpleStringProperty("-");
        tAllocationProperty = new SimpleStringProperty("-");

        if(this.relativeCreationTime < 0 || this.duration < 0 || this.memory < 0)
            System.out.println("WARNING: Process with negative values");
    }

    public int getMemory() {
        return this.memory;
    }

    public int getCreationTime() {
        return this.relativeCreationTime;
    }

    public int getRealCreationTime() { return realCreationTime; }

    public int getDuration() {
        return this.duration;
    }

    public int getId() { return this.id; }

    public StringProperty getIDProperty() { return idProperty; }

    public StringProperty getTCreationProperty() { return tRelativeCreationProperty; }

    public StringProperty getTRealCreationProperty() { return tRealCreationProperty; }

    public StringProperty getTDurationProperty() { return tDurationProperty; }

    public StringProperty getMemoryProperty() { return memoryProperty; }

    public StringProperty getTEndProperty() {
        return tEndProperty;
    }

    public StringProperty getTWaitProperty() {
        return tWaitProperty;
    }

    public StringProperty getTAllocationProperty() { return tAllocationProperty; }

    public void setRealCreationTime(int realCreationTime) { this.realCreationTime = realCreationTime; }
}