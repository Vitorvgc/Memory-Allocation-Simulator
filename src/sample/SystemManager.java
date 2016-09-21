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

    private int actualProcess;

    public SystemManager(ArrayList<Process> processes, AllocationType allocationType, int totalMemory, Process so) {
        this.processes = processes;
        this.allocationType = allocationType;
        this.memory = new Memory(totalMemory, so);
        this.processesQueue = new ArrayList<>();
        this.timer = new Timer();
        this.actualProcess = 0;
    }

    public void start() {

        this.allocateProcess(processes.get(0));
    }

    private void allocateProcess(Process process) {

        boolean allocationSucessful = this.memory.allocateProcess(process, this.allocationType);

        if(allocationSucessful) {
            System.out.println("Processo alocado");

            TimerTask desallocate = new TimerTask() {
                @Override
                public void run() {
                    desallocateProcess(process);
                }
            };
            this.timer.schedule(desallocate, process.getDuration() * 1000);

            if(++this.actualProcess < this.processes.size()) {
                Process nextProcess = processes.get(this.actualProcess);
                TimerTask allocate = new TimerTask() {
                    @Override
                    public void run() {
                        allocateProcess(nextProcess);
                    }
                };
                this.timer.schedule(allocate, nextProcess.getCreationTime() * 1000);
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

}