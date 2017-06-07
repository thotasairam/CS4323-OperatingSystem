/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase One
 * d. 9/11/2016
 * e. Global Variables:
 *
 *      readyQueue: is a PCB method and it consists of all the information about the jobs.
 *      mySched : to utilize methodes in J_SCHED
 *      systemClock: Timer for this simulation
 *
 * f.
 *      The System reads all jobs from the file and then take all the jobs to J_SCHED.java which
 *      maintains the "readyQueue" which is the pcb using the jobMix method. Then at final shutdown
 *      displays the statistics.
 *
 * SYSTEM.java is my simulator's main class.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SYSTEM {

    public static int systemClock = 0;

    public static ArrayList<PCB> readyQueue = new ArrayList<PCB>();

    public static J_SCHED mySched;

    public static int invJobsCount = 0;

    public static void main(String args[]) throws IOException
    {

        BufferedReader br = new BufferedReader(new FileReader("/Users/thotasairam/Documents/Fall 2016/CS 4323 - Operating System/Phase1/src/input.txt"));

        Scanner scan = new Scanner(java.lang.System.in);

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


            mySched = new J_SCHED(new myList(jobNum, classNum, reqMem,pTime, systemClock));

            systemClock++;

        } //end of while loop


        //ShutDown Conditions

        while(mySched.diskList.size()>0)
        {
            mySched.clearDiskList();
        }
        while(mySched.jobList.size()>0) {
            mySched.jobMix();

        }
        while(readyQueue.size()>0)
        {
            J_DISPATCH.read(SYSTEM.readyQueue.get(0));
            new J_TERM(SYSTEM.readyQueue.get(0));

        }


        //Stat Final print output
        System.out.println("\n**********************************************************************************************");
        System.out.println("                               Phase One Statistics                                      ");
        System.out.println("**********************************************************************************************");
        System.out.printf("Total jobs printed out: %70d%n", J_TERM.jobsProcessed);
        System.out.printf("CPU bound jobs processed: %68d%n",J_TERM.cpuBoundCount);
        System.out.printf("Balanced  jobs processed: %68d%n",J_TERM.balancedCount);
        System.out.printf("I/O bound jobs processed: %68d%n",J_TERM.ioBoundCount);
        System.out.printf("Average Turnaround Time: %64.2f(VTU)%n", (J_TERM.totalTurnAroundTime/J_TERM.jobsProcessed));
        System.out.printf("Total time for processing the jobs: %53d(VTU)%n", J_TERM.totalProcessedTime);
        System.out.printf("Average Waiting Time: %67.2f(VTU)%n",(J_TERM.totalWaitTime/J_TERM.jobsProcessed));

//        System.out.println("\nTotal jobs:" + J_TERM.jobsProcessed +  "rq : "+
//                readyQueue.size() +"dL: " + J_SCHED.diskList.size() + " jL: " + J_SCHED.jobList.size());
//        System.out.println("Num of zeros: " + mySched.zeroCount + "Num of inv num: " + mySched.invNumCount);

    }

}
