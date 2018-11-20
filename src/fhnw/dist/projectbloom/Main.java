package fhnw.dist.projectbloom;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Double p = 0.01;
        int n = 0;
        int falsePositives = 0;

        ArrayList<String> WordListFromFile = new ArrayList<>();

        File wordFile = new File("../data/words.txt");
        try (Scanner scanner = new Scanner(wordFile)) {
            while (scanner.hasNextLine()){
                n++;
                WordListFromFile.add(scanner.nextLine());
            }
        }
        Bloom b = new Bloom(p, n, WordListFromFile); // Create new Bloom filter, already passing the WordListFromFile which gets added on Object construction
        ArrayList<String> testStrings = GenerateRandomStrings(10000);

        for (String ts : testStrings){
            if(b.probablyContains(ts)){
                falsePositives++;
            }
        }

        System.out.println("expected p=" + p);
        System.out.println("actual   p=" + MessageFormat.format("{0,number,#.#########}", (double)falsePositives / (double)testStrings.size()));


    }

    // Helpermethods to generate Test data

    private static ArrayList<String> GenerateRandomStrings(int numStrings){
        ArrayList<String> stringsList = new ArrayList<>();
        Random r = new Random();
        while (numStrings-- != 0){
            stringsList.add(randomAlphaNumeric(getRandomInt(4,9)));
        }
        return stringsList;
    }

    public static String randomAlphaNumeric(int count) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static int getRandomInt (int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
