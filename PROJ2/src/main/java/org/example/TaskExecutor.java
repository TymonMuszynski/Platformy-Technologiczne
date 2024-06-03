package org.example;

public class TaskExecutor implements Runnable {
    private final TaskScheduler taskScheduler;
    private final ResultCollector resultCollector;

    private int progress;

    private double currentPi;
    private int input;
    private int id;

    public TaskExecutor(TaskScheduler taskScheduler, ResultCollector resultCollector) {
        this.taskScheduler = taskScheduler;
        this.resultCollector = resultCollector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskScheduler.getNextTask();
                input = task.getInput();
                id = task.getTaskId();
                double result = compute(input);
                resultCollector.addResult(result, id, input);
            } catch (InterruptedException e) {
                double proc = (double) progress /  (double) input *100;
                System.out.println("Progress of id: " + id + " is "+ proc + " % and current PI: " + currentPi);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private double compute(int input) throws InterruptedException {
        Thread.sleep(2000);
        double pi = 0;
        for(int n =1; n<=input; n++){
            Thread.sleep(200);
            progress = n;
            pi += Math.pow((-1), (n-1))/ (2*n-1);
            currentPi =pi;
        }
        return pi*4;
    }
}
