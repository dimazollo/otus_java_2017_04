package ru.otus.homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tully.
 */
public class Main {
    private static final int MEASURE_COUNT = 10;

    public static void main(String... args) {
        // Create standard and my own implementations of
        List<Integer> stdArrayList = new ArrayList<>();
        List<Integer> myArrayList = new MyArrayList<>();

        // Fill arrays with Integers
        int min = 0;
        int max = 9_999;

        System.out.println("Standard ArrayList : fill");
        measureTimeOfRoutine(() -> {
            stdArrayList.clear();
            for (int i = min; i < max + 1; i++) {
                stdArrayList.add(i);
            }
        });

        System.out.println("my ArrayList : fill");
        measureTimeOfRoutine(() -> {
            myArrayList.clear();
            for (int i = min; i < max + 1; i++) {
                myArrayList.add(i);
            }
        });
        System.out.println();

        System.out.println("Standard ArrayList : shuffle");
        measureTimeOfRoutine(() -> Collections.shuffle(stdArrayList));

        System.out.println("My ArrayList : shuffle");
        measureTimeOfRoutine(() -> Collections.shuffle(myArrayList));
        System.out.println();

        System.out.println("Copying from my ArrayList to standard one");
        measureTimeOfRoutine(() -> Collections.copy(stdArrayList, myArrayList));

        System.out.println("Copying from standard ArrayList to mine");
        measureTimeOfRoutine(() -> Collections.copy(myArrayList, stdArrayList));
        System.out.println();

        System.out.println("Ten starting elements of my ArrayList before sorting");
        for (int i = 0; i < 10; i++) {
            System.out.print(myArrayList.get(i) + " ");
        }

        System.out.println("\nSorting with Shell sort algorithm");
        measureTimeOfRoutine(() -> Collections.sort(myArrayList, Comparator.comparingInt(o -> o)));

        System.out.println("Ten starting elements of my ArrayList after sorting:");
        for (int i = 0; i < 10; i++) {
            System.out.print(myArrayList.get(i) + " ");
        }

        System.out.println("\n\nSorting of the same standard ArrayList with Timsort");
        measureTimeOfRoutine(() -> Collections.sort(myArrayList, Comparator.comparingInt(o -> o)));
    }

    private static void measureTimeOfRoutine(Runnable runnable) {
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / MEASURE_COUNT;
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}
