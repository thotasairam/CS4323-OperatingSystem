/**
 * a. Sai Ram Thota
 * b. CS 4323
 * c. Phase Two
 * d. 11/29/2016
 * e. No global variables. But, can be accessed through getters and setters.
 *
 * f.I have used this class to have an ArrayList of information about the jobList. This
 *  is very easy in java, and so I choose to implement the address book i.e, PCB in this way. This is used only
 *  for by the readyQueue.
 *
 * g.This class will return all the job values which are required in different classes. And this is made more easy than
 *  using an ArrayList inside ArrayList.
 *
 */

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
    private int quantumNum;
    private int virtualTime;
    private int priorityNum;
    private int processedTime;
    private int trafficCount;

    public PCB(myList jList)
    {
        jobNum = jList.getJobNum();
        classNum = jList.getClassNum();
        reqMem = jList.getReqMem();
        pTime = jList.getpTime();
        arrivalTime = jList.getArrivalTime();
        loadTime =0;
        termTime = 0 ;
        turnaroundTime = 0;
        waitTime =0;
        priorityNum = 0;
        memIndex = -1;
        quantumNum =0;
        virtualTime = SYSTEM.systemClock;
        trafficCount = 1;
        processedTime = pTime;

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
    public int getVirtualTime() {
        return virtualTime;
    }
    public int getProcessedTime() {
        return processedTime;
    }
    public int getTrafficCount() {
        return trafficCount;
    }
    public void increaseTrafficCount() {
        this.trafficCount++;
    }
    public void setProcessedTime(int processedTime) {
        this.processedTime = processedTime;
    }
    public void setVirtualTime(int virtutalTime) {
        this.virtualTime = virtutalTime;
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
    public int getPriorityNum() {
        return priorityNum;
    }
    public int getQuantumNum() {
        return quantumNum;
    }
    public void setQuantumNum(int quantumNum) {
        this.quantumNum = quantumNum;
    }
    public void setPriorityNum(int priorityNum) {
        this.priorityNum = priorityNum;
    }


    @Override
    public String toString() {
        return (jobNum  + " " +classNum  + " " +reqMem + " " +pTime + " " + priorityNum);
    }

}
