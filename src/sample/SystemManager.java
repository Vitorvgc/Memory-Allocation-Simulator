package sample;

import Controllers.Controller;
import Enums.AllocationType;
import Enums.Constants;

import java.util.*;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 * Created by vitorchagas on 20/09/16.
 *
 * Class responsable of managing the memory allocation and
 * desallocation and the updates of the user interface.
 */
public class SystemManager {

    private Memory memory;
    private AllocationType allocationType;
    private ArrayList<Process> processes;
    private ArrayList<Process> processesQueue;
    private Process so;
    private Timer timer;
    private Clock clock;
    private int memoryUsed;
    private int maxMemoryUsed;

    private int totalWaitTime;
    private int finishedProcesses;

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
        this.controller.setProcesses(this.processes);
        this.totalWaitTime = 0;
        this.finishedProcesses = 0;
        this.memoryUsed = 0;
        this.maxMemoryUsed = this.memoryUsed;
        this.clock = new Clock(this.controller.getClockLabel());
        this.clock.start();
    }

    public void start() {

        this.controller.allocateProcess(this.so, (double)so.getMemory() / memory.getTotalMemory(),
                                       (double)(memory.getTotalMemory() - so.getMemory()) / memory.getTotalMemory());
        this.memoryUsed += this.so.getMemory();
        this.maxMemoryUsed = max(this.maxMemoryUsed, this.memoryUsed);
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

        if(process.getTRealCreationProperty().getValue().equals("0")) {
            process.setRealCreationTime(this.clock.getTime());
            process.getTRealCreationProperty().setValue(String.format("%d", this.clock.getTime()));
        }

        // schedule desallocation of process
        if(memoryPosition != -1) {

            process.getTAllocationProperty().setValue(String.format("%d", clock.getTime()));
            process.getTEndProperty().setValue(String.format("%d", clock.getTime() + process.getDuration()));

            this.memoryUsed += process.getMemory();
            this.maxMemoryUsed = max(this.maxMemoryUsed, this.memoryUsed);
            this.controller.getMemoryUsedProperty().setValue(String.format("%d ( %.1f %% )",
                                      this.memoryUsed, (double) this.memoryUsed/ this.memory.getTotalMemory() * 100.0));

            if(this.processesQueue.contains(process)) {
                this.processesQueue.remove(process);
                this.controller.updateQueue(this.processesQueue);
            }

            double sizeProportion = (double)process.getMemory() / this.memory.getTotalMemory();
            double heightProportion = (double)memoryPosition / this.memory.getTotalMemory();

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
                    } catch (InterruptedException ignored) {

                    }
                    this.cancel();
                }
            };
            this.timer.schedule(desallocate, process.getDuration() * Constants.TIME_SECOND.getValue());
        }
        else if(!this.processesQueue.contains(process)){
            this.processesQueue.add(process);
            this.controller.updateQueue(this.processesQueue);
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

        this.finishedProcesses++;

        process.getTWaitProperty().setValue(String.format("%d", clock.getTime() - process.getRealCreationTime()));

        this.totalWaitTime += clock.getTime() - process.getRealCreationTime();

        this.controller.getAverageWaitingTimeProperty().setValue(String.format("%.1f", (double)this.totalWaitTime / this.finishedProcesses));


        this.memory.desallocateProcess(process);
        this.controller.desallocateProcess(process);
        this.memoryUsed -= process.getMemory();
        this.controller.getMemoryUsedProperty().setValue(String.format("%d ( %.1f %% )",
                                      this.memoryUsed, (double) this.memoryUsed/ this.memory.getTotalMemory() * 100.0));

        if(this.finishedProcesses == this.processes.size()) {
            this.finish();

        }

        List syncProcessQueue = Collections.synchronizedList(this.processesQueue);
        try {
            synchronized (syncProcessQueue) {
                for (Object processObject : syncProcessQueue) {
                    Process p = syncProcessQueue.isEmpty() ? null : (Process) processObject;
                    this.allocateProcess(p);
                }
            }
        } catch (ConcurrentModificationException | NullPointerException e) {
            // do nothing
        }

    }

    private void finish() {
        this.clock.flag = false;
        this.clock = null;

        System.out.println("--- End of simulation ---");
        System.out.printf("Maximum of memory used: %d (%.1f%%)\n", maxMemoryUsed, 100 * (double)maxMemoryUsed / memory.getTotalMemory());
    }

}