/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase One
 * d. 9/11/2016
 * e.
 *      jobList: is the list of jobs that are always sen to jobMix() for scheduling algorithm
 *      diskList: it is used when the memory is not avaiable. this list is again send to jobMix()
 *      myMem: this is used to make us of the methods in MEM_MANAGER.java
 *
 * f.J_SCHED is my job scheduler. It checks if memory is avaiable and send the balanced jobmix to the PCB,
 *  Dispatches and Termintates the job.
 *
 *  g. This is most complicated class in my all classes. And this does implement, a partial OS.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


 /*
  *
  * J_SCHED will get a job and it needs to decide to send to
  *   sorts and manipulates jobList and diskList
  *   updates PCB
  *
  */

public class J_SCHED {

    //Two global variables
    public static ArrayList<myList> jobList = new ArrayList<myList>();
    public static ArrayList<myList> diskList = new ArrayList<myList>();
    public static MEM_MANAGER myMem = new MEM_MANAGER();

    public static int zeroCount = 0;
    public static int invNumCount = 0;

    //This is a constructor which basically used only to schedule purpose

    public J_SCHED(myList job)
    {
        //adding jobList or sending for scheduling algorithm until
        if( (jobList.size() <= 50) && (job.getJobNum() != 0) && (job.getReqMem()<=128))
        {
            if(jobList.size() == 50)
            {
                jobMix();
            }

            jobList.add(job);
        }
        else
        {
            if(job.getJobNum() == 0 ) //zero encountered DO THE PROCESS HERE
            {
                jobList.add(job);
                jobMix();
            }
            else if(job.getReqMem() > 128) //More that 128
            {
                invNumCount++;
            }
            else
            {
                System.out.println("Error! job Num:" + job.getJobNum() + " "+ jobList.size());
            }
        }
    }

    //checks for the memory, sends for dispatch and terminates the job
    //This is my scheduling algorithm and I have used SJF by using Java.Collections and
    //Java.Comparator
    public void  jobMix()
    {

        //SORTING ALGORITHM for a balanced jobMix using comparable
        Collections.sort(jobList, myList.myListPTimeComparator);

        callMemManager(jobList);

    }


    //Calls memory manager
    public void callMemManager(ArrayList<myList> jobList) {

        addAllJobsToReadyQueue(jobList);


        Collections.sort(SYSTEM.readyQueue, PCB.myPCBPTimeComparator);

        //Conditions to run the process
        if ((SYSTEM.readyQueue.get(0).getJobNum() == 0)) {
            while (SYSTEM.readyQueue.size() > 0) {
                J_DISPATCH.read(SYSTEM.readyQueue.get(0));
                new J_TERM(SYSTEM.readyQueue.get(0));

            }
            while(jobList.size()!=0)
            {
                jobMix();
            }

            if(SYSTEM.readyQueue.size() > 0)
            {
                J_TERM.terminateAll(SYSTEM.readyQueue);

            }
            else if(diskList.size() == 300) //check at shut down if (disklist.size>0), then cleardisklist
            {
                clearDiskList();
            }
            else if (SYSTEM.readyQueue.size() == 0 && diskList.size() > 0)
            {
                clearDiskList();
            }
            else
            {
                System.out.println("Error! sizes of readyQueue: " +
                        SYSTEM.readyQueue.size() + " disk: " + diskList.size());
            }

        }

    }

    //This is the method checking the memoryAvailable
    public static void addAllJobsToReadyQueue(ArrayList<myList> jobList)
    {
        for (int i = 0; i < jobList.size(); i++) {
            i = 0;
            boolean memAvailable = myMem.checkMem(jobList.get(i).getJobNum(), jobList.get(i).getReqMem());


            if (memAvailable) {

                SYSTEM.readyQueue.add(new PCB(jobList.get(i)));//going to PCB
                jobList.remove(i);
            }

        }
    }

    //clear the diskList and do job mix
    public void clearDiskList()
    {
        while(diskList.size()>0)
        {
            jobList.add(diskList.get(0));

            diskList.remove(0);

        }

        Collections.sort(jobList, myList.myListPTimeComparator);

        //remove from jobList and add to the readyQueue
        addAllJobsToReadyQueue(jobList);

        J_TERM.terminateAll(SYSTEM.readyQueue);

    }


}
