package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        /*

        3 1 2
        p1 5 1 5
        p2 1 5 3
        p3 2 2 1

        */

        ArrayList<Process> processes = new ArrayList<>();

        int processorCount, roundRobinQuant, contextSwitch;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of processes, round robin time quantum, context switch: ");
        processorCount = scanner.nextInt();
        roundRobinQuant = scanner.nextInt();
        contextSwitch = scanner.nextInt();

        for (int i = 0; i < processorCount; i++) {
            Process p = new Process();
            System.out.print("Enter Process Name, Process Arrival Time, Burst Time, Priority: ");
            p.setName(scanner.next());
            p.setArrivalTime(scanner.nextInt());
            p.setBurstTime(scanner.nextInt());
            p.setPriority(scanner.nextInt());

            processes.add(p);
        }

        SJF sjf = new SJF(processes,contextSwitch);
        RoundRobin roundRobin = new RoundRobin(processes,contextSwitch,roundRobinQuant);
        PrioritySched pScheduler = new PrioritySched(processes,contextSwitch);
        pScheduler.start();
    }
}
