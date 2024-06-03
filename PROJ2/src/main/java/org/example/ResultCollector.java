package org.example;
import java.util.LinkedList;

public class ResultCollector {

    private final LinkedList<Result> results = new LinkedList<>();
    public synchronized void addResult(double result, int id, int input) {
            System.out.println(id + " -> Task, input: " + input + " result: " + result);
            results.add(new Result(id, result));
    }

    public synchronized void printResults() {
            for (Result result : results) {
                System.out.println("Result: " + result.getIndedx() + " is " + result.getResult());
            }
    }
}
