package fhnw.dist.projectbloom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Double p = 0.01;
        int n = 0, falsePositives = 0, numTestStrings = 100000;
        ArrayList<String> WordListFromFile = new ArrayList<>();
        File wordFile = new File(Main.class.getResource("data/words.txt").getFile());
        try (Scanner scanner = new Scanner(wordFile)) {
            while (scanner.hasNextLine()) {
                n++;
                WordListFromFile.add(scanner.nextLine());
            }
        }
        Bloom b = new Bloom(p, n, WordListFromFile); // Create new Bloom filter, already passing the WordListFromFile which gets added on Object construction
        ArrayList<String> testStrings = GenerateRandomStrings(numTestStrings);
        for (String ts : testStrings) {
            if (b.probablyContains(ts)) {
                falsePositives++;
            }
        }
        System.out.println("-------------------------------------");
        System.out.println("n (Num. words in file):   " + b.getN());
        System.out.println("m (Filtersize):           " + b.getM());
        System.out.println("k (Num Hashfunctions):    " + b.getK());
        System.out.println("Expected p (Probability): " + b.getP());
        System.out.println("Actual   p (Probability): " + (double) falsePositives / (double) numTestStrings);
        System.out.println("False Positives:          " + falsePositives);
        System.out.println("Tested strings:           " + numTestStrings);
        System.out.println("-------------------------------------");
    }

    // Helpermethods to generate Test data

    // Creates string with random length between 4 and 9 characters;
    private static ArrayList<String> GenerateRandomStrings(int numStrings) {
        ArrayList<String> stringsList = new ArrayList<>();
        Random r = new Random();
        while (numStrings-- != 0) {
            stringsList.add(randomAlphaNumeric(getRandomInt(4, 9)));
        }
        return stringsList;
    }

    public static String randomAlphaNumeric(int count) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    // Returns random Int between min and max
    private static int getRandomInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
