package helloworld.memory;

import java.util.ArrayList;

/**
 * Runs heap and stack memory experiments to test JVM memory limits.
 */
public class MemoryExperiment {

    /**
     * Runs a heap stress test by creating large objects
     * until the JVM throws an OutOfMemoryError.
     */
    public void runHeapTest() {
        int objectCount = 0;
        long startTime = System.currentTimeMillis();

        long lastPrintTime = startTime;
        long throttlingPoint = -1;

        ArrayList<long[]> memoryList = new ArrayList<>();

        try {
            while (true) {
                long[] data = new long[400];
                memoryList.add(data);
                objectCount++;

                if (objectCount % 1000 == 0) {
                    long now = System.currentTimeMillis();
                    long interval = now - lastPrintTime;

                    System.out.println("Objects: " + objectCount +
                            " | Time: " + (now - startTime) / 1000.0 + " s" +
                            " | Interval: " + interval + " ms");

                    if (interval > 200 && throttlingPoint == -1) {
                        throttlingPoint = objectCount;
                        System.out.println("Throttling detected at: " + throttlingPoint + " objects");
                    }

                    lastPrintTime = now;
                }
            }

        } catch (OutOfMemoryError e) {
            long endTime = System.currentTimeMillis();

            System.out.println("\nHEAP TEST FINISHED");
            System.out.println("Total objects created: " + objectCount);
            System.out.println("Time elapsed: " + (endTime - startTime) / 1000.0 + " seconds");

            if (throttlingPoint != -1) {
                System.out.println("Throttling started at: " + throttlingPoint + " objects");
            } else {
                System.out.println("No clear throttling detected");
            }
        }
    }

    /**
     * Runs a stack stress test using recursion
     * until the JVM throws a StackOverflowError.
     */
    public void runStackTest() {
        long startTime = System.currentTimeMillis();

        try {
            recursiveMethod(0);
        } catch (StackOverflowError e) {
            long endTime = System.currentTimeMillis();

            System.out.println("\nSTACK TEST FINISHED");
            System.out.println("Time elapsed: " + (endTime - startTime) / 1000.0 + " seconds");
        }
    }

    /**
     * Recursive helper method used for the stack test.
     *
     * @param depth current recursion depth
     */
    private void recursiveMethod(int depth) {
        if (depth % 1000 == 0) {
            System.out.println("Current depth: " + depth);
        }

        recursiveMethod(depth + 1);
    }

    /**
     * Main method used to run memory experiments.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        MemoryExperiment experiment = new MemoryExperiment();

        experiment.runHeapTest();
        // experiment.runStackTest();
    }
}