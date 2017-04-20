package ru.otus.homework;
import java.util.*;

/**
 * Created by tully.
 */
public class Main {
    private static final int MEASURE_COUNT = 100;

    public static void main(String... args) {
        Collection<Integer> example = new ArrayList<>();
        List<Integer> myArrayList = new MyArrayList<>();
        int min = 0;
        int max = 50;
        for (int i = min; i < max + 1; i++) {
            example.add(i);
            myArrayList.add(i);
        }
        Collections.shuffle((List<Integer>)example);
        Collections.shuffle(myArrayList);
        Collections.copy((ArrayList<Integer>) example, myArrayList);
        Collections.sort(myArrayList, Comparator.comparingInt(o -> o));
    }

    private static void findTheElement(Runnable runnable) {
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / MEASURE_COUNT;
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}
