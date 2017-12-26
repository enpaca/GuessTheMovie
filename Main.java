import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //In case args is used from shell.
        String movieFilePath = "movies.txt";
        if (args.length != 0) {
            movieFilePath = args[0];
        }
        //Create new game.
        Game newGame = new Game(movieFilePath);
        //Play the game, you have 10 guesses before you lose!
        while (newGame.getNumberOfWrongLetters() < 10) {
            System.out.println("You are guessing: " + newGame.getConvertedMovieTitle());
            System.out.println("You have guessed (" + newGame.getNumberOfWrongLetters() + ") wrong letters: "
                    + newGame.getTriedCharacters());
            System.out.print("Guess a letter:");
            //Read player input.
            char characterFromPlayer = readPlayerInput();
            //Search the character against movie title.
            newGame.searchInTitle(characterFromPlayer);
            //Are all the letters yet revealed?
            if (newGame.noMoreCharactersToReveal()) {
                System.out.println("You Won!");
                System.out.println("Movie title is " + newGame.getConvertedMovieTitle());
                break;
            }
        }
        //Output if you lose.
        if (newGame.youLose()) {
            System.out.println("You Lose");
        }
    }

    /**
     * Helper method to read input from user.
     *
     * @return Returns a character the user has typed.
     */
    private static char readPlayerInput() {
        //Create object to receive input
        Scanner userInputScanner = new Scanner(System.in);
        //Read input from player
        String userInput = userInputScanner.nextLine();
        //Extract character from player input
        char characterFromUser = userInput.charAt(0);
        return characterFromUser;
    }
}
