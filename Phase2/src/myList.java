/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 *
 *
 * myList a object containing necessary variables for a job entries.
 */

import java.util.Comparator;

public class myList {

    private int jobNum;
    private int classNum;
    private int reqMem;
    private int pTime;
    private int arrivalTime; //set the arrival time

    public myList(int jNum, int cNum, int rMem, int pTime, int aTime) {

        jobNum = jNum;
        classNum = cNum;
        reqMem = rMem;
        this.pTime = pTime;
        arrivalTime = SYSTEM.systemClock;

    }

    //getters and setters
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getpTime() {
        return pTime;
    }
    public int getReqMem() {
        return reqMem;
    }
    public int getClassNum() {
        return classNum;
    }
    public int getJobNum() {
        return jobNum;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void setJobNum(int jobNum) {
        this.jobNum = jobNum;
    }

    public void setpTime(int pTime) {
        this.pTime = pTime;
    }
    public void setReqMem(int reqMem) {
        this.reqMem = reqMem;
    }


    @Override
    public String toString() {
        return ("jobNum: " + jobNum  + " classNum " +classNum  + " requiredMem " +reqMem + " processingTime " +pTime);
    }

}
