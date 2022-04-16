/*
MAMAN 11 Q1.
Leon Trestman, 
The program is a game in which the player is  guessing a random word from a default pool of words.
The player continues to guess the word a letter at a time until the player have guessed the whole word and he receives his total number of guessing tries.
Game isn't Case sensitive and invalid input doesn't count as a valid guess try.
*/


/*
This is the GameRunner class just used to create the game and run it.
*/
public class GameRunner {

    public static void main(String[] args) {
        Game currentGame = new Game();
        currentGame.run();
    }

}
