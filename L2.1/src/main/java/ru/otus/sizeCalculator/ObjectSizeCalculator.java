package ru.otus.sizeCalculator;

import java.util.function.Supplier;

/**
 * @Author Dmitry Volovod
 * created on 14.04.2017
 */
public class ObjectSizeCalculator {
    private long SLEEP_TIME = 100;

    public long estimateObjectSize(Supplier<?> supplier, int sampleSize) {
        long result = 0;

        Object[] objects = new Object[sampleSize];

        // Create a number of objects
        try {
//            Object throwAway = aClass.newInstance();
            long startMemoryUsage = getMemoryUsage();

            for (int i = 0; i < objects.length; ++i) {
                objects[i] = supplier.get();
            }

            long endMemoryUsage = getMemoryUsage();

            result = Math.round((endMemoryUsage - startMemoryUsage) / (float) sampleSize);
        } catch (Exception ex) {
            System.out.println("Cannot create object using " + supplier);
        }
        return result;
    }

    private void cleanMemory() throws InterruptedException {
        try {
            for (int i = 0; i < 2; i++) {
                System.gc();
                Thread.sleep(SLEEP_TIME);
                System.runFinalization();
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getMemoryUsage() throws InterruptedException {
        cleanMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        cleanMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        return totalMemory - freeMemory;
    }
}
