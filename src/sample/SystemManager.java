package sample;

import Controllers.Controller;
import Enums.AllocationType;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

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

    private Semaphore allocationMutex;
    private Semaphore desallocationMutex;

    public SystemManager(ArrayList<Process> processes, AllocationType allocationType, int totalMemory, Process so, Controller controller) {
        this.processes = processes;
        this.allocationType = allocationType;
        this.memory = new Memory(totalMemory, so);
        this.processesQueue = new ArrayList<>();
        this.timer = new Timer();
        this.actualProcess = 0;
        this.controller = controller;

        this.allocationMutex = new Semaphore(1);
        this.desallocationMutex = new Semaphore(1);
    }

    public void start() {

        try {
            this.allocateProcess(processes.get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void allocateProcess(Process process) throws InterruptedException {

        this.allocationMutex.acquire();
        int memoryPosition = this.memory.allocateProcess(process, this.allocationType);
        this.allocationMutex.release();

        if(memoryPosition != -1) {

            double sizeProportion = (double)process.getMemory() / this.memory.getTotalMemory();
            double heightProportion = (double)memoryPosition / this.memory.getTotalMemory();
            //System.out.println("Processo alocado");
            //System.out.printf("Tamanho: %f Altura: %f\n", sizeProportion, heightProportion);

            System.out.printf("Processo %d allocado\n", process.getId());
            this.allocationMutex.acquire();
            this.controller.allocateProcess(process, sizeProportion, heightProportion);
            this.allocationMutex.release();

            TimerTask desallocate = new TimerTask() {
                @Override
                public void run() {
                    try {
                        desallocateProcess(process);
                    } catch (InterruptedException e) {

                    }
                    this.cancel();
                }
            };
            this.timer.schedule(desallocate, process.getDuration() * SECOND_TIME);

            if(++this.actualProcess < this.processes.size()) {
                Process nextProcess = processes.get(this.actualProcess);
                TimerTask allocate = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            allocateProcess(nextProcess);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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

    private void desallocateProcess(Process process) throws InterruptedException {

        this.desallocationMutex.acquire();
        this.memory.desallocateProcess(process);
        this.controller.desallocateProcess(process);
        this.desallocationMutex.release();
        System.out.printf("Processo %d desalocado\n", process.getId());
        for(Process p : this.processesQueue) {
            this.allocateProcess(p);
        }

    }

}