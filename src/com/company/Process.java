package com.company;

public class Process {
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int priority;
    private String name;

    public Process(int arriveTime, int burstTime, String name, int priority) {
        this.arrivalTime = arriveTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.name = name;
        waitingTime = 0;
    }

    public Process(){}

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
}

