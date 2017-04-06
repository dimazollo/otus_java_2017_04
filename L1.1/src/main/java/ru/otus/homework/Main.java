package ru.otus.homework;

import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author Dmitry Volovod
 * created on 02.04.2017
 */


public class Main {
    private static final String DICTIONARY = "dictionary.csv";
    private static BullCowGame bullCowGame;
    private static List<String> words;

    public static void main(String[] args) {

        try {
            readWords();
            do {
                setUpGameParameters();
                printIntro();
                playGame();
            } while (askToPlayAgain());
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Ups! Sneaky goblins have stolen hidden word you had to guess!\n " +
                    "Game cannot be played without any one...");
        }
    }

    private static void readWords() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(DICTIONARY);
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        words = reader.readAll().stream().map(line -> line[0].trim()).collect(Collectors.toList());
    }

    private static void setUpGameParameters() throws IllegalArgumentException {
        // Let the user enter length for a word to guess.
        int specifiedWordLength = 0;
        System.out.print("How many letters in a word do you prefer?(from 3 to 10) : ");
        try {
            do {
                specifiedWordLength = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                if (specifiedWordLength < 3 || specifiedWordLength > 10) {
                    System.out.print("Invalid word length! Please type a number from 3 to 10 : ");
                }
            } while (specifiedWordLength < 3 || specifiedWordLength > 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String hiddenWord = getRandomWordWithSpecifiedLength(words, specifiedWordLength);
        bullCowGame = BullCowGame.getNewGame(hiddenWord);
    }

    private static String getRandomWordWithSpecifiedLength(List<String> words, int wordLength) {
        List<String> filteredWords = words.stream()
                .filter(word -> word.length() == wordLength)
                .collect(Collectors.toList());
        String randomWord = filteredWords.get(new Random().nextInt(filteredWords.size()));
        return randomWord;
    }

    private static void printIntro() {
        // Introduce the game.
        System.out.println("Welcome to Bulls and Cows, a fun word game!\n" +
                "                           /  \\    \n" +
                "           __n__n__      __\\__/__\n" +
                "    .------`-\\00/-'      '-\\00/-'------.\n" +
                "   /  ##  ## (oo)          (oo) ##  ##  \\\n" +
                "  / \\## __   ./              \\.   __ ##/ \\\n" +
                "     |//YY \\|/                \\|/   \\\\|\n" +
                "     |||   |||                |||   |||");
        System.out.print("Can you guess the " + bullCowGame.getHiddenWordLength());
        System.out.println(" letter isogram I'm thinking of?\n");
    }

    private static void playGame() throws IOException {
        int maxTries = bullCowGame.getMaxTries();
        // Loop asking for guesses while the game
        // is NOT won and there are still tries remaining.
        while (!bullCowGame.isGameWon() && bullCowGame.getCurrentTry() <= maxTries) {
            String guess = getValidGuess();
            // Submit valid guess to the game
            BullCowCount bullCowCount = bullCowGame.submitValidGuess(guess);
            // Print number of bulls and cows
            System.out.print("Bulls = " + bullCowCount.getBulls());
            System.out.println(". Cows = " + bullCowCount.getCows() + "\n");
        }
        printGameSummary();
    }

    private static String getValidGuess() throws IOException {
        String guess;
        BullCowGame.GuessStatus Status = BullCowGame.GuessStatus.Invalid;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        do {
            // Get a guess from the player.
            System.out.print("Try " + bullCowGame.getCurrentTry() + " of " + bullCowGame.getMaxTries());
            System.out.print(". Enter your guess: ");
            guess = bufferedReader.readLine();

            // Check status and give feedback.
            Status = bullCowGame.checkGuessValidity(guess);
            switch (Status) {
                case Wrong_Length:
                    System.out.println("Please enter a " + bullCowGame.getHiddenWordLength() + " length word.\n");
                    break;
                case Not_Lowercase:
                    System.out.println("Please enter all lowercase letters.\n");
                    break;
                case Not_Isogram:
                    System.out.println("Please enter the word without repeating letters.\n");
                    break;
                default:
                    // Assume the guess is valid
                    break;
            }
        } while (Status != BullCowGame.GuessStatus.OK);    // Keep looping until we get no errors.
        return guess;
    }

    private static void printGameSummary() {
        if (bullCowGame.isGameWon()) {
            System.out.println("WELL DONE - YOU WIN!\n");
        } else {
            System.out.println("Better luck next time!\n");
        }
    }

    private static boolean askToPlayAgain() throws IOException {
        System.out.print("Do you want to play again? (y/n) ");
        String response;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        response = bufferedReader.readLine();
        return (response.toLowerCase().charAt(0) == 'y');
    }
}
