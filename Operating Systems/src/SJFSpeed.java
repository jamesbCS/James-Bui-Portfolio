package OSSchedulingProject.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class SJFSpeed {
    private PriorityQueue<Process> processes = new PriorityQueue<Process>(250);
    private LinkedList<Process> finishedProcesses = new LinkedList<Process>();
    private Processor processor;
    private int numOfCores;
    private int freeCores;

    public SJFSpeed(String processFileString, Processor processor) {
        this.processor = processor;
        numOfCores = processor.getNumOfCores();
        freeCores = numOfCores;
        try {
            processes = readProcessesSJF(processFileString);
            runSJF(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void runSJF() 
    {
    	double time = 0.0;
    	
    	while (true)
    	{
    		loadProcessesSJF(time); // Loads inactive cores in processor
    		
    		int nextFree = findNextExitSJF(); // Find process with shortest remaining runTime 
    		time = time + incrementTime(nextFree); //Increment time both globally and for all active processes
    		
    		removeProcesses(time); //Remove all finished process and mark turn-around times
    		
    		 // Break if all cores are free and there are no processes waiting to run
            if ((freeCores == numOfCores) && (processes.size() == 0)){
                break;
            }
    	}
    }

    private static PriorityQueue<Process> readProcessesSJF(String processFileString) throws IOException {
        PriorityQueue<Process> processes = new PriorityQueue<Process>();
        try {
            File file = new File(processFileString);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            String[] tempArr; 
            int count = 0;

            while ((line = bufferedReader.readLine()) != null ) {
                if (count++ != 0) {
                    tempArr = line.split(",");
                    int pid = Integer.parseInt(tempArr[0]);
                    int cycles = Integer.parseInt(tempArr[1]);
                    int memory = Integer.parseInt(tempArr[2]);
                    processes.add(new Process(pid, cycles, memory));
                }
            }
            bufferedReader.close();
            return processes;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private void loadProcessesSJF(double time) 
    {
    	for (int i = 0; i < numOfCores; i++) 
    	{
    		//Loads a process if the core is empty and there are processes waiting
    		if ((processor.getProcess(i).getPid() == -1) && (processes.size() > 0))
    		{
    			processor.setProcess(processes.poll(), i); //Removes process from the Priority Queue
    			processor.getProcess(i).setWaitTime(time); //Store the wait time
    			processor.getProcess(i).generateRunTime(processor.getCoreSpeed(i)); //Calculate runTime given the processor speed
    			freeCores--;
    		}
    	}
    }
    
    public void removeProcesses(double time) {
        for(int i = 0; i < numOfCores; i++) {
            // Remove a process if it is valid has a remaining runTime of 0
            if ((processor.getProcess(i).getPid() != -1) && (processor.getProcess(i).getRunTime() == 0)) {
                processor.getProcess(i).setTurnAroundTime(time); // Store the processes turnaround time
                finishedProcesses.add(processor.getProcess(i)); // Add processor to finishedProcesses list
                processor.setProcess(new Process(), i); // Fill core with default process
                freeCores++;
            }
        }
    }
    
 // The return is used to increment the time value of the algorithm
    private double incrementTime(int nextFree) {
        double time = processor.getProcess(nextFree).getRunTime(); // Store increment value BEFORE modifying process values
        for (int i = 0; i < numOfCores; i++) {
            if (processor.getProcess(i).getPid() != -1) {
                processor.getProcess(i).decRunTime(time); // Subtract elapsed time from remaining times for active processes
            }
        }
        return time;
    }
    
    private int findNextExitSJF() {
        double minVal = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < numOfCores; i++) {
            if (processor.getProcess(i).getPid() != -1){
                if (processor.getProcess(i).getRunTime() < minVal) {
                    minVal = processor.getProcess(i).getRunTime();
                    minIndex = i;
                } 
            } 
        }
        return minIndex;
    }

    public PriorityQueue<Process> getProcesses() {
        return processes;
    }

    public LinkedList<Process> getFinishedProcesses() {
        return finishedProcesses;
    }
    
}



// Edited by James Bui