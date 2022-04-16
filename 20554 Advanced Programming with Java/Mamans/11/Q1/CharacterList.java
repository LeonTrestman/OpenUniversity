/*
class that represents the Character List
*/

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterList {

    private ArrayList<Character> usedCharacter; //chars user have used
    private ArrayList<Character> unUsedCharacter; // chars unused by user

    public CharacterList() {
        usedCharacter = new ArrayList<Character>();
        unUsedCharacter = new ArrayList<Character>(Arrays.asList
                ('a', 'b', 'c', 'd',
                        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                        'p', 'q', 'r', 's', 't',
                        'u', 'v', 'w', 'x', 'y', 'z'));

    }

    //returns arraylist of used characters
    public ArrayList<Character> getUsedCharacters() {
        return usedCharacter;
    }

    //returns arraylist of unused characters
    public ArrayList<Character> getUnUsedCharacters() {
        return unUsedCharacter;
    }

    //returns if the char is used
    public boolean usedChar(char ans) {
        return usedCharacter.contains(ans);
    }

    //receives a valid char (in string format) and updates the CharacterList
    public void updateCharacterList(char c) {
        removeUnusedChar(c);
        addCharacterToUsed(c);
    }

    //removes character from unused characters arraylist
    private void removeUnusedChar(char Character) {
        unUsedCharacter.remove(unUsedCharacter.indexOf(Character));
    }

    //adds character to used characters arraylist
    private void addCharacterToUsed(char Character) {
        usedCharacter.add(Character);
    }

}
