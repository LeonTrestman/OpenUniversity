/*
Class that represents the Woods Pool
 */

import java.util.ArrayList;
import java.util.Random;

public class WordsPool {

    private ArrayList<String> wordsPool = new ArrayList<String>(); // The words pool
    private int numOfWords; //num of words in our pool

    //Default Constructor
    public WordsPool() {
        String[] defaultPool = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        for (int i = 0; i < defaultPool.length; i++) {
            wordsPool.add(defaultPool[i]);
        }
        numOfWords = wordsPool.size();
    }

    //Constructor for passing ArrayList, arrayList used as the wordpool
    public WordsPool(ArrayList<String> arrayList) {
        wordsPool = arrayList;
        numOfWords = arrayList.size();
    }

    //Constructor for passing Array of Strings , StringArray used as the wordpool
    public WordsPool(String[] StringArray) {
        for (int i = 0; i < StringArray.length; i++) {
            wordsPool.add(StringArray[i]);
        }
        numOfWords = wordsPool.size();
    }

    //returns random word from the wordpool
    public String getWord() {
        Random rand = new Random();
        return wordsPool.get(rand.nextInt(numOfWords));
    }

}
