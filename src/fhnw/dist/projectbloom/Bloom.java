package fhnw.dist.projectbloom;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Bloom {
    private int n, m, k;
    private double p;
    private boolean[] list;
    protected ArrayList<HashFunction> hashFunctionList = new ArrayList<>();

    public Bloom(Double p, int n){
        this.p = p;
        this.n = n;
        this.m = (int) -((n * Math.log(p)) / (Math.log(2) * Math.log(2))); // geeignete Filtergrösse
        this.k = (int) -(Math.log(p) / Math.log(2)); //Anzahl Hashfunctions
        this.list = new boolean[m];
        Random r = new Random();
        for (int i = 0; i < k; k++){
            hashFunctionList.add(Hashing.murmur3_128(r.nextInt()));
        }
    }

    public Bloom(Double p, int n, ArrayList<String> aL){
        this.p = p;
        this.n = n;
        this.m = (int) -((n * Math.log(p)) / (Math.log(2) * Math.log(2))); // geeignete Filtergrösse
        this.k = (int) -(Math.log(p) / Math.log(2)); //Anzahl Hashfunctions
        this.list = new boolean[m];
        Random r = new Random();
        for (int i = 0; i < k; i++){
            hashFunctionList.add(Hashing.murmur3_128(r.nextInt()));
        }
        for (String s : aL){ // Add all words to the list with our nice method ;)
            this.addString(s);
        }


    }

    //iterate through all hash functions. If the item at any given position of the array is false,
    // then our string is not contained. Otherwise, it is probably contained inside the word list and we would
    // access the data to confirm it.
    public boolean probablyContains(String s){
        for (HashFunction function : hashFunctionList) {
            HashCode hash = function.hashString(s, Charset.defaultCharset());
            int pos = Math.abs(hash.asInt()) % this.m;
            if(!list[pos]){
                return false;
            }
        }
        return true;
    }

    //iterate through all hash functions. Calculate the corresponding position in the array and set it to true
    //if a position is already true, leave it as such
    public void addString (String s){
        for (HashFunction function : hashFunctionList) {
            HashCode hash = function.hashString(s, Charset.defaultCharset());
            int pos = Math.abs(hash.asInt()) % this.m;
            list[pos] = true;
            }
    }
}
