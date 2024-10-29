package OSSchedulingProject.src;

public class Process implements Comparable<Process>{
    private int pid = -1;
    private int cycles = -1;
    private int memory = -1;
    private double waitTime = -1;
    private double turnAroundTime = -1;
    private double runTime;

    // Constructors
    public Process() {

    }

    public Process(int pid, int cycles, int memory) {
        this.pid = pid;
        this.cycles = cycles;
        this.memory = memory;
    }

    // Setter Methods
    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public void setTurnAroundTime(double turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void generateRunTime(double processorSpeed) {
        // When the process is loaded into the processor, calculate the runtime for that procesor's speed
        // time (seconds) = cycles / processorSpeed (cycles per second)
        runTime = (double)cycles *
         (double)processorSpeed;
    }

    public void setRunTime(int quantum, double processorSpeed) {
        if (quantum > cycles) {
            runTime = cycles / processorSpeed;
        } else {
            runTime = quantum / processorSpeed;
        }        
    }

    // Getter Methods
    public int getPid(){
        return pid;
    }

    public int getCycles(){
        return cycles;
    }

    public int getMemory(){
        return memory;
    }

    public double getWaitTime(){
        return waitTime;
    }

    public double getTurnAroundTime(){
        return turnAroundTime;
    }

    public double getRunTime(){
        return runTime;
    }

    public void decRunTime(double time){
        runTime = runTime - time;
    }

    public void decCycles(double time, double processorSpeed) {
        cycles = cycles - (int)(time * processorSpeed);
    }

    @Override
    public int compareTo(Process p) {
        if (cycles < p.getCycles()) {
            return -1;
        } else if (cycles > p.getCycles()) {
            return 1;
        } else {
            return 0;
        }
    }
}