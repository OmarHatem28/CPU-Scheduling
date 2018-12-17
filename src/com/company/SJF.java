package com.company;

import java.util.*;

public class SJF {

    Queue<Process> priorityQ;
    ArrayList<Process> processes = new ArrayList<>();
    int NumbProcess, contextSwitch;

    public SJF(ArrayList<Process> processes, int contextSwitch){
        for(int i=0;i<processes.size();++i) {
            this.processes.add(new Process(processes.get(i)));
        }
        this.NumbProcess = processes.size();
        this.contextSwitch = contextSwitch;
        start();
    }

    public void start(){

        for (int i = 0; i < processes.size(); i++) {
            processes.get(i).resetRemainingTime();
        }

        Comparator<Process> comparator = (o1, o2) -> {
            if ( o1.getRemainingTime() > o2.getRemainingTime() )
                return 1;
            else if ( o1.getRemainingTime() < o2.getRemainingTime() )
                return -1;
            return 0;
        };

        priorityQ = new PriorityQueue<>(comparator);


        int co =0;
        double avgTurnAround=0.0,avgWaitingTime=0.0;
        boolean canWork = true;
        Process process = new Process();

        while ( !processes.isEmpty() ){
            for ( int i=0;i<processes.size();i++){
                if ( processes.get(i).getArrivalTime() <= co ){
                    boolean flag = true;
                    for (int j = 0; j < priorityQ.size(); j++) {
                        if ( priorityQ.contains(processes.get(i)) ){
                            flag=false;
                            break;
                        }
                    }
                    if ( flag ){
                        priorityQ.add(processes.get(i));
                    }
                }
            }
            if ( canWork && !priorityQ.isEmpty() ){
                System.out.println(co+") "+priorityQ.peek().getName()+" is being processed.");
                canWork=false;
                process=priorityQ.peek();
            }
            if ( !priorityQ.isEmpty() && priorityQ.peek().getRemainingTime() > 0 ){
                if ( process != priorityQ.peek() ){
                    System.out.println(co+") "+"Switched from "+process.getName()+" to "+priorityQ.peek().getName());
                    process = priorityQ.peek();
                    co+=contextSwitch;
                    for (int i = 0; i < processes.size(); i++) {
                        if ( priorityQ.contains(processes.get(i)) ){
                            processes.get(i).setWaitingTime(processes.get(i).getWaitingTime() + contextSwitch);
                        }
                    }
                    continue;
                }
                System.out.println(co+") "+priorityQ.peek().getName()+" is being processed, remaining time is: "+priorityQ.peek().getRemainingTime());
                priorityQ.peek().decrementRemainingTime();
            }
            if ( priorityQ.size() > 1 ){
                for (int i = 0; i < processes.size(); i++) {
                    if ( priorityQ.contains(processes.get(i)) && priorityQ.peek() != processes.get(i) ){
                        processes.get(i).incrementWaitingTime();
                    }
                }
            }
            if ( !priorityQ.isEmpty() && priorityQ.peek().getRemainingTime() <= 0 ){
                int turnAround = co-priorityQ.peek().getArrivalTime()+1;
                System.out.println(co+") "+priorityQ.peek().getName()+" finished processing, with waiting time = "
                        +priorityQ.peek().getWaitingTime()+" and Turn Around Time = "+turnAround);
                avgTurnAround+=turnAround;
                avgWaitingTime+=priorityQ.peek().getWaitingTime();
                processes.remove(priorityQ.remove());
                canWork=true;
                co+=contextSwitch;
                if ( !priorityQ.isEmpty() ) {
                    for (int i = 0; i < processes.size(); i++) {
                        if ( priorityQ.contains(processes.get(i)) ){
                            processes.get(i).setWaitingTime(processes.get(i).getWaitingTime() + contextSwitch);
                        }
                    }
                }
            }
            co++;
        }
        System.out.println("==================== SJF ===================");
        System.out.println("Average Turn Around  = "+ avgTurnAround /NumbProcess);
        System.out.println("Average Waiting time = "+ avgWaitingTime/NumbProcess);
        System.out.println("==================== SJF ===================");
    }

}
