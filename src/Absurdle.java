import java.util.*;

public class Absurdle extends Wordle {

    private Set<String> possibleWords;

    public Absurdle(String dictFilename) {
        super(dictFilename);
        chooseRandomWord(super.dictionary);
    }

    @Override
    public String makeGuess(String word) {
        // Convert the guessed word and correct word to lowercase
        String lowerCaseWord = word.toLowerCase();
        String lowerCaseCorrectWord = getCorrectWord().toLowerCase();
        Set<String> lowerCaseCorrectWords = new HashSet<>(Collections.singletonList(lowerCaseCorrectWord));

        

        if (lowerCaseWord.equals(lowerCaseCorrectWord)) {
            currentGuess = word;
            return "Correct Guess!";
        }

        if (lowerCaseWord.length() != lowerCaseCorrectWord.length()) {
            return "Error: Guessed word length does not match the correct word length.";
        }

        if (!dictionary.contains(lowerCaseWord)) {
            return "Error: Guessed word is not in the dictionary.";
        }

        currentGuess = word;
        super.guessesRemaining--;

        // Update possible words based on the current guess
        updatePossibleWords(lowerCaseWord);
        return generateFeedback(lowerCaseWord, possibleWords);
        
    
        
    }

    // Method to update the possible words based on the current guess
    private void updatePossibleWords(String guess) {
        HashMap<String, HashSet<String>> updatedPossibleWords = new HashMap<>();

        for (String possibleWord : possibleWords) {
            StringBuilder feedback = new StringBuilder();

            for (int i = 0; i < possibleWord.length(); i++) {
                char guessedLetter = guess.charAt(i);
                char correctLetter = possibleWord.charAt(i);

                if (guessedLetter == correctLetter) {
                    feedback.append("G");
                } else if (guess.indexOf(correctLetter) != -1) {
                    feedback.append("Y");
                } else {
                    feedback.append("N");
                }
            }

            if (!updatedPossibleWords.containsKey(feedback.toString())) {
            	updatedPossibleWords.put(feedback.toString(), new HashSet<String>());
            }
            updatedPossibleWords.get(feedback.toString()).add(possibleWord);
        }
     // Find the largest set of possible words
        Set<String> largestSet = new HashSet<>();
        for (Set<String> wordSet : updatedPossibleWords.values()) {
            if (wordSet.size() > largestSet.size()) {
                largestSet = wordSet;
            }
        }

        // Update possibleWords with the largest set
        possibleWords = largestSet;
    }
    
    
    // Method to generate feedback based on the guessed word and list of correct words
    private String generateFeedback(String guessedWord, Set<String> correctWords) {
        StringBuilder feedback = new StringBuilder();

        for (int i = 0; i < guessedWord.length(); i++) {
            char guessedLetter = guessedWord.charAt(i);
            boolean correctLetterInAllWords = true;
            for (String correctWord : correctWords) {
                if (correctWord.charAt(i) != guessedLetter) {
                    correctLetterInAllWords = false;
                    break;
                }
            }

            if (correctLetterInAllWords) {
                feedback.append("G");
            } else {
                boolean correctLetterInAnyWord = false;
                for (String correctWord : correctWords) {
                    if (correctWord.indexOf(guessedLetter) != -1) {
                        correctLetterInAnyWord = true;
                        break;
                    }
                }
                if (correctLetterInAnyWord) {
                    feedback.append("Y");
                } else {
                    feedback.append("N");
                }
            }
        }

        return feedback.toString();
    }

    
    @Override
    public void reset() {
        // Reset the game state
        super.reset();

        // Initialize the possible words based on the dictionary
        initializePossibleWords();
    }


    @Override
    public String getCorrectWord() {
    	  return chooseRandomWord(possibleWords);
    }


    private void initializePossibleWords() {
        // Initialize the possible words to all words of the same length as the correct word
        possibleWords = new HashSet<>();
        String temp = chooseRandomWord(dictionary);
        for (String word : dictionary) {
            if (word.length() == temp.length()) {
                possibleWords.add(word);
            }
        }
    }

    private String chooseRandomWord(Set<String> words) {
        // Choose a random word from the given set of words
        List<String> wordList = new ArrayList<>(words);
        if (wordList.isEmpty()) {
            throw new IllegalArgumentException("Word list is empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        return wordList.get(randomIndex);
    }


    
}