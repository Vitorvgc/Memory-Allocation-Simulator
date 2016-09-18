package sample;

import java.util.Random;

/**
 * Created by Vítor on 17/09/2016.
 */
public class Process {

    private int creationTime;
    private int duration;
    private int memory;

    public Process(int memory) {
        this.creationTime = 0;
        this.duration = Integer.MAX_VALUE;
        this.memory = memory;
    }

    public Process(int minCreationTime, int maxCreationTime, int minDuration, int maxDuration, int minMemory, int maxMemory) {

        Random random = new Random();

        this.creationTime =  random.nextInt(maxCreationTime - minCreationTime) + minCreationTime;
        this.duration = random.nextInt(maxDuration - minDuration) + minDuration;
        this.memory = random.nextInt(maxMemory - minMemory) + minMemory;

        if(this.creationTime < 0 || this.duration < 0 || this.memory < 0)
            System.out.println("WARNING: Process with negative values");
    }

    public int getMemory() {
        return this.memory;
    }

}
