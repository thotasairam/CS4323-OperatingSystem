/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 *
 * e. Global Variables:
 *    p0List: This is my arrayList of PCBs with priority 0
 *    p1List: This is my arrayList of PCBs with priority 1
 *    p5List: This is my arrayList of PCBs with priority 5
 *
 * f. My J_DISPATCH is the one which is implementing Round Robin process scheduling.
 *    As mentioned in the program, process scheduling is implemented using multi-level feedback queue based on
 *    priority. J_DISPATCH is the class which send the jobs to cpu to and after each job is dispatched to cpu,
 *    it updates the priorities of the jobs and sub-queues when necessary.
 *
 *
 */

import java.util.ArrayList;

public class J_DISPATCH {

    public static ArrayList<PCB> p0List = new ArrayList<PCB>();
    public static ArrayList<PCB> p1List = new ArrayList<PCB>();
    public static ArrayList<PCB> p5List = new ArrayList<PCB>();

    public J_DISPATCH(PCB job)
    {
        //Initially, the jobs are distributed to the priority arrayLists based on job class number.
        switch(job.getClassNum())
        {
            case 1: p0List.add(job); break;
            case 2: p1List.add(job); break;
            case 3: p5List.add(job); break;
            default: System.out.print("Error! PCB: " + job);
        }
    }

    //Runs through all the sub-queues and dispatches until they all are length of zero.
    public static void dispatchJobsInSubQueuesBasedOnPriorities()
    {
        //Runs through all the priority 5 jobs until it goes to zero.
        while(p5List.size()!=0){

            for(int i=0; i<p5List.size(); i++)
            {
                //read function sends the job to CPU
                read(p5List.get(i));

                //After the job is sent to CPU if the pTime is less than QuantumTime of the job, it will be terminated
                if(p5List.get(i).getpTime()<=p5List.get(i).getQuantumNum())
                {
                    new J_TERM(p5List.get(i));
                    p5List.remove(i);
                }
                else
                {
                    //checks to decrease a job or not
                    canDecrementPriority(p5List.get(i));
                }
            }
        }

        while(p1List.size()!=0)
        {
            for(int i=0; i < p1List.size(); i++)
            {
                read(p1List.get(i));

                //if a job is incremented then start from the highest priority so "return"/"break"
                if(checkAllJobsInCPUAndBalanced())
                {
                    return;
                }

                if(p1List.get(i).getpTime() <= p1List.get(i).getQuantumNum())
                {
                    new J_TERM(p1List.get(i));
                    p1List.remove(i);
                }
                else if(canDecrementPriority(p1List.get(i)))
                {
                    decrementPriority(p1List.get(i));
                }


            }
        }

        //this loop is similar to p1
        while(p0List.size()!=0)
        {
            for (int i = 0; i < p0List.size(); i++)
            {
                read(p0List.get(i));

                if(checkAllJobsInCPUAndBalanced())
                {
                    return;
                }

                if(p0List.get(i).getpTime() <= p0List.get(i).getQuantumNum())
                {
                    new J_TERM(p0List.get(i));
                    p0List.remove(i);
                }
                else if(canDecrementPriority(p0List.get(i)))
                {
                    decrementPriority(p0List.get(i));
                }
            }
        }

    }

    //This method checks all the CPU and Balanced jobs and tries to increment it's priority.
    //It returns a true value if the job's priority is increased.
    public static boolean checkAllJobsInCPUAndBalanced()
    {

        for(int i=0; i < J_SCHED.balancedQueue.size(); i++)
        {
            if(canIncrementPriority(J_SCHED.balancedQueue.get(i)))
            {
                incrementPriority(J_SCHED.balancedQueue.get(i));
                return true;
            }
        }

        for(int i=0; i < J_SCHED.cpuBoundQueue.size(); i++)
        {
            if(canIncrementPriority(J_SCHED.cpuBoundQueue.get(i)))
            {
                incrementPriority(J_SCHED.cpuBoundQueue.get(i));
                return true;
            }
        }

        return false;
    }

    //This method checks all restrictions on incrementing the priority.
    //If the priority is increased it returns a true values.
    public static boolean canIncrementPriority(PCB job)
    {
        boolean result = false;

       // int vTime = SYSTEM.systemClock - job.getVirtualTime();

        if(job.getClassNum()==2 && job.getVirtualTime() >=400)
        {
            result = true;
        }
        else if(job.getClassNum()==1 && job.getVirtualTime() >=600)
        {
            result = true;
        }

        return result;
    }

    //This method increases the priorities of the job with only one restriction.
    //The restriction is the the priority has to be less than 5.
    public static void incrementPriority(PCB tempJob){

        if(tempJob.getPriorityNum() == 4)
        {
            tempJob.setPriorityNum(5);

            if(J_SCHED.cpuBoundQueue.remove(tempJob))
            {
                tempJob.increaseTrafficCount();
            }
            else if(J_SCHED.balancedQueue.remove(tempJob))
            {
                tempJob.increaseTrafficCount();
            }

            J_SCHED.ioBoundQueue.add(tempJob);  //changing to IO class

        }
        else if(tempJob.getPriorityNum() < 4)
        {
            tempJob.setPriorityNum(tempJob.getPriorityNum()+1);
        }
    }

    //This methods checks all restriction on decrementing the priority.
    //If the priority is decreased then it returns a true value.
    public static boolean canDecrementPriority(PCB tempJob)
    {
        boolean isDecreased = false;

        if(J_SCHED.ioBoundQueue.contains(tempJob) && tempJob.getClassNum()!= 3)
        {
            decrementPriority(tempJob);
            if(tempJob.getClassNum()==1)
            {
                J_SCHED.ioBoundQueue.remove(tempJob);
                tempJob.increaseTrafficCount();
                J_SCHED.cpuBoundQueue.add(tempJob);

            }
            else if(tempJob.getClassNum()==2)
            {
                J_SCHED.ioBoundQueue.remove(tempJob);
                tempJob.increaseTrafficCount();
                J_SCHED.cpuBoundQueue.add(tempJob);
            }
            isDecreased = true;
        }
        else if(tempJob.getPriorityNum()>1 && tempJob.getClassNum()!=3)
        {
            decrementPriority(tempJob);
            isDecreased = true;
        }


        return isDecreased;
    }

    public static void decrementPriority(PCB tempJob){

        tempJob.setPriorityNum(tempJob.getPriorityNum() - 1);
    }

    public static void read(PCB job)
    {
         new CPU(job);
    }

}
