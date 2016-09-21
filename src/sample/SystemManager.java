package sample;

import Controllers.Controller;
import Enums.AllocationType;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vitorchagas on 20/09/16.
 */
public class SystemManager {

    private final int SECOND_TIME = 1000; // Duration of a second in the simulation, in miliseconds

    private Memory memory;
    private AllocationType allocationType;
    private ArrayList<Process> processes;
    private ArrayList<Process> processesQueue;
    private Timer timer;

    private Controller controller;

    private int actualProcess;

    public SystemManager(ArrayList<Process> processes, AllocationType allocationType, int totalMemory, Process so, Controller controller) {
        this.processes = processes;
        this.allocationType = allocationType;
        this.memory = new Memory(totalMemory, so);
        this.processesQueue = new ArrayList<>();
        this.timer = new Timer();
        this.actualProcess = 0;
        this.controller = controller;
    }

    public void start() {
        this.allocateProcess(processes.get(0));
    }

    private void allocateProcess(Process process) {

        int memoryPosition = this.memory.allocateProcess(process, this.allocationType);

        this.controller.allocateProcess(process, 0.25, 0.25);

        if(memoryPosition != -1) {
            System.out.println("Processo alocado");

            TimerTask desallocate = new TimerTask() {
                @Override
                public void run() {
                    desallocateProcess(process);
                    this.cancel();
                }
            };
            this.timer.schedule(desallocate, process.getDuration() * SECOND_TIME);

            if(++this.actualProcess < this.processes.size()) {
                Process nextProcess = processes.get(this.actualProcess);
                TimerTask allocate = new TimerTask() {
                    @Override
                    public void run() {
                        allocateProcess(nextProcess);
                        this.cancel();
                    }
                };
                this.timer.schedule(allocate, nextProcess.getCreationTime() * SECOND_TIME);
            }
        }
        else if(!this.processesQueue.contains(process)){
            this.processesQueue.add(process);
            System.out.println("Processo nao pode ser alocado, entrou na fila");
        }
    }

    private void desallocateProcess(Process process) {

        this.memory.desallocateProcess(process);
        System.out.println("Processo desalocado");
        for(Process p : this.processesQueue) {
            this.allocateProcess(p);
        }
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }
}