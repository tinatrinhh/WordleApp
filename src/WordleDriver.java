import java.util.Scanner;
/**
 * A simple text interface to play the game Wordle
 * @author cmb79981
 *
 */
public class WordleDriver {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		boolean play = true;
		while (play) {
			//Wordle game = new Wordle("wordle.txt");
			Wordle game = new Absurdle("dictLen4-10.txt");
			System.out.println("Welcome to the game of Wordle! The computer has picked its word,");
			System.out.println("please begin guessing.");
			System.out.println();
			System.out.println("The word has "+game.getCorrectWord().length()+" letters");
			while (!game.gameOver()) {
				System.out.println();
				System.out.println("What is your next guess?");
				String next = in.next();
				System.out.println();
				String response = game.makeGuess(next);
				System.out.println(response);
			}
			System.out.println();
			System.out.println("Game Over! The word was: "+game.getCorrectWord());
			System.out.println("Would you like to play again? y/yes or n/no?");
			String again = in.next();
			if(again.toLowerCase().charAt(0)=='n') {
				play = false;
			}
		}
	}
}
