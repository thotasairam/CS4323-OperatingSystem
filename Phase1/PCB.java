/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase One
 * d. 9/11/2016
 * e. No global variables. But, can be accessed through getters and setters.
 * f.
 *  PCB.java:
 *
 *  I have used this class to have an ArrayList of informaiton about the jobList. This
 *  is very easy in java, and so I choose to implement the address book i.e, PCB in this way. This is used only
 *  for by the readyQueue.
 *
 * g.
 *
 *  This class will return all the job values which are required in different classes. And this is made more easy than
 *  using an ArrayList inside ArrayList.
 *
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;

public class PCB {

    private int jobNum;
    private int classNum;
    private int reqMem;
    private int pTime;
    private int arrivalTime; //set the arrival time
    private int loadTime;
    private int termTime; //TerminationTime
    private int turnaroundTime;
    private int waitTime;
    private int memIndex;

    public PCB(myList jList)
    {
        jobNum = jList.getJobNum();
        classNum = jList.getClassNum();
        reqMem = jList.getReqMem();
        pTime = jList.getpTime();
        arrivalTime = jList.getArrivalTime();
        loadTime = SYSTEM.systemClock++;
        termTime = 0 ;
        turnaroundTime = 0;
        waitTime =0;

        if(this.jobNum != 0) {
            memIndex = MEM_MANAGER.memIndex.get(jList.getJobNum())-1;
        }
        //add rest all to zero
    }

    //All getters and setters of the private variable
    public void setMemIndex(int memIndex){this.memIndex= memIndex;}
    public int getMemIndex(){return memIndex;}
    public int getJobNum() {
        return jobNum;
    }
    public int getClassNum() {
        return classNum;
    }
    public int getReqMem() {
        return reqMem;
    }
    public int getpTime() {
        return pTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setJobNum(int jobNum) {
        this.jobNum = jobNum;
    }
    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }
    public void setReqMem(int reqMem) {
        this.reqMem = reqMem;
    }
    public void setpTime(int pTime) {
        this.pTime = pTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getLoadTime() {
        return loadTime;
    }
    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }
    public int getTermTime() {
        return termTime;
    }
    public void setTermTime(int termTime) {
        this.termTime = termTime;
    }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
    public int getWaitTime() {
        return waitTime;
    }
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }


    @Override
    public String toString() {
        return (jobNum  + " " +classNum  + " " +reqMem + " " +pTime);
    }

    public static Comparator<PCB> myPCBPTimeComparator = new Comparator<PCB>() {

        public int compare(PCB m1, PCB m2) {

            return Integer.compare(m1.getpTime(), m2.getpTime()); // sorting processing Time in ascending sort
        }};

}
