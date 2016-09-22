package sample;

import Controllers.Controller;
import Enums.AllocationType;
import Enums.Constants;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by vitorchagas on 20/09/16.
 */
public class SystemManager {

    private Memory memory;
    private AllocationType allocationType;
    private ArrayList<Process> processes;
    private ArrayList<Process> processesQueue;
    private Process so;
    private Timer timer;
    private Clock clock;
    private int memoryUsed = 0;

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
        this.clock = new Clock(this.controller.getClockLabel());
        clock.start();
    }

    public void start() {

        this.controller.allocateProcess(this.so, (double)so.getMemory() / memory.getTotalMemory(),
                                       (double)(memory.getTotalMemory() - so.getMemory()) / memory.getTotalMemory());
        this.memoryUsed += this.so.getMemory();

        TimerTask allocate = new TimerTask() {
            @Override
            public void run() {
                try {
                    allocateProcess(processes.get(0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        this.timer.schedule(allocate, processes.get(0).getCreationTime() * Constants.TIME_SECOND.getValue());
    }

    private void allocateProcess(Process process) throws InterruptedException {

        int memoryPosition = this.memory.allocateProcess(process, this.allocationType);

        process.setRealCreationTime(getClock().getTime());
        process.getTRealCreationProperty().setValue(String.format("%d", getClock().getTime()));

        // schedule desallocation of process
        if(memoryPosition != -1) {

            process.getTAllocationProperty().setValue(String.format("%d", getClock().getTime()));
            process.getTEndProperty().setValue(String.format("%d", clock.getTime() + process.getDuration()));

            this.memoryUsed += process.getMemory();
            this.controller.getMemoryUsedProperty().setValue(String.format("%d ( %.1f %% )",
                                      this.memoryUsed, (double) this.memoryUsed/ this.memory.getTotalMemory() * 100.0));

            if(this.processesQueue.contains(process)) this.processesQueue.remove(process);

            double sizeProportion = (double)process.getMemory() / this.memory.getTotalMemory();
            double heightProportion = (double)memoryPosition / this.memory.getTotalMemory();

            System.out.printf("Processo %d alocado\n", process.getId());
            this.controller.allocateProcess(process, sizeProportion, heightProportion);

            if(process.getId() != 0) {
                TimeCounter counter = new TimeCounter(process.getTDurationProperty());
                counter.start();
            }

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
            this.timer.schedule(desallocate, process.getDuration() * Constants.TIME_SECOND.getValue());
        }
        else if(!this.processesQueue.contains(process)){
            this.processesQueue.add(process);
            System.out.println("Processo nao pode ser alocado e entrou na fila");
        }

        // schedule allocation of next process, if there is any
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
            this.timer.schedule(allocate, nextProcess.getCreationTime() * Constants.TIME_SECOND.getValue());
        }

    }

    private void desallocateProcess(Process process) throws InterruptedException {

        process.getTWaitProperty().setValue(String.format("%d", clock.getTime() - process.getRealCreationTime()));

        this.memory.desallocateProcess(process);
        this.controller.desallocateProcess(process);
        this.memoryUsed -= process.getMemory();
        this.controller.getMemoryUsedProperty().setValue(String.format("%d ( %.1f %% )",
                                      this.memoryUsed, (double) this.memoryUsed/ this.memory.getTotalMemory() * 100.0));

        System.out.printf("Processo %d desalocado\n", process.getId());

        List syncProcessQueue = Collections.synchronizedList(this.processesQueue);

        try {
            synchronized (syncProcessQueue) {
                for (Iterator it = syncProcessQueue.iterator(); it.hasNext(); ) {
                    Process p = syncProcessQueue.isEmpty() ? null : (Process) it.next();
                    this.allocateProcess(p);
                }
            }
        } catch (ConcurrentModificationException e) {
            // do nothing
        } catch (NullPointerException e2) {
            // do nothing too (there is no process in queue)
        }

    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public Clock getClock() {
        return clock;
    }
}