/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase One
 * d. 9/11/2016
 *
 *
 *
 * J_TERM.java terminates the job and outputs the job values after terminating it.
 */

import java.util.ArrayList;
import java.util.HashMap;

public class J_TERM {

    /*
     * my PCB is a Information about the job list
     *
     * The idea is to delete and release the memory and print the output
     */

    public static int jobsProcessed = 0;
    public static int ioBoundCount =0, cpuBoundCount =0,balancedCount =0, totalProcessedTime;
    public static double totalTurnAroundTime, totalWaitTime;

    public J_TERM(PCB job)
    {
        if(job.getJobNum()==0)
        {
            J_SCHED.zeroCount++;
        }
        else
        {
            jobsProcessed++;
            if(job.getClassNum()==1)
            {
                ioBoundCount++;
            }
            else if(job.getClassNum()==2)
            {
                balancedCount++;
            }
            else
            {
                cpuBoundCount++;
            }
        }

        job.setTermTime(J_DISPATCH.tTime);
        job.setTurnaroundTime(job.getTermTime()-job.getArrivalTime());
        job.setWaitTime(job.getTurnaroundTime()-job.getpTime());


        totalProcessedTime = job.getTermTime();
        totalTurnAroundTime = totalTurnAroundTime + job.getTurnaroundTime();
        totalWaitTime = totalWaitTime + job.getWaitTime();


        SYSTEM.readyQueue.remove(SYSTEM.readyQueue.indexOf(job));

        MEM_MANAGER.memRelease(job.getReqMem(), MEM_MANAGER.memIndex.get(job.getJobNum()));

        if(job.getClassNum() != 0 )
        {
            if(J_TERM.jobsProcessed==1) {
                System.out.println("****************************************************************" +
                        "*********************************************************");
                System.out.printf("%-10s %-10s %-15s %-15s %-15s %-20s %-15s %-15s %n", "Job ID", "Job Class",
                        "Arrival(VTU)", "Loaded(VTU)", "Terminate(VTU)", "Turn Around(VTU)", "Processing(VTU)",  "Waiting(VTU)");
                System.out.println("********************************************************************" +
                        "*****************************************************");
            }

            System.out.printf("%-10d %-10d %-15d %-15d %-15d %-20d %-15d %-15d %n",job.getJobNum(),job.getClassNum(),
                job.getArrivalTime(), job.getLoadTime(), job.getTermTime(), job.getTurnaroundTime(),
                    job.getpTime(),job.getWaitTime());
        }
    }

    public static void terminateAll(ArrayList<PCB> termList)
    {
        while(termList.size()>0)
        {
            J_DISPATCH.read(termList.get(0));
            new J_TERM(termList.get(0));
        }
    }
}