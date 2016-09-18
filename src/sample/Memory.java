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
        this.memory.add(new Node(this.so));
    }

    // for test purporses
    public void printMemory() {
        for(Node n : this.memory)
            System.out.printf("|%b - %d| ", n.free, n.size);
        System.out.println();
    }

    public void allocateProcess(Process process, AllocationType type) {
        switch(type) {
            case FIRST_FIT: allocateProcessWithFirstFit(process);  break;
            case BEST_FIT:  allocateProcessWithBestFit(process);   break;
            case WORST_FIT: allocateProcessWithWorsttFit(process); break;
            case NEXT_FIT:  allocateProcessWithNextFit(process);   break;
        }
        this.printMemory(); // test
    }

    public void desallocateProcess(Process process) {
        for(int i = 0; i < this.memory.size(); i++) {
            Node node = this.memory.get(i);
            if(node.process == process) {
                this.memory.get(i).free();
                if(i > 0 && this.memory.get(i-1).free) {
                    this.memory.get(i).size += this.memory.get(i-1).size;
                    this.memory.remove(--i);
                }
                if(i < this.memory.size() - 1 && this.memory.get(i+1).free) {
                    this.memory.get(i).size += this.memory.get(i+1).size;
                    this.memory.remove(i+1);
                }
                this.printMemory(); // test
                return;
            }
        }
    }

    private void allocateProcessWithFirstFit(Process process) {

        for(int i = 0; i < this.memory.size(); i++) {
            Node actual = this.memory.get(i);
            if(actual.free && actual.size >= process.getMemory()) {
                this.memory.get(i).size -= process.getMemory();
                this.memory.add(i, new Node(process));
                return;
            }
        }
    }

    private void allocateProcessWithBestFit(Process process) {

    }

    private void allocateProcessWithWorsttFit(Process process) {

    }

    private void allocateProcessWithNextFit(Process process) {

    }

    private class Node {

        boolean free;
        int size;
        Process process;

        // Constructor to create an occupied node
        private Node(Process process) {
            this.free = false;
            this.size = process.getMemory();
            this.process = process;
        }

        // Constructor to create a free node
        private Node(int space) {
            this.free = true;
            this.size = space;
            this.process = null;
        }

        private void free() {
            this.free = true;
            this.size = this.process.getMemory();
            this.process = null;
        }
    }
}
