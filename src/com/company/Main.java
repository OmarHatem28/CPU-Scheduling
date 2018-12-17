package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        ArrayList<Process> processes = new ArrayList<>();

        int processorCount, roundRobinQuant, contextSwitch;
        Scanner scanner = new Scanner(System.in);

        processorCount = scanner.nextInt();
        roundRobinQuant = scanner.nextInt();
        contextSwitch = scanner.nextInt();

        for (int i = 0; i < processorCount; i++) {
            Process p = new Process();
            scanner.nextLine();
            System.out.print("Enter Process Name: ");
            p.setName(scanner.nextLine());
            System.out.print("Enter Process Arrival Time, Burst Time, Priority: ");
            p.setArrivalTime(scanner.nextInt());
            p.setBurstTime(scanner.nextInt());
            p.setPriority(scanner.nextInt());

            processes.add(p);
        }

        SJF obj = new SJF(processes,contextSwitch);
    }
}
