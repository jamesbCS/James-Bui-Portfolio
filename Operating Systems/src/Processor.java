package OSSchedulingProject.src;

import java.util.LinkedList;
import java.util.Queue;

public class Processor {
    private int numOfCores;
    private double speed[];
    private int memRequirements[];
    private Process processes[];
    private Queue<Process> waitQueue = new LinkedList<Process>();

    public Processor() {

    }

    public Processor(int numOfCores, double speed[]) {
        setNumOfCores(numOfCores);
        this.speed = speed;
    }

    // If there are memory requirements for the processor, then the number of cores is constant (project specific question).
    public Processor(int numOfCores, double speed[], int memRequirements[]) {
        this.numOfCores = numOfCores;
        this.speed = speed;
        this.memRequirements = memRequirements;
        this.processes = new Process[numOfCores];
        for (int i = 0; i < numOfCores; i++) {
            processes[i] = new Process();
        }
    }

    public void setNumOfCores(int numOfCores) {
        this.numOfCores = numOfCores;
        processes = new Process[numOfCores];
        for (int i = 0; i < numOfCores; i++) {
            processes[i] = new Process();
        }
    }

    public void setSpeed(double speed[]){
        this.speed = speed;
    }

    public void setMemRequirements(int memRequirements[]) {
        this.memRequirements = memRequirements;
    }

    public void setProcess(Process process, int i) {
        processes[i] = process;
    }
    
    public int getNumOfCores() {
        return numOfCores;
    }

    public double getCoreSpeed(int i) {
        return speed[i];
    }

    public double getMemRequirements(int i) {
        return memRequirements[i];
    }

    public Process getProcess(int i) {
        return processes[i];
    }

    public void addWaitQueue(Process process) {
        waitQueue.add(process);
    }

    public Queue<Process> getWaitQueue() {
        return waitQueue;
    }
}
