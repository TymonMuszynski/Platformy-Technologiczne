package org.example;

public class Task {
    private final int taskId;
    private final int input;

    public Task(int taskId, int input) {
        this.taskId = taskId;
        this.input = input;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getInput() {
        return input;
    }
}