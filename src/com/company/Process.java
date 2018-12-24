package com.company;

public class Process {
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int priority;
    private int remainingTime;
    private String name;
    private int quantum;

    public Process(int arriveTime, int burstTime, String name, int priority) {
        this.arrivalTime = arriveTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.name = name;
        waitingTime = 0;
        quantum = 0;
    }

    public Process(int arriveTime, int burstTime, String name, int priority, int quantum) {
        this.arrivalTime = arriveTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.name = name;
        this.quantum = quantum;
        waitingTime = 0;
    }

    public Process(Process p){
        this.arrivalTime = p.getArrivalTime();
        this.burstTime = p.getBurstTime();
        this.priority = p.getPriority();
        this.name = p.getName();
        waitingTime = 0;
        quantum = 0;
    }

    public Process(){}

    public void resetRemainingTime(){
        this.remainingTime = this.burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void decrementRemainingTime(){
        this.remainingTime--;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // Get the arrive time value
    public int getArrivalTime() {
        return arrivalTime;
    }

    // Set the arrive time value
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Get the burst time value
    public int getBurstTime() {
        return burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    // Set the burst time value
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    // Get the process name
    public String getName() {
        return name;
    }

    // Set the process name
    public void setName(String name) {
        this.name = name;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }


    public void incrementWaitingTime(){
        this.waitingTime++;
    }

    public void decrementPriority(){
        --priority;
    }

}

