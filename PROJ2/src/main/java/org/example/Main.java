package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskScheduler taskScheduler = new TaskScheduler();
        ResultCollector resultCollector = new ResultCollector();
        int numberOfThreads = Integer.parseInt(args[0]);
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TaskExecutor(taskScheduler, resultCollector));
            threads[i].start();
        }

        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        while (true) {
            System.out.println("Enter input or 'E' to exit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("E")) {
                break;
            }
            try {
                int taskInput = Integer.parseInt(input);
                taskScheduler.addTask(new Task(counter, taskInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
            counter++;
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        resultCollector.printResults();

        scanner.close();
    }
}
