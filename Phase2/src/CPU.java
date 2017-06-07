/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 * e. No global variables
 *
 * f. This CPU class just processes the job, decrements the processTime based on the jobs QuantumNum, and
 *    updates the System Clock as well.
 *
*/

public class CPU {

    public CPU(PCB job)
    {
        //Subtracting the quantumTime of the job
        // if job's process time is greater than it's quantum time
        if(job.getpTime() >= job.getQuantumNum())
        {
            job.setpTime(job.getpTime()-job.getQuantumNum());
            job.setVirtualTime(job.getVirtualTime()+job.getQuantumNum());

            SYSTEM.systemClock = SYSTEM.systemClock + job.getQuantumNum();
        }
        else if(job.getQuantumNum() > job.getpTime())
        {
            SYSTEM.systemClock = SYSTEM.systemClock + job.getpTime();
        }
        else
        {
            System.out.println("Error! pcb:" + job);
        }

    }
}
