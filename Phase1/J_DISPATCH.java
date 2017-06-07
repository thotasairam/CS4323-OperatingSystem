/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase One
 * d. 9/11/2016
 * e. I have used this "tTime", for keeping tracj of termination time.
 *
 *     stat is an arralyList of all the jobs that have encountered. It is printed at the end.
 *
 * Dispatch just dispatches all the jobs in PCB in FCFS order and terminates the next.
 */

import java.util.ArrayList;


public class J_DISPATCH {

    public static int tTime = 0 ;

    public static ArrayList<PCB> stat = new ArrayList<PCB>();


    public static void read(PCB job)
    {
        stat.add(job);
        new CPU(job.getJobNum(), job.getpTime());

    }

}
