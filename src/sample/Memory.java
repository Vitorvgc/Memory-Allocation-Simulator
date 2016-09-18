package sample;

import Enums.AllocationType;

import java.util.ArrayList;

/**
 * Created by Vítor on 17/09/2016.
 */
public class Memory {

    private int totalMemory;
    private Process so;
    private ArrayList<Node> memory;

    public Memory(int totalMemory, Process so) {
        this.totalMemory = totalMemory;
        this.so = so;
        this.memory = new ArrayList<>();
        this.memory.add(new Node(this.totalMemory - this.so.getMemory()));
        this.memory.add(new Node(this.so.getMemory()));
    }

    public void allocateProcess(Process process, AllocationType type) {
        switch(type) {
            case FIRST_FIT: allocateProcessWithFirstFit(process);  break;
            case BEST_FIT:  allocateProcessWithBestFit(process);   break;
            case WORST_FIT: allocateProcessWithWorsttFit(process); break;
            case NEXT_FIT:  allocateProcessWithNextFit(process);   break;
        }
    }

    public void desallocateProcess(Process process) {

    }

    private void allocateProcessWithFirstFit(Process p) {

    }

    private void allocateProcessWithBestFit(Process p) {

    }

    private void allocateProcessWithWorsttFit(Process p) {

    }

    private void allocateProcessWithNextFit(Process p) {

    }

    private class Node {

        boolean free;
        int space;
        Process process;

        // Constructor to create an occupied node
        private Node(Process process) {
            this.free = false;
            this.space = process.getMemory();
            this.process = process;
        }

        // Constructor to create a free node
        private Node(int space) {
            this.free = true;
            this.space = space;
            this.process = null;
        }
    }
}
