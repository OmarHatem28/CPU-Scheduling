package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class CompareArrival implements Comparator<Process> {
    public int compare(Process a, Process b){
        return a.getArrivalTime() - b.getArrivalTime();
    }
}

class ComparePriority implements Comparator<Process> {
    public int compare(Process a, Process b){
        return a.getPriority() - b.getPriority();
    }
}

class CompareRemaining implements Comparator<Process> {
    public int compare(Process a, Process b){
        return a.getRemainingTime() - b.getRemainingTime();
    }
}

public class AGSched {

    private ArrayList<Process> processes;
    
    private PriorityQueue<Process> lv1, lv2, lv3;
    int contextSwitch , time;

    public AGSched(ArrayList<Process> processes, int contextSwitch){
        lv1 = new PriorityQueue<>(new CompareArrival());
        lv2 = new PriorityQueue<>(new ComparePriority());
        lv3 = new PriorityQueue<>(new CompareRemaining());

        this.contextSwitch = contextSwitch;
        this.processes = new ArrayList<>();
        for(int i=0;i<processes.size();++i)     //copy original processes arraylist
            this.processes.add( new Process(processes.get(i)) );

        for(int i=0;i<processes.size();++i)
            this.processes.get(i).resetRemainingTime();

        for (int i = 0; i < processes.size(); ++i)
            lv1.add(processes.get(i));
        time = 0;
        start();
    }
    
    public void start(){
        int numprocesses = processes.size();
        double averageWaitingTime = 0, averageTurnAroundTime = 0;
        System.out.println("------------------AG Scheduling----------------");

        for (; processes.size() > 0; time++) {

            if (!lv1.isEmpty()) {
                fcfs();
            } else if (!lv2.isEmpty()) {
                priorityScheduling();
            } else if (!lv3.isEmpty()) {

            }

            //check finished processes to remove them
            for (int i = 0; i < processes.size(); i++)
                if (processes.get(i).getRemainingTime() <= 0) processes.remove(i--);
        }
        
        averageWaitingTime /= numprocesses;
        averageTurnAroundTime /= numprocesses;

        System.out.println("AG scheduling average waiting time = " + averageWaitingTime );
        System.out.println("AG scheduling average turnaround time = " + averageTurnAroundTime );
        System.out.println("===============================================");

    }

    public void fcfs(){

        if (lv1.peek().getArrivalTime() <= time) {//if process arrived process it for ceil(quantum/4)

            int processTime = (int) Math.ceil(lv1.peek().getQuantum() / 4.0);

            if (processTime > lv1.peek().getQuantum()) {
                processTime = lv1.peek().getQuantum();
            }

            increaseWaitingTime(processTime,lv1.peek());
            time += processTime;
            //if a process arrives during the processing time: wt time = wt - arrival + time before processing
            for (int i = 0; i < processes.size(); i++)
                if (processes.get(i).getArrivalTime() <= time && processes.get(i).getArrivalTime() > time - processTime){
                    processes.get(i).setWaitingTime(
                            processes.get(i).getWaitingTime() - processes.get(i).getArrivalTime() + (time - processTime));
                }

            lv1.peek().setRemainingTime(lv1.peek().getRemainingTime() - processTime);
            if (lv1.peek().getRemainingTime() > 0) {//if it isn't finished quantum += 2 and move it to level 2
                lv1.peek().setQuantum(lv1.peek().getQuantum() + 2);
                lv2.add(lv1.peek());
            }

            lv1.poll();
        }
    }

    public void priorityScheduling(){

        int processTime = (int) Math.ceil(lv2.peek().getQuantum() / 4.0);

        if (processTime > lv2.peek().getQuantum()) {
            processTime = lv2.peek().getQuantum();
        }

        increaseWaitingTime(processTime,lv2.peek());
        time += processTime;
        //if a process arrives during the processing time: wt time = wt - arrival + time before processing
        for (int i = 0; i < processes.size(); i++)
            if (processes.get(i).getArrivalTime() <= time && processes.get(i).getArrivalTime() > time - processTime){
                processes.get(i).setWaitingTime(
                        processes.get(i).getWaitingTime() - processes.get(i).getArrivalTime() + (time - processTime));
            }

        lv2.peek().setRemainingTime(lv2.peek().getRemainingTime() - processTime);
        if (lv2.peek().getRemainingTime() > 0) {//if it isn't finished (quantum += quantum / 2) and move it to level 3
            lv2.peek().setQuantum(lv2.peek().getQuantum() + lv2.peek().getQuantum() / 2);
            lv3.add(lv2.peek());
        }

        lv2.poll();

    }

    public void increaseWaitingTime(int val, Process p) { // increases waiting time for all processes
        for (int i = 0; i < processes.size(); i++) {      // except the one being processed
            if (processes.get(i) != p && processes.get(i).getArrivalTime() <= time) {
                processes.get(i).setWaitingTime(processes.get(i).getWaitingTime() + val);
            }
        }
    }

}
