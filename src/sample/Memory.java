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

    private int lastPosition; // used in next-fit allocation method

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

    public boolean allocateProcess(Process process, AllocationType type) {
        switch(type) {
            case FIRST_FIT: return allocateProcessWithFirstFit(process);
            case BEST_FIT:  return allocateProcessWithBestFit(process);
            case WORST_FIT: return allocateProcessWithWorstFit(process);
            case NEXT_FIT:  return allocateProcessWithNextFit(process);
        }
        return false;
    }

    //TODO: Update lastPosition after removing nodes
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

    private boolean allocateProcessWithFirstFit(Process process) {

        for(int i = 0; i < this.memory.size(); i++) {
            Node actual = this.memory.get(i);
            if(actual.free && actual.size >= process.getMemory()) {
                this.memory.get(i).size -= process.getMemory();
                this.memory.add(i, new Node(process));
                if(i < lastPosition) lastPosition++;
                return true;
            }
        }
        return false;
    }

    private boolean allocateProcessWithBestFit(Process process) {

        int minPosition = -1, minSize = Integer.MAX_VALUE;
        for(int i = 0; i < this.memory.size(); i++) {
            Node actual = this.memory.get(i);
            if (actual.free && actual.size >= process.getMemory() && actual.size < minSize) {
                minPosition = i;
                minSize = this.memory.get(i).size;
            }
        }
        if(minPosition != -1) {
            this.memory.get(minPosition).size -= process.getMemory();
            this.memory.add(minPosition, new Node(process));
            if(minPosition < lastPosition) lastPosition++;
            return true;
        }
        return false;
    }

    private boolean allocateProcessWithWorstFit(Process process) {

        int maxPosition = -1, maxSize = -1;
        for(int i = 0; i < this.memory.size(); i++) {
            Node actual = this.memory.get(i);
            if (actual.free && actual.size > maxSize) {
                maxPosition = i;
                maxSize = this.memory.get(i).size;
            }
        }
        if(maxPosition != -1 && maxSize >= process.getMemory()) {
            this.memory.get(maxPosition).size -= process.getMemory();
            this.memory.add(maxPosition, new Node(process));
            if(maxPosition < lastPosition) lastPosition++;
            return true;
        }
        return false;
    }

    //TODO: Check functionality of the next-fit method
    private boolean allocateProcessWithNextFit(Process process) {

        for(int i = lastPosition + 1; i != lastPosition; i = (i+1) % this.memory.size()) {
            Node actual = this.memory.get(i);
            if(actual.free && actual.size >= process.getMemory()) {
                this.memory.get(i).size -= process.getMemory();
                this.memory.add(i, new Node(process));
                if(i < lastPosition) lastPosition++;
                return true;
            }
        }
        return false;
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
