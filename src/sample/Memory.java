package sample;

import Enums.AllocationType;

import java.util.ArrayList;

/**
 * Created by Vítor on 17/09/2016.
 *
 * Class that represents the system memory, containing
 * methods to allocate and desallocate processes with
 * four different strategies.
 */
public class Memory {

    private int totalMemory;
    private Process so;
    private ArrayList<Node> memory;

    private int lastPos = 0; // used in next-fit allocation method

    public Memory(int totalMemory, Process so) {
        this.totalMemory = totalMemory;
        this.so = so;
        this.memory = new ArrayList<>();
        this.memory.add(new Node(this.totalMemory - this.so.getMemory()));
        this.memory.add(new Node(this.so));
    }

    public int allocateProcess(Process process, AllocationType type) {
        switch(type) {
            case FIRST_FIT: return allocateProcessWithFirstFit(process);
            case BEST_FIT:  return allocateProcessWithBestFit(process);
            case WORST_FIT: return allocateProcessWithWorstFit(process);
            case NEXT_FIT:  return allocateProcessWithNextFit(process);
        }
        return -1;
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

                System.out.println("\n------------------------------------------------------");
                System.out.printf("Process %d desallocation\n", process.getId());
                //this.printMemory();

                return;
            }
        }
    }

    private int allocateProcessWithFirstFit(Process process) {
        int pos = 0;
        for(int i = 0; i < this.memory.size(); i++) {
            Node actual = this.memory.get(i);
            if(actual.free && actual.size >= process.getMemory()) {
                this.memory.get(i).size -= process.getMemory();
                if(this.memory.get(i).size == 0) this.memory.remove(i--);
                this.memory.add(i, new Node(process));
                return pos;
            }
            pos += this.memory.get(i).size;
        }
        return -1;
    }

    private int allocateProcessWithBestFit(Process process) {
        int pos = 0;
        int minPosition = -1, minSize = Integer.MAX_VALUE, minPos = -1;
        for(int i = 0; i < this.memory.size(); pos += this.memory.get(i).size, i++) {
            Node actual = this.memory.get(i);
            if (actual.free && actual.size >= process.getMemory() && actual.size < minSize) {
                minPosition = i;
                minSize = this.memory.get(i).size;
                minPos = pos;
            }
        }
        if(minPosition != -1) {
            this.memory.get(minPosition).size -= process.getMemory();
            this.memory.add(minPosition, new Node(process));
            System.out.println(minPos);
            return minPos;
        }
        return -1;
    }

    private int allocateProcessWithWorstFit(Process process) {

        int maxPosition = -1, maxSize = -1, maxPos = -1;
        for(int i = 0, pos = 0; i < this.memory.size(); pos += this.memory.get(i).size, i++) {
            Node actual = this.memory.get(i);
            if (actual.free && actual.size > maxSize) {
                maxPosition = i;
                maxSize = actual.size;
                maxPos = pos;
            }
        }
        if(maxPosition != -1 && maxSize >= process.getMemory()) {
            this.memory.get(maxPosition).size -= process.getMemory();
            this.memory.add(maxPosition, new Node(process));
            return maxPos;
        }
        return -1;
    }
/*
    private void printMemory() {
        for(Node node : this.memory) {
            System.out.printf("[%s - %d] ",(node.free ? "free" : "ocpd"), node.size);
        }
        System.out.println();
    }
*/
    private int allocateProcessWithNextFit(Process process) {
        int pos = 0, ind = 0;
        /*
        System.out.println("\n------------------------------------------------------");
        System.out.printf("Process %d allocation\n", process.getId());
        this.printMemory();
        System.out.printf("Lastpos: %d ", lastPos);
        */
        // move to first node after the last position
        for (int i = 0; pos < lastPos; i++) {
            pos += memory.get(i).size;
            ind = i+1;
        }

        // try to allocate in the first gap after the last position
        for(int i = ind; i < memory.size(); i++) {
            Node actual = this.memory.get(i);
            if(actual.free && actual.size >= process.getMemory()) {
                this.memory.get(i).size -= process.getMemory();
                if(this.memory.get(i).size == 0) this.memory.remove(i--);
                this.memory.add(i, new Node(process));
                lastPos = pos;
                return pos;
            }
            pos += actual.size;
        }

        // try to allocate in the first gap before the last position
        pos = 0;
        for(int i = 0; i < ind; i++) {
            Node actual = this.memory.get(i);
            if(actual.free && actual.size >= process.getMemory()) {
                this.memory.get(i).size -= process.getMemory();
                if(this.memory.get(i).size == 0) this.memory.remove(i--);
                this.memory.add(i, new Node(process));
                lastPos = pos;
                return pos;
            }
            pos += actual.size;
        }
        return -1;
    }

    public int getTotalMemory() {
        return this.totalMemory;
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

        // Removes the process from a node, becoming a gap
        private void free() {
            this.free = true;
            this.size = this.process.getMemory();
            this.process = null;
        }
    }
}
