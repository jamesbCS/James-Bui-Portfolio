package OSSchedulingProject.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class SJF {
    private PriorityQueue<Process> processes = new PriorityQueue<Process>(250);
    private LinkedList<Process> finishedProcesses = new LinkedList<Process>();
    private Process[] processor;
    private int numOfCores;
    private int freeCores;

    public SJF(String processFileString, Process[] processor, double processorSpeed) {
        this.processor = processor;
        numOfCores = processor.length;
        freeCores = numOfCores;
        try {
            processes = readProcessesSJF(processFileString);
            runSJF(processorSpeed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void runSJF(double processorSpeed) 
    {
    	double time = 0.0;
    	
    	while (true)
    	{
    		loadProcessesSJF(time, processorSpeed); // Loads inactive cores in processor
    		
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
    
    private void loadProcessesSJF(double time, double processorSpeed) 
    {
    	for (int i = 0; i < numOfCores; i++) 
    	{
    		//Loads a process if the core is empty and there are processes waiting
    		if ((processor[i].getPid() == -1) && (processes.size() > 0))
    		{
    			processor[i] = processes.poll(); //Removes process from the Priority Queue
    			processor[i].setWaitTime(time); //Store the wait time
    			processor[i].generateRunTime(processorSpeed); //Calculate runTime given the processor speed
    			freeCores--;
    		}
    	}
    }
    
    public void removeProcesses(double time) {
        for(int i = 0; i < numOfCores; i++) {
            // Remove a process if it is valid has a remaining runTime of 0
            if ((processor[i].getPid() != -1) && (processor[i].getRunTime() == 0)) {
                processor[i].setTurnAroundTime(time); // Store the processes turnaround time
                finishedProcesses.add(processor[i]); // Add processor to finishedProcesses list
                processor[i] = new Process(); // Fill core with default process
                freeCores++;
            }
        }
    }
    
 // The return is used to increment the time value of the algorithm
    private double incrementTime(int nextFree) {
        double time = processor[nextFree].getRunTime(); // Store increment value BEFORE modifying process values
        for (int i = 0; i < numOfCores; i++) {
            if (processor[i].getPid() != -1) {
                processor[i].decRunTime(time); // Subtract elapsed time from remaining times for active processes
            }
        }
        return time;
    }
    
    private int findNextExitSJF() {
        double minVal = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < numOfCores; i++) {
            if (processor[i].getPid() != -1){
                if (processor[i].getRunTime() < minVal) {
                    minVal = processor[i].getRunTime();
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