/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 * e. Global Variables:
 *
 *      jobsProcessed = number of jobs processed and terminated
 *      ioBoundCount = number of  ioJobs processed
 *      cpuBoundCount = number of cpuJobs processed
 *      balancedCount = number of balancedJobs processed
 *      totalTurnAroundTime = totalTurnAroundTime for the final Statistics
 *      totalTurnWaitTime = totalWaitTime for the final Statistics
 *      jobCount = it's used to output external and internal fragmentation for each 15 jobs
 *
 * J_TERM.java terminates the job and outputs the job values after terminating it.
 */

public class J_TERM {

    /*
    * my PCB is a Information about the job list
    *
    * The idea is to delete and release the memory and print the output
    */
    public static int jobsProcessed = 0;
    public static int ioBoundCount =0, cpuBoundCount =0,balancedCount =0, totalProcessedTime;
    public static double totalTurnAroundTime, totalWaitTime;
    public static int jobCount = 0;

    public J_TERM(PCB job)
    {

        jobsProcessed++;

        if(job.getClassNum()==1)
        {
            cpuBoundCount++;
        }
        else if(job.getClassNum()==2)
        {
            balancedCount++;
        }
        else
        {
            ioBoundCount++;
        }

        //Job's Termination Time
        job.setTermTime(SYSTEM.systemClock);

        //Job's TurnAroundTime
        job.setTurnaroundTime(job.getTermTime()-job.getArrivalTime());

        //processedTime = taken to process and terminate the job
        job.setProcessedTime(job.getTermTime()-job.getLoadTime());

        //Job's wait time
        job.setWaitTime(job.getTurnaroundTime()-job.getProcessedTime());

        totalProcessedTime = job.getTermTime();
        totalTurnAroundTime = totalTurnAroundTime + job.getTurnaroundTime();
        totalWaitTime = totalWaitTime + job.getWaitTime();

        removeFromQueue(job);

        MEM_MANAGER.stats();

        MEM_MANAGER.memRelease(job.getReqMem(), MEM_MANAGER.memIndex.get(job.getJobNum()));

        jobCount++;

        //output External and Average Internal Fragmentation for every 15 jobs terminated.
        if(jobCount == 15)
        {
            if (J_TERM.jobsProcessed <=15)
            {
                System.out.println("****************************************************************" +
                        "*****************************************************************************");
                System.out.printf("%-10s %-10s %-15s %-15s %-15s %-20s %-15s %-15s %-10s %n",
                        "Job ID", "Job Class", "Arrival(VTU)", "Loaded(VTU)",
                        "Terminate(VTU)", "Turn Around(VTU)", "Processing(VTU)",
                        "Waiting(VTU)", "TrafficCount");
                System.out.println("***************************************************************************" +
                        "******************************************************************");
            }

            System.out.printf("%-10d %-10d %-15d %-15d %-15d %-20d %-15d %-15d %10d  %n", job.getJobNum(), job.getClassNum(),
                    job.getArrivalTime(), job.getLoadTime(), job.getTermTime(), job.getTurnaroundTime(),
                    job.getProcessedTime(), job.getWaitTime(), job.getTrafficCount());

            jobCount = 0;
        }

    }

    //checks the jobs in sub-queues and removes it.
    public  void removeFromQueue(PCB job)
    {
        if(J_SCHED.ioBoundQueue.remove(job))
        {
        }
        else if(J_SCHED.cpuBoundQueue.remove(job))
        {

        }
        else if(J_SCHED.balancedQueue.remove(job))
        {
        }
        else
        {
            System.out.println("Error! in remove from Queue, job: " + job);
        }

    }

}