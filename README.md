# Wordle Application Project 
# Overview 

This project is a Java implementation of the game Wordle. The game challenges the player to guess a secret word within six attempts. After each guess, the game provides feedback indicating which letters are in the correct position (green), which letters are in the word but in the wrong position (yellow), and which letters are not in the word (gray). Utilizing a decrease and conquer algorithm, the game effectively narrows down potential solutions based on the player's guesses, ensuring accurate tracking of letter usage for both duplicate and unique letters. The project features a graphical user interface (GUI) for an engaging user experience.


# Features 
* **Graphical User Interface (GUI)**: the interface allows player to interact with the game visually, displaying feedback for guesses with colored boxes (Green, Yellow, and Gray). 
* **6 attempts:** users have up to 6 chances to guess the correct word.
* **Custom Dictionaries:** game supports external dictionary files, enable users to load custom word lists.
* **Feedback system:** Feedback system is provided using:
  * **Green (G):** Correct letter in the correct position
  * **Yellow (Y):** Correct letter in the wrong position
  * **Gray (N):** letter is not in the word.
* **Random Word Selection:** secret word is chosen randomly from the dictionary, making each game unique

# Getting Started 
## Prerequistes: 
* Java Development Kit (JDK) version 1.8 or later
* Eclipse IDE (or any preferred Java IDE).
## Installation 
1. Clone the repository to your local machine:
   
`git@github.com:tinatrinhh/WordleApp.git`

3. Open the project in Eclipse or any Java IDE and select File > Open Projects from File System. 
4. Navigate to the directory where you cloned the repository and select it to import the project.
5. Run the WordleApp.java file to start the game. You can do this by right-clicking the file in the Project Explorer and selecting Run As > Java Application.
6. The GUI will launch, and you can begin playing the game.


# Project Structure:
* **Wordle.java**: Contains the main game logic, including validating guesses, providing feedback (green, yellow, gray boxes), and handling duplicate letters.
- **WordleDriver.java**: Provides a simple text-based interface to play Wordle in the console.
- **WordleGUI.java**: Implements a graphical user interface to play Wordle using Swing, with colored boxes representing the correctness of each guess.
- **wordle.txt**: A file containing a list of valid words that can be used for guessing in the game.

# Ackowledgements
I would like to thank Dr. Baker for their guidance and support throughout the project. Their feedback was invaluable in enhancing my understanding of the concepts covered in class.
