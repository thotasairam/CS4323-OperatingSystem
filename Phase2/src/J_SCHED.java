/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 * e. Global Variables:-
 *
 *      jobList: tempList to read the jobs until zero is encountered or disklist==300.
 *      diskList: used for storing diskList
 *      myMem: this is used to make use of the methods in MEM_MANAGER.java
 *      zeroCount: number of jobs with job num == 0
 *      invJobCount: number of jobs with invalid required memory
 *      ioBoundQueue: ArrayList of PCB with jobClassNum == 3 and Priority == 5
 *      cpuBoundQueue: ArrayList of PCB with jobClassNum == 1
 *      balancedBoundQueue: ArrayList of PCB with jobClassNum == 0
 *
 * f. J_SCHED is my job scheduler. It checks if memory is available and send the job to
 *  dispatcher and terminates the job when necessary.
 *
 */

import java.util.ArrayList;


 /*
  *
  * J_SCHED will get a job and it needs to decide to send to
  *   sorts and manipulates jobList and diskList
  *   updates PCB
  *
  */

public class J_SCHED {

    public static J_DISPATCH myDispatcher;
    public static ArrayList<PCB> ioBoundQueue  = new ArrayList<PCB>();
    public static ArrayList<PCB> cpuBoundQueue = new ArrayList<PCB>();
    public static ArrayList<PCB> balancedQueue = new ArrayList<PCB>();
    public static ArrayList<myList> jobList = new ArrayList<myList>();
    public static ArrayList<myList> diskList = new ArrayList<myList>();
    public static MEM_MANAGER myMem = new MEM_MANAGER();
    public static int zeroCount = 0;
    public static int invJobCount = 0;



    public J_SCHED(myList job)
    {
        if(diskList.size() >= 300)
        {
            while(diskList.size()!=0)
            {
                clearAllFromList(diskList);
            }

        }
        else if(job.getJobNum() != 0 && job.getReqMem() <= 128)
        {
            jobList.add(job);
        }
        else
        {
            if(job.getReqMem() > 128)
            {
                //reject the job and put it outside the queue
                invJobCount++;
            }
            else if(job.getJobNum() == 0)
            {
                //stop and "do something" make sure the program runs again
                callMemManager(jobList);

                while(diskList.size()!=0)
                {
                    clearAllFromList(diskList);

                }

                zeroCount++;

            }

        }
    }

    public static void clearAllFromList(ArrayList<myList> tempList)
    {
        for(int i=0; i<tempList.size();i++)
        {
            addAllJobsFromListToSubQueue(tempList);
        }

        while(ioBoundQueue.size()!=0 || cpuBoundQueue.size()!=0 || balancedQueue.size()!=0)
        {
            J_DISPATCH.dispatchJobsInSubQueuesBasedOnPriorities();
        }
    }

    public void callMemManager(ArrayList<myList> jobList)
    {
        while(jobList.size() > 0)
        {
            addAllJobsFromListToSubQueue(jobList);
            //By now, jobs are distributed in readyQueue and diskList
        }

        while(ioBoundQueue.size()!=0 || cpuBoundQueue.size()!=0 || balancedQueue.size()!=0)
        {
            J_DISPATCH.dispatchJobsInSubQueuesBasedOnPriorities();
        }

    }

    //This is the method checking the memoryAvailable
    //And calls the  myDispatcher if memAvailable boolean is true.
    public static void addAllJobsFromListToSubQueue(ArrayList<myList> jobList)
    {
        for (int i = 0; i < jobList.size(); i++) {

            boolean memAvailable = myMem.checkMem(jobList.get(i).getJobNum(), jobList.get(i).getReqMem());

            if (memAvailable) {

                PCB job = new PCB(jobList.get(i));

                //Based on the jobClassNum the jobs are added to the sub-queues and sent for dispatch
                switch (job.getClassNum())
                {
                    case 1:
                        job.setQuantumNum(75);
                        job.setPriorityNum(0);
                        myDispatcher = new J_DISPATCH(job);
                        job.setLoadTime(SYSTEM.systemClock++);
                        cpuBoundQueue.add(job);
                        break;

                    case 2:
                        job.setQuantumNum(40);
                        job.setPriorityNum(1);
                        myDispatcher = new J_DISPATCH(job);
                        job.setLoadTime(SYSTEM.systemClock++);
                        balancedQueue.add(job);
                        break;

                    case 3:
                        job.setQuantumNum(20);
                        job.setPriorityNum(5);
                        myDispatcher = new J_DISPATCH(job);
                        job.setLoadTime(SYSTEM.systemClock++);
                        ioBoundQueue.add(job);

                        break;

                    default: System.out.println("Error! pcb: " + job);break;

                }
                jobList.remove(i);
            }
            else if(!J_SCHED.diskList.contains(jobList.get(i))) //if memory not available, add to disk list
            {
                J_SCHED.diskList.add(jobList.get(i));
                jobList.remove(i);
            }
        }
    }

}
