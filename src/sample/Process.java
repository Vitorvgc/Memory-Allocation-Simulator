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

    private int creationTime;
    private int duration;
    private int memory;
    private int id;


    private final StringProperty idProperty, tCreationProperty, tDurationProperty, memoryProperty;//,
                                 //tEndProperty, tWaitProperty, tAlocationProperty;

    public Process(int memory) {
        this.creationTime = 0;
        this.duration = Integer.MAX_VALUE;
        this.memory = memory;
        this.id = ++globalId;

        idProperty = new SimpleStringProperty("0");
        tCreationProperty = new SimpleStringProperty("0");
        tDurationProperty = new SimpleStringProperty("0");
        memoryProperty = new SimpleStringProperty("0");
    }

    public Process(int minCreationTime, int maxCreationTime, int minDuration, int maxDuration, int minMemory, int maxMemory) {

        Random random = new Random();

        this.creationTime =  random.nextInt((int)min(maxCreationTime - minCreationTime, 1)) + minCreationTime;
        this.duration = random.nextInt((int)min(maxDuration - minDuration, 1)) + minDuration;
        this.memory = random.nextInt((int)min(maxMemory - minMemory, 1)) + minMemory;
        this.id = ++globalId;

        idProperty = new SimpleStringProperty(String.format("%d", id));
        tCreationProperty = new SimpleStringProperty(String.format("%d", creationTime));
        tDurationProperty = new SimpleStringProperty(String.format("%d", duration));
        memoryProperty = new SimpleStringProperty(String.format("%d", memory));

        if(this.creationTime < 0 || this.duration < 0 || this.memory < 0)
            System.out.println("WARNING: Process with negative values");
    }

    public int getMemory() {
        return this.memory;
    }

    public int getCreationTime() {
        return this.creationTime;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getId() { return this.id; }


    public StringProperty getIDProperty() { return idProperty; }

    public StringProperty getTCreationProperty() { return tCreationProperty; }

    public StringProperty getTDurationProperty() { return tDurationProperty; }

    public StringProperty getMemoryProperty() { return memoryProperty; }

//    public StringProperty getTEndProperty() {
//        return tEndProperty;
//    }
//
//    public StringProperty getTWaitProperty() {
//        return tWaitProperty;
//    }
//
//    public StringProperty getTAlocationProperty() {
//        return tAlocationProperty;
//    }
}
