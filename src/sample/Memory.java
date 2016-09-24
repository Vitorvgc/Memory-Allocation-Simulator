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

    private int lastPosition = 0, lastPos = 0; // used in next-fit allocation method

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

    public int allocateProcess(Process process, AllocationType type) {
        switch(type) {
            case FIRST_FIT: return allocateProcessWithFirstFit(process);
            case BEST_FIT:  return allocateProcessWithBestFit(process);
            case WORST_FIT: return allocateProcessWithWorstFit(process);
            case NEXT_FIT:  return allocateProcessWithNextFit(process);
        }
        return -1;
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
                    if(lastPosition > i) lastPosition--;
                }
                if(i < this.memory.size() - 1 && this.memory.get(i+1).free) {
                    this.memory.get(i).size += this.memory.get(i+1).size;
                    this.memory.remove(i+1);
                    if(lastPosition > i + 1) lastPosition--;
                }
                //this.printMemory(); // test
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
            this.printMemory();
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

    //TODO: Check functionality of the next-fit method
    private int allocateProcessWithNextFit(Process process) {
        try {
            int pos = 0, ind = 0;
            for (int i = 0; pos < lastPos; i++) {
                pos += memory.get(i).size;
                ind = i;
            }
            System.out.printf("pos: %d\n", pos);

            int pos2 = 0, anterior = 0;
            for (int i = 0; pos2 + memory.get(i).size < lastPos; i++) {
                pos2 += memory.get(i).size;
                anterior++;
            }

            int gap1 = pos - lastPos;
            int gap2 = lastPos - pos2;


            System.out.printf("Tentando alocar no espaço entre %d e %d\n", pos, this.memory.get(ind).size);

            int index = ind - 1;
            if (index < 0) index = 0;

            // doing
            if (gap1 >= process.getMemory()) {
                memory.get(index).size -= gap2 + process.getMemory();
                memory.add(index, new Node(process));
                memory.add(index, new Node(gap2));
                lastPosition++;
                return lastPos;
            }

            for (int i = lastPosition; i < memory.size(); i++) {
                Node actual = this.memory.get(i);
                if (actual.free && actual.size >= process.getMemory()) {
                    this.memory.get(i).size -= process.getMemory();
                    this.memory.add(i, new Node(process));
                    lastPosition = i;
                    //if(i < lastPosition) lastPosition++;
                    lastPos = pos;
                    this.printMemory();
                    return pos;
                }
                pos += memory.get(i).size;
                //if(i == this.memory.size() - 1) this.lastPos = 0;
            }
            pos = 0;
            for (int i = 0; i < lastPosition; i++) {
                Node actual = this.memory.get(i);
                if (actual.free && actual.size >= process.getMemory()) {
                    this.memory.get(i).size -= process.getMemory();
                    this.memory.add(i, new Node(process));
                    lastPosition = i;
                    //if(i < lastPosition) lastPosition++;
                    lastPos = pos;
                    this.printMemory();
                    return pos;
                }
                pos += memory.get(i).size;
            }
            return -1;
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Fora do intervalo");
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

        private void free() {
            this.free = true;
            this.size = this.process.getMemory();
            this.process = null;
        }
    }
}
