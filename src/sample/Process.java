package sample;

import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.min;

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

        this.creationTime =  random.nextInt((int)min(maxCreationTime - minCreationTime, 1)) + minCreationTime;
        this.duration = random.nextInt((int)min(maxDuration - minDuration, 1)) + minDuration;
        this.memory = random.nextInt((int)min(maxMemory - minMemory, 1)) + minMemory;

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

}
