package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoundRobin {

    Queue<Process> priorityQ;
    ArrayList<Process> processes = new ArrayList<>();
    ArrayList<Process> ready = new ArrayList<>();
    private int NumbProcess, contextSwitch, roundRobinQuant;

    public RoundRobin(ArrayList<Process> processes, int contextSwitch, int roundRobinQuant){
        for(int i=0;i<processes.size();++i) {
            this.processes.add(new Process(processes.get(i)));
        }
        this.NumbProcess = processes.size();
        this.contextSwitch = contextSwitch;
        this.roundRobinQuant = roundRobinQuant;

//        Process temp = new Process();
//
//        for ( int i=0;i<processes.size()-1;i++){
//            temp = processes.get(i);
//            for (int j = i+1; j < processes.size(); j++)
//                if (processes.get(j).getArrivalTime() < temp.getArrivalTime() )
//                    temp = processes.get(j);
//
//            // Swap the found minimum element with the first element
//            swap(temp, processes.get(i));
//        }

        start();
    }

//    public void swap(Process obj1, Process obj2){
//        Process myTemp;
//        myTemp = obj1;
//        obj1 = obj2;
//        obj2 = myTemp;
//    }

    public void start(){

        for (int i = 0; i < processes.size(); i++) {
            processes.get(i).resetRemainingTime();
        }

        Comparator<Process> comparator = (o1, o2) -> {
            if ( o1.getArrivalTime() > o2.getArrivalTime() )
                return 1;
            else if ( o1.getArrivalTime() < o2.getArrivalTime() )
                return -1;
            return 0;
        };

        priorityQ = new PriorityQueue<>(comparator);

        priorityQ.addAll(processes);

        processes.clear();
        while ( !priorityQ.isEmpty() ){
            processes.add(priorityQ.remove());
        }

        int co=0, ind=0;
        double avgTurnAround=0.0,avgWaitingTime=0.0;
        while ( !processes.isEmpty() ){
            for ( int i=0;i<processes.size();i++){
                if ( processes.get(i).getArrivalTime() <= co ){
                    boolean flag = true;
                    for (int j = 0; j < ready.size(); j++) {
                        if ( ready.contains(processes.get(i)) ){
                            flag=false;
                            break;
                        }
                    }
                    if ( flag ){
                        ready.add(processes.get(i));
                    }
                }
            }
            int temp = roundRobinQuant;
            while ( temp > 0 && !ready.isEmpty()){
                System.out.println(co+") "+ready.get(ind%ready.size()).getName()
                        +" Is Being Processed, Remaining time is: "+ready.get(ind%ready.size()).getRemainingTime());
                ready.get(ind%ready.size()).decrementRemainingTime();

                // increment waiting time of other ready processes
                for (int i = 0; i < ready.size(); i++) {
                    if ( i != ind%ready.size() ){
                        ready.get(i).incrementWaitingTime();
                    }
                }

                if ( ready.get(ind%ready.size()).getRemainingTime() <= 0 ){
                    int turnAround = co-ready.get(ind%ready.size()).getArrivalTime()+1;
                    int waiting = turnAround - ready.get(ind%ready.size()).getBurstTime();
                    ready.get(ind%ready.size()).setWaitingTime(waiting);
                    System.out.println(co+") "+ready.get(ind%ready.size()).getName()
                            +" Finished Processing, Waiting Time is: "+ready.get(ind%ready.size()).getWaitingTime()
                            +" Turn Around Time is: "+turnAround);
                    avgTurnAround+=turnAround;
                    avgWaitingTime+=ready.get(ind%ready.size()).getWaitingTime();
                    processes.remove(ready.remove(ind%ready.size()));
                    co+=temp;
                    break;
                }
                temp--;
                co++;
//                if ( temp >= 1 ){
//                    co++;
//                }
            }
            if ( !ready.isEmpty() ){
                ind++;
                co+=contextSwitch;
            }
            else{
                co++;
            }
        }
        System.out.println("==================== RR ===================");
        System.out.println("Average Turn Around  = "+ avgTurnAround /NumbProcess);
        System.out.println("Average Waiting time = "+ avgWaitingTime/NumbProcess);
        System.out.println("==================== RR ===================");
    }

}
