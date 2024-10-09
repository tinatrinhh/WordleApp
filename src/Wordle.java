import java.util.*;
import java.io.*;

/**
 * Implements a version of the Game Wordle
 * (https://www.nytimes.com/games/wordle/index.html). Wordle is a game where
 * players have six chances to guess a word. Based on their guess, the users are
 * given feedback on whether the letters in their guess were in the correct
 * location, in the word, but in the incorrect location, or not in the word. The
 * game ends when the user guesses the word or runs out of guesses.
 * 
 * @author Catie Baker
 *
 */
public class Wordle {

	protected int guessesRemaining;
	protected String currentGuess;

	protected Set<String> dictionary; // Dictionary of valid words
	protected String correctWord; // The word to be guessed

	protected static final int MAX_GUESSES = 6; // Maximum number of guesses allowed

	/**
	 * Determines the dictionary of words to use for the game and then sets up a new
	 * game
	 * 
	 * @param dictFilename
	 */
	public Wordle(String dictFilename) {
		dictionary = new HashSet<>();
		loadDictionary(dictFilename);
		reset();

	}

	/**
	 * Loads the dictionary of words from the specified file.
	 * 
	 * @param filename the name of the file containing the dictionary
	 */ 
	protected void loadDictionary(String filename) {
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String chosenWord = scanner.nextLine().trim().toLowerCase();
				if (!chosenWord.isEmpty()) {
					dictionary.add(chosenWord);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: Dictionary file not found: " + filename);
			System.exit(1);
		}
	}

	/**
	 * Takes a players guess and then returns the response for that guess. The guess
	 * must be a word in the dictionary that is the same length as the actual word.
	 * Based on the word, the boxes for each letter of the guess are determined (G =
	 * Green, Y = Yellow, N = Gray). If a guess has duplicate letters, the letter
	 * will only light up as many times as it occurs in the word, with green boxes
	 * getting priority over yellow boxes.
	 * 
	 * @param word the word to guess.
	 * @return If it is a valid guess, it will return a string of Gs, Ys, or Ns
	 *         representing the box color for each letter of the guess; If it is not
	 *         a valid guess, it will return a message that starts Error: and then
	 *         details the issue with the guess (e.g. wrong length or not in the
	 *         dictionary)
	 */
	public String makeGuess(String word) {
		// Convert the guessed word and correct word to lowercase
	    String lowerCaseWord = word.toLowerCase();
	    String lowerCaseCorrectWord = correctWord.toLowerCase();

	    if (lowerCaseWord.length() != lowerCaseCorrectWord.length()) {
	        return "Error: Guessed word length does not match the correct word length.";
	    }

	    if (!dictionary.contains(lowerCaseWord)) {
	        return "Error: Guessed word is not in the dictionary.";
	    }

	    currentGuess = lowerCaseWord;

	    StringBuilder feedback = new StringBuilder();
	    int[] correctWordCharCounts = new int[26];
	    
	 // First pass: Mark greens and count letters in correct word
	    for (int i = 0; i < lowerCaseCorrectWord.length(); i++) {
	        char guessedLetter = lowerCaseWord.charAt(i);
	        char correctLetter = lowerCaseCorrectWord.charAt(i);

	        if (guessedLetter == correctLetter) {
	            feedback.append("G"); // Green - correct letter in correct position
	        } else {
	            feedback.append("-");  // Placeholder for later checks
	            correctWordCharCounts[correctLetter - 'a']++; // Count letters in correct word
	        }
	    }

	    // Second pass: Mark yellows and grays
	    for (int i = 0; i < lowerCaseCorrectWord.length(); i++) {
	        if (feedback.charAt(i) == 'G') {
	            continue; // Already processed green letters
	        }

	        char guessedLetter = lowerCaseWord.charAt(i);
	        int letterIndex = guessedLetter - 'a';

	        if (correctWordCharCounts[letterIndex] > 0) {
	            // Mark as yellow if the letter exists in the correct word but wrong position
	            feedback.setCharAt(i, 'Y');
	            correctWordCharCounts[letterIndex]--; // Decrease the count for yellow usage
	        } else {
	            // Mark as gray if the letter is not in the correct word
	            feedback.setCharAt(i, 'N');
	        }
	    }

	    guessesRemaining--;
	    return feedback.toString();
	}
	/**
	 * Determines whether the game is over. A game is considered over when the
	 * player has no guesses remaining or has correctly guessed the word
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean gameOver() {
		return guessesRemaining <= 0 || correctWord.equals(currentGuess);
	}

	private boolean correctWordGuessed() {
		// Check if the correct word has been guessed
		return correctWord.equals(currentGuess);

	}

	/**
	 * Returns how many guesses the player has left
	 * 
	 * @return the number of guesses remaining
	 */
	public int guessesRemaining() {
		return guessesRemaining;
	}

	/**
	 * Resets for a new game of Wordle, selecting a new word and resetting the
	 * guesses remaining
	 */
	public void reset() {
		// Reset the number of remaining guesses
		guessesRemaining = MAX_GUESSES;
		correctWord = chooseSecretWord();

		// Clear the current guess (if needed)
		currentGuess = null;
	}

	// helper method to get a random word to play
	protected String chooseSecretWord() {
		Random random = new Random();
		int index = random.nextInt(dictionary.size());
		Iterator<String> iterator = dictionary.iterator();
		for (int i = 0; i < index; i++) {
			iterator.next();
		}
		return iterator.next();
	}

	/**
	 * Returns the correct word for the game
	 * 
	 * @return the word the user is trying to guess
	 */
	public String getCorrectWord() {
		return correctWord;
	}

}
