package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

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

        PrioritySched pScheduler = new PrioritySched(processes,contextSwitch);
        pScheduler.start();

    }
}
