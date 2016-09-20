package sample;

import Enums.AllocationType;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vitorchagas on 20/09/16.
 */
public class SystemManager {


    private Memory memory;
    private AllocationType allocationType;
    private ArrayList<Process> processes;
    private ArrayList<Process> processesQueue;
    private Timer timer;

    public SystemManager(ArrayList<Process> processes, AllocationType allocationType, int totalMemory, Process so) {
        this.processes = processes;
        this.allocationType = allocationType;
        this.memory = new Memory(totalMemory, so);
        this.processesQueue = new ArrayList<>();
        this.timer = new Timer();
    }

    public void start() {

    }

    private void allocateProcess(Process process) {

        boolean allocationSucessful = this.memory.allocateProcess(process, this.allocationType);

        if(allocationSucessful) {

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    desallocateProcess(process);
                }
            };

            this.timer.schedule(timerTask, process.getDuration());

        }

    }

    private void desallocateProcess(Process process) {

    }

}