package ru.otus.homework;

/**
 * @Author Dmitry Volovod
 * created on 03.04.2017
 */
public class BullCowCount {
    private int bulls = 0;
    private int cows = 0;

    public int getBulls() {
        return bulls;
    }

    public void incrementBulls() {
        bulls++;
    }

    public int getCows() {
        return cows;
    }

    public void incrementCows() {
        cows++;
    }
}
