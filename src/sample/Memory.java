package sample;

import java.util.ArrayList;

/**
 * Created by Vítor on 17/09/2016.
 */
public class Memory {

    private int totalMemory;
    private int soMemory;
    private ArrayList<Node> memory;

    public Memory() {

    }

    public void allocateProcess(Process process) {

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
        public Node(int space, Process process) {
            this.free = false;
            this.space = space;
            this.process = process;
        }
        
        // Constructor to create a free node
        public Node(int space) {
            this.free = true;
            this.space = space;
            this.process = null;
        }
    }
}
