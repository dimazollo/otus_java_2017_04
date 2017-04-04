package ru.otus.homework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author Dmitry Volovod
 * created on 02.04.2017
 */

public class BullCowGame {
    private static BullCowGame instance;
    private boolean isGameWon;
    private String hiddenWord;
    private int currentTry;

    private BullCowGame() {
    }

    public static BullCowGame getNewGame(String hiddenWord) {
        if (instance == null) {
            instance = new BullCowGame();
        }
        if (hiddenWord == null) throw new IllegalArgumentException("Hidden word in game can't be null.");
        instance.hiddenWord = hiddenWord;
        instance.currentTry = 1;
        instance.isGameWon = false;
        return instance;
    }

    public int getMaxTries() {
        Map<Integer, Integer> wordLengthToMaxTries = new HashMap<>();
        wordLengthToMaxTries.put(3, 6);
        wordLengthToMaxTries.put(4, 9);
        wordLengthToMaxTries.put(5, 12);
        wordLengthToMaxTries.put(6, 15);
        wordLengthToMaxTries.put(7, 19);
        wordLengthToMaxTries.put(8, 25);
        wordLengthToMaxTries.put(9, 32);
        wordLengthToMaxTries.put(10, 40);
        return wordLengthToMaxTries.get(hiddenWord.length());
    }

    public int getCurrentTry() {
        return currentTry;
    }

    public int getHiddenWordLength() {
        return hiddenWord.length();
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public GuessStatus checkGuessValidity(String guess) {
        if (!isIsogram(guess)) {
            return GuessStatus.Not_Isogram;
        } else if (!isLowercase(guess)) {
            return GuessStatus.Not_Lowercase;
        } else if (getHiddenWordLength() != guess.length()) {
            return GuessStatus.Wrong_Length;
        } else {
            return GuessStatus.OK;
        }
    }

    // provide a method for counting bulls & cows, and increases try # assuming valid guess
    public BullCowCount submitValidGuess(String guess) {
        // increment the turn number
        currentTry++;
        // setup a return variable
        BullCowCount bullCowCount = new BullCowCount();
        // loop through all letters in the guess
        int hiddenWordLength = hiddenWord.length();
        int guessLength = guess.length();
        for (int MHWChar = 0; MHWChar < hiddenWordLength; MHWChar++) {
            //	compare letters against the hidden word
            for (int GChar = 0; GChar < guessLength; GChar++) {
                if (guess.charAt(GChar) == hiddenWord.charAt(MHWChar)) {
                    // if they are in the same place
                    if (MHWChar == GChar) {
                        bullCowCount.incrementBulls();
                    } else {
                        bullCowCount.incrementCows();
                    }
                }
            }
        }
        if (bullCowCount.getBulls() == hiddenWordLength) {
            isGameWon = true;
        } else {
            isGameWon = false;
        }
        return bullCowCount;
    }

    private boolean isIsogram(String guess) {
        Set<Character> letters = new HashSet<>();
        for (int i = 0; i < guess.length(); i++) {
            if (!letters.contains(guess.charAt(i)))
                letters.add(guess.charAt(i));
            else return false;
        }
        return true;
    }

    private boolean isLowercase(String guess) {
        return guess.equals(guess.toLowerCase());
    }

    enum GuessStatus {
        Invalid,
        OK,
        Not_Isogram,
        Wrong_Length,
        Not_Lowercase
    }
}
