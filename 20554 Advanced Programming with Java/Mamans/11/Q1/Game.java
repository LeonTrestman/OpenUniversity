
/*
 Class that represents the game
 * */

import java.util.Scanner;

public class Game {

    private boolean keepPlaying; //flag for continuing playing the game
    private int numOfGuesses;
    private ChosenWord chosenWord;
    private WordsPool wordsPool;
    private CharacterList characterList;

    //default Game constructor
    public Game() {
        keepPlaying = true;
        numOfGuesses = 0;
        wordsPool = new WordsPool();
        characterList = new CharacterList();
        chosenWord = new ChosenWord(wordsPool.getWord());
        welcomeUser();
    }

    //welcome prompt at the start of a game
    private void welcomeUser() {
        System.out.println("Welcome to the guessing game.\nYour goal is the guess the word,a letter at a time.\n" +
                "You will be presented with blank word in which each underscore '_' represents a missing english letter.\n" +
                "Good luck and have fun");
    }

    //runs the game
    public void run() {
        while (keepPlaying) {
            while (!chosenWordFound()) {
                guessAChar();
                incNumOfGuesses();
            }
            printTheChosenWord();
            printNumOfGuesses();
            resetGamePrompt();
        }
    }

    //prints message for finishing the round with the chosen word and the number of attempts
    private void printTheChosenWord() {
        System.out.println("Congratulations\nYou've managed to guess the word:" + chosenWord.getChosenWord());

    }

    //resets the game
    private void resetGame() {
        numOfGuesses = 0;
        wordsPool = new WordsPool();
        characterList = new CharacterList();
        chosenWord = new ChosenWord(wordsPool.getWord());
    }

    //guess a valid char and update the word and character list as result of this char
    private void guessAChar() {
        System.out.println(chosenWord.getCurrentGuess());
        char currentInputChar = guessValidUnusedChar(); // stores current user valid char
        characterList.updateCharacterList(currentInputChar); //update used and unused chars
        chosenWord.tryGuessChar(currentInputChar); // updates the chosen word
    }

    //returns if chosen word is completely guessed
    private boolean chosenWordFound() {
        return chosenWord.wordFound();
    }

    //Increases the number of guesses
    private void incNumOfGuesses() {
        this.numOfGuesses++;
    }

    //prints the number of guesses
    private void printNumOfGuesses() {
        System.out.println("Your Number of Guesses: " + numOfGuesses);
    }

    //Asks if the user wishes to continue playing
    // Y for continuing the game and starting another round ,N for stopping,

    private void resetGamePrompt() {
        System.out.println("Would you like to continue to play another round?\nEnter Y to continue,N to quit");
        switch (getYesOrNo().toUpperCase()) {
            case "N": // NO , stop playing
                keepPlaying = false;
                break;
            case "Y": //Yes , continue playing
                resetGame();
                break;
        }
    }

    //gets Y for yes or N for no from user
    private String getYesOrNo() {
        Scanner scanner = new Scanner(System.in);
        String ans = scanner.nextLine();//user input
        //loop until answer is Y or N
        while (!ans.toUpperCase().equals("Y") && !ans.toUpperCase().equals("N")) {
            System.out.println("Error,Please enter Y to continue or N to quit");
            ans = scanner.nextLine();
        }
        return ans;
    }

    // Getting char from user and validating the char,returns valid char (casting to char)
    private char guessValidUnusedChar() {
        System.out.println("Guess your character\nUnused character: " + characterList.getUnUsedCharacters().toString());
        Scanner scanner = new Scanner(System.in);
        String ans = scanner.nextLine();//user input
        //loops until valid and unused char received from user
        while (!ValidAndUnusedChar(ans)) {
            ans = scanner.nextLine();//user input
        }
        return validStringCharToCharLowerCase(ans);
    }


    //checks if given char(from passing String) is valid and unused
    private boolean ValidAndUnusedChar(String string) {

        if (!isStringAChar(string)) {
            System.out.println("Error,The input isn't char");
            return false;
        }

        if (charIsUsed(validStringCharToCharLowerCase(string))) // cast to char
        {
            System.out.println("Character: " + string + " was guessed previously.\n" +
                    "Pick another character\n" +
                    "Unused character: " + characterList.getUnUsedCharacters().toString());
            return false;
        }
        return true;
    }

    //checks if String is a valid character
    private boolean isStringAChar(String string) {
        String charlist = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return string.length() == 1 && charlist.contains(string);
    }

    //checks if the char already been selected (used)
    private boolean charIsUsed(char c) {
        return this.characterList.usedChar(c);
    }

    //returns valid char (of string type) as char type in lower case
    private char validStringCharToCharLowerCase(String string) {
        char c = string.toLowerCase().charAt(0); //casting the string to char and lowercase
        return c;
    }

}


