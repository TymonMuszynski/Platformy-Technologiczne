package org.example;
import java.util.LinkedList;

public class TaskScheduler {
    private final LinkedList<Task> taskQueue = new LinkedList<>();

    public synchronized void addTask(Task task) {
            taskQueue.add(task);
            // notify thread to wake up
            notify();
    }

    public synchronized Task getNextTask() throws InterruptedException {
            while (taskQueue.isEmpty()) {
                wait();
            }
            return taskQueue.remove();
    }
}
