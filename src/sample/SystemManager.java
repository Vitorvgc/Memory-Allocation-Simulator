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
    private Process so;
    private Timer timer;

    private Controller controller;

    private int actualProcess;

    public SystemManager(ArrayList<Process> processes, AllocationType allocationType, int totalMemory, Process so, Controller controller) {
        this.processes = processes;
        this.allocationType = allocationType;
        this.memory = new Memory(totalMemory, so);
        this.processesQueue = new ArrayList<>();
        this.so = so;
        this.timer = new Timer();
        this.actualProcess = 0;
        this.controller = controller;
        this.controller.setProcesses(getProcesses());
    }

    public void start() {

        this.controller.allocateProcess(this.so, (double)so.getMemory() / memory.getTotalMemory(), (double)(memory.getTotalMemory() - so.getMemory()) / memory.getTotalMemory());

        TimerTask allocate = new TimerTask() {
            @Override
            public void run() {
                try {
                    allocateProcess(processes.get(0));
                    TimeCounter counter = new TimeCounter(processes.get(0).getTDurationProperty());
                    counter.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        this.timer.schedule(allocate, processes.get(0).getCreationTime() * SECOND_TIME);
    }

    private void allocateProcess(Process process) throws InterruptedException {
        int memoryPosition = this.memory.allocateProcess(process, this.allocationType);

        if(memoryPosition != -1) {

            double sizeProportion = (double)process.getMemory() / this.memory.getTotalMemory();
            double heightProportion = (double)memoryPosition / this.memory.getTotalMemory();

            System.out.printf("Processo %d alocado\n", process.getId());
            this.controller.allocateProcess(process, sizeProportion, heightProportion);

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
                            TimeCounter counter = new TimeCounter(nextProcess.getTDurationProperty());
                            counter.start();
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

        this.memory.desallocateProcess(process);
        this.controller.desallocateProcess(process);
        System.out.printf("Processo %d desalocado\n", process.getId());
        for(Process p : this.processesQueue) {
            this.allocateProcess(p);
            TimeCounter counter = new TimeCounter(p.getTDurationProperty());
            counter.start();
        }
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }
}