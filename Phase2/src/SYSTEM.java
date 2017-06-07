/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 * e. Global Variables:
 *
 *      readyQueue: is a PCB method and it consists of all the information about the jobs.
 *      mySched : to utilize methodes in J_SCHED
 *      systemClock: Timer for this simulation
 *
 * f.
 *      The System reads all jobs from the file and then take all the jobs to J_SCHED.java which
 *      maintains the "sub-Queues" which is arrayLists of pcb using the dispatchJobsInSubQueuesBasedOnPriorities() method.
 *      Then at final shutdown displays the statistics.
 *
 *  SYSTEM.java is my simulator's main class.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class SYSTEM {

    public static int systemClock = 0;

    public static J_SCHED mySched;


    public static void main(String args[]) throws IOException
    {


        BufferedReader br = new BufferedReader(new FileReader("/Users/thotasairam/Documents/Fall 2016/CS 4323 - Operating System/Phase1/src/input.txt"));

        String input;

        //Parsing the inputLine to four integers
        while ((input = br.readLine()) != null)
        {

            String in = input.trim().replaceAll("\\s+", " ");

            String[] temp = in.split("\\s");

            int jobNum = Integer.parseInt(temp[0]);
            int classNum = Integer.parseInt(temp[1]);
            int reqMem = Integer.parseInt(temp[2]);
            int pTime = Integer.parseInt(temp[3]);

            mySched = new J_SCHED(new myList(jobNum,classNum,reqMem,pTime,systemClock));

            systemClock++;

        } //end of while loop


        //ShutDown Conditions
        while(mySched.jobList.size()!=0)
        {
            mySched.callMemManager(mySched.jobList);
        }
        while(mySched.diskList.size()!=0)
        {
            mySched.clearAllFromList(mySched.diskList);

        }

        //Stat Final print output
        double totalAverageInternalFragmentation = MEM_MANAGER.totalInternalFragmentation/ (J_TERM.jobsProcessed);
        double totalAverageExternalFragmentation = MEM_MANAGER.totalExternalFragmentation/(J_TERM.jobsProcessed);

        System.out.print("\n");
        System.out.println("*********************************************************************" +
                                 "***********************************************************************");
        System.out.println("                                                                Statistics");
        System.out.println("**************************************************************" +
                                "******************************************************************************");
        System.out.printf("Total jobs printed out: %116d%n", J_TERM.jobsProcessed);
        System.out.printf("CPU bound jobs processed: %114d%n",J_TERM.cpuBoundCount);
        System.out.printf("Balanced  jobs processed: %114d%n",J_TERM.balancedCount);
        System.out.printf("I/O bound jobs processed: %114d%n",J_TERM.ioBoundCount);
        System.out.printf("Number of jobs rejected because of requested memory: %87d%n", mySched.invJobCount);
        System.out.printf("Average Turnaround Time: %110.2f(VTU)%n", (J_TERM.totalTurnAroundTime/J_TERM.jobsProcessed));
        System.out.printf("Average Waiting Time: %113.2f(VTU)%n",(J_TERM.totalWaitTime/J_TERM.jobsProcessed));
        System.out.printf("Total time for processing the jobs: %99d(VTU)%n", J_TERM.totalProcessedTime);
        System.out.printf("Total Average External Fragmentation: %102.2f%n", totalAverageExternalFragmentation);
        System.out.printf("Total Average Internal Fragmentation: %102.2f%n", totalAverageInternalFragmentation);

    }

}
