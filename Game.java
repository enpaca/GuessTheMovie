import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    //Fields:
    private int numberOfLinesInMovieFile = 0; //Keeps count of how many movie titles are in the list.
    private String movieTitle;                //Holds the randomly chosen movie title.
    private String convertedMovieTitle;       //Holds the movie title the player guesses.
    private int numberOfWrongLetters = 0;     //Keeps track of how many guesses are used.
    private String triedCharacters = "";      //Keeps track of all the tried character.
    private String symbol = "*";              //The symbol to replace letters in the movie title with.

    //Constructor:

    /**
     * This constructs the object depending, on the file which is passed
     * as an input, in parameter movieFilePath.
     *
     * @param movieFilePath String that contains the movie file path.
     */
    public Game(String movieFilePath) {
        this.movieTitle = pickRandomMovie(movieFilePath).toLowerCase();
        this.convertedMovieTitle = convertMovieTitle(this.movieTitle);
    }

    //Methods:

    /**
     * This method takes a character as an input parameter and searches it in the movie title field. If
     * the character is among the letters it calls revealMovieTitle(), if not decreases the number
     * of guesses left and saves the character,in triedCharacter field, to keep track of tried characters.
     *
     * @param characterFromUser Parameter of type char.
     */
    public void searchInTitle(char characterFromUser) {
        //Check if character matches any letter in the movie title.
        int charPosition = this.movieTitle.indexOf(characterFromUser, 0);
        if (charPosition == -1) {
            //If it doesn't match...
            //No guesses (points) lost when player types the same letter.
            if (this.triedCharacters.indexOf(characterFromUser) == -1) {
                //Update number of wrong letters(guesses)
                this.numberOfWrongLetters++;
                //If no such character was tried, than update triedCharacters
                this.triedCharacters += characterFromUser;
            }
        } else {
            //If it matches, replace symbols with character.
            convertedMovieTitle = revealMovieTitle(charPosition, characterFromUser);
        }
    }

    /**
     * Get the movie title with all revealed characters.
     *
     * @return Returns a string.
     */
    public String getConvertedMovieTitle() {
        return this.convertedMovieTitle;
    }

    /**
     * Get number of wrong letters that user has tried already.
     *
     * @return Returns an integer between 0 and 10
     */
    public int getNumberOfWrongLetters() {
        return this.numberOfWrongLetters;
    }

    /**
     * Get all the tried characters in this game.
     *
     * @return Returns a string.
     */
    public String getTriedCharacters() {
        return this.triedCharacters;
    }

    /**
     * Returns true if all the player has found all the characters in the movie title.
     *
     * @return Returns false if the movie has not yet been found (revealed).
     */
    public boolean noMoreCharactersToReveal() {
        //Check whether a symbol is left inside the field convertedMovieTitle.
        if (this.convertedMovieTitle.indexOf(this.symbol.charAt(0)) == -1) {
            return true;
        }
        return false;
    }

    /**
     * You can see if the game is lost.
     *
     * @return Returns true if the player has los the game.
     */
    public boolean youLose() {
        if (this.numberOfWrongLetters >= 10) {
            return true;
        }
        return false;
    }

    /**
     * Reveals the movie title with the character at the input
     *
     * @param firstCharacterPosition First occurrence of character in the string position
     * @param characterFromUser      The character to be placed in the movie title.
     * @return Returns the movie title with characters in the movie title
     */
    private String revealMovieTitle(int firstCharacterPosition, char characterFromUser) {
        String revealedMovieTitle = this.convertedMovieTitle;
        while (firstCharacterPosition >= 0) {
            revealedMovieTitle = revealedMovieTitle.substring(0, firstCharacterPosition) + characterFromUser
                    + revealedMovieTitle.substring(firstCharacterPosition + 1);
            firstCharacterPosition = this.movieTitle.indexOf(characterFromUser, firstCharacterPosition + 1);
        }
        return revealedMovieTitle;
    }

    /**
     * This method will convert the title of the movie initially, replacing every character in the string parameter
     * with a certain symbol.
     *
     * @param movieTitle This accepts a String value, holding the movie title
     * @return Returns a string same length with input parameter
     */
    private String convertMovieTitle(String movieTitle) {
        //Convert all letters in the movie with a symbol and return the string result.
        String convertedMovieTitle = movieTitle.replaceAll("[A-Za-z0-9]", this.symbol);
        return convertedMovieTitle;
    }

    /**
     * This helper method will pick a movie randomly from a file.
     *
     * @param movieFilePath Expects a string that has a path to a file with a list of movies.
     * @return Returns a string lateral, the movie name.
     */
    private String pickRandomMovie(String movieFilePath) {
        String[] allMovies = new String[100];
        String line;
        //Create file object
        File moviesFile = new File(movieFilePath);
        //Open the file and read it
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(moviesFile);
        } catch (FileNotFoundException exception) {
            System.out.println("File with the list of movies doesn't exist." + exception);
        }
        //Inside the file:
        while (fileScanner.hasNextLine()) {
            this.numberOfLinesInMovieFile++;
            line = fileScanner.nextLine();
            //Save all the movies in this array.
            allMovies[this.numberOfLinesInMovieFile] = line;
        }
        //Pick a movie randomly.
        return allMovies[generateRandomInteger(this.numberOfLinesInMovieFile)];
    }

    /**
     * This helper method will generate a random number between [1,fromOneToThis].
     *
     * @param fromOneToThis The upper limit of random number to be generated.
     * @return Returns an integer.
     */
    private int generateRandomInteger(int fromOneToThis) {
        double randomNumber = Math.random() * fromOneToThis; //Generate the random number.
        int number = (int) randomNumber + 1;                 //Add one and cast to int.
        return number;
    }
}
