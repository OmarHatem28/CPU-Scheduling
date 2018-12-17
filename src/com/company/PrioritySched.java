package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class Compare implements Comparator<Process> {
    public int compare(Process a, Process b){
        return a.getPriority() - b.getPriority();
    }
}

public class PrioritySched {
    //context switching
    public PriorityQueue<Process> priorityQ;
    private ArrayList<Process> processes;
    private int contextSwitch;
    public PrioritySched(ArrayList<Process> processes, int contextSwitch){
        this.contextSwitch = contextSwitch;
        this.processes = new ArrayList<>();
        for(int i=0;i<processes.size();++i)
            this.processes.add( new Process(processes.get(i)) );

        priorityQ = new PriorityQueue<>(new Compare());
        for(int i=0;i<processes.size();++i)
            this.processes.get(i).resetRemainingTime();

    }

    public void start(){
        Process top = processes.get(0);
        int time , numprocesses = processes.size();
        double averageWaitingTime = 0, averageTurnAroundTime = 0;
        System.out.println("--------------Priority Scheduling--------------");

        for (time = 0;processes.size() > 0; time++) {   // INFINITE LOOP!!! PriorityQ size always 0

            for(int i=0;i<processes.size();++i){        // check for new processes' arrival
                if( processes.get(i).getArrivalTime() <= time && !priorityQ.contains(processes.get(i)))
                    priorityQ.add(processes.get(i));
            }

            if(!priorityQ.isEmpty()) {
                if (top != priorityQ.peek()) {          // if a higher priority process is added
                    top = priorityQ.peek();             // context switching should be done here
                    System.out.println("Process:" + top.getName()
                            + " is runnning, remaining time = " + top.getRemainingTime());
                }
            }
            if(!priorityQ.isEmpty())
                for(int i=0;i<processes.size();++i)     // increment waiting time for processes that aren't running
                    if( processes.get(i) != top && priorityQ.contains(processes.get(i)))
                        processes.get(i).incrementWaitingTime();


            if(!priorityQ.isEmpty()) {
                top.decrementRemainingTime();
                if (top.getRemainingTime() <= 0) {
                    processes.remove(priorityQ.remove());
                    averageWaitingTime += top.getWaitingTime();
                    averageTurnAroundTime += time - top.getArrivalTime();
                    System.out.println("Process:" + top.getName()
                            + " finished processing with waiting time = " + top.getWaitingTime()
                            + " and turnaround time = " + (time - top.getArrivalTime() + 1));

                }
            }

            if( time % 5 == 0 )                         // Aging for starvation problem
                for(int i=0;i<processes.size();++i)
                    if( processes.get(i).getPriority() > 1 && priorityQ.contains(processes.get(i)) )
                        processes.get(i).decrementPriority();

        }

        averageWaitingTime /= numprocesses;
        averageTurnAroundTime /= numprocesses;

        System.out.println("Priority scheduling average waiting time = " + averageWaitingTime );
        System.out.println("Priority scheduling average turnaround time = " + averageTurnAroundTime );
        System.out.println("===============================================");

    }

}
/*
3 1 1
p1 5 1 5
p2 1 5 3
p3 2 2 1
 */