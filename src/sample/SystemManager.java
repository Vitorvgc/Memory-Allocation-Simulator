package sample;

import Enums.AllocationType;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by vitorchagas on 20/09/16.
 */
public class SystemManager {

    private int processesNumber;
    private Memory memory;
    private AllocationType allocationType;

    private ArrayList<Process> processes;
    private ArrayList<Process> processesQueue;

    private Timer timer;

    public SystemManager(ArrayList<Process> processes, AllocationType allocationType, int totalMemory, Process so) {
        this.processes = processes;
        this.processesNumber = processes.size();
        this.allocationType = allocationType;
        this.memory = new Memory(totalMemory, so);
        this.processesQueue = new ArrayList<>();
        this.timer = new Timer();
    }

    public void allocateProcess(Process process) {

    }

    public void desallocateProcess(Process process) {

    }

}