/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase One
 * d. 9/11/2016
 *
 *
 * myList a object containing necessary variables for a job entries.
 */

import java.util.ArrayList;
import java.util.HashMap;
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

    public static Comparator<myList> myListPTimeComparator = new Comparator<myList>() {

        public int compare(myList m1, myList m2) {

            return Integer.compare(m1.getpTime(), m2.getpTime()); // sorting processing Time in ascending sort
        }};

    @Override
    public String toString() {
        return ("jobNum: " + jobNum  + " classNum " +classNum  + " requiredMem " +reqMem + " processingTime " +pTime);
    }

}
