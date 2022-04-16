/*
Class that represents the chosen word to guess
*/
public class ChosenWord {

    private final String chosenWord;
    private final int wordLength;
    private String currentGuess;

    //Constructor with variable of String, uses the String as the ChosenWord
    public ChosenWord(String word) {
        chosenWord = word;
        wordLength = word.length();
        currentGuess = replaceCharsToUnderscores(chosenWord);
    }

    //replaces word characters with underscores
    private String replaceCharsToUnderscores(String word) {
        String underscoredWord = "";
        for (int i = 0; i < word.length(); i++) {
            underscoredWord = underscoredWord + "_";
        }
        return underscoredWord;
    }

    // returns the chosen word
    public String getChosenWord() {
        return chosenWord;
    }

    // returns the current guess String
    public String getCurrentGuess() {
        return currentGuess;
    }

    //returns if user guessed the word
    public boolean wordFound() {
        return currentGuess.equals(chosenWord);
    }

    //if char in the chosen word ,update it , else print msg the char isn't in the word
    //assumes valid char (validation is done before)
    public void tryGuessChar(char c) {
        if (isCharInWord(c, chosenWord))
            updateFoundChars(c);
        else
            System.out.println("Character " + c + " isn't in the word");
    }

    //returns if char c in the word
    private boolean isCharInWord(char c, String word) {
        //index of given char in the chosen word,if char doesn't exist in word returns -1
        int index = word.toLowerCase().indexOf(c);
        if (index == -1)
            return false;
        else
            return true;
    }

    //updates all appearances of found Char C in the current guess
    private void updateFoundChars(char c) {
        String temp = chosenWord;
        while (isCharInWord(c, temp)) {
            int indexChar = temp.toLowerCase().indexOf(c);
            String charPlacement= getCharPlacementCase(c, temp);
            currentGuess = updateSingleCharFromWord(charPlacement, indexChar, currentGuess);
            temp = removeSingleCharFromWord(charPlacement, indexChar, temp);
        }
    }

    //returns string of char placement case,
    //stirng can be at the beginning , end, or not in ends.
    private String getCharPlacementCase(char c, String CurrentWord) {
        int indexChar = CurrentWord.toLowerCase().indexOf(c);
        if (indexChar == 0)
            return "STRING_BEGINNING";
        else if (indexChar == wordLength - 1)
            return "STRING_END";
        else
            return "NOT_IN_ENDS";
    }

    //returns string with removed single char give by index , replaces the char with an underscore
    private String removeSingleCharFromWord(String charPlacement, int indexChar, String word) {
        switch (charPlacement) {
            case "STRING_BEGINNING":
                return '_' + word.substring(indexChar + 1);
            case "STRING_END":
                return word.substring(0, indexChar) + '_';
            case "NOT_IN_ENDS":
                return word.substring(0, indexChar) + '_' + word.substring(indexChar + 1);
        }
        return "";
    }

    //returns string updated by single char given by index , replaces the underscore with a char
    private String updateSingleCharFromWord(String charPlacement, int indexChar, String word) {
        switch (charPlacement) {
            case "STRING_BEGINNING":
                return chosenWord.charAt(indexChar) + word.substring(indexChar + 1);
            case "STRING_END":
                return currentGuess = word.substring(0, indexChar) + chosenWord.charAt(indexChar);
            case "NOT_IN_ENDS":
                return word.substring(0, indexChar) + chosenWord.charAt(indexChar) + word.substring(indexChar + 1);
        }
        return "";

    }

}
