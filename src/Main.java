import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Double p = 0.01;
        File wordlist = new File("../data/words.txt");
        try (Scanner scanner = new Scanner(wordlist)) {

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
            }
        }

    }
}
