import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * DO NOT TOUCH
 * 
 * This class provides a simple interface for playing the game of Hangman.
 * 
 * @author cmb79981
 *
 */
public class WordleGUI {

	private JFrame frame;
	private Wordle game;
	private JLabel message;
	private JLabel redacted;
	private JLabel[][] boxes = new JLabel[0][0];
	private JTextPane textPane;
	private JButton submit;
	private int currRow;
	private static final int BOXES_AREA = 500;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordleGUI window = new WordleGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WordleGUI() {
		this.game = new Wordle("wordle.txt");
	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(750, 250 + BOXES_AREA);
		// frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		message = new JLabel("Current State of the Word", SwingConstants.CENTER);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setBounds(50, 0, 650, 100);
		frame.getContentPane().add(message);

		// redacted = new JLabel("New label");
		// redacted.setHorizontalAlignment(SwingConstants.CENTER);
		// redacted.setBounds(50, 50, 183, 46);
		// frame.getContentPane().add(redacted);

		textPane = new JTextPane();
		textPane.setBounds(200, 100 + BOXES_AREA, 350, 30);
		textPane.getAccessibleContext().setAccessibleDescription("Please type your guess");
		frame.getContentPane().add(textPane);

		submit = new JButton("Submit Guess");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.gameOver()) {
					reset();
				} else {
					guess();
				}
			}
		});
		submit.setBounds(250, 150 + BOXES_AREA, 250, 50);
		frame.getContentPane().add(submit);

		reset();
	}

	/**
	 * Creates the empty boxes based on the word selected
	 */
	public void createBoxes() {
		int len = this.game.getCorrectWord().length();
		int max = Math.max(len, 6);
		int boxWidth = (BOXES_AREA - (10 * (max + 1))) / max;
		int startX = (750 - (boxWidth * len + 10 * (len + 1))) / 2;
		int y = 100;
		this.boxes = new JLabel[6][len];
		for (int row = 0; row < 6; row++) {
			int x = startX;
			for (int col = 0; col < len; col++) {
				JLabel temp = new JLabel(" ", SwingConstants.CENTER);
				temp.setBounds(x, y, boxWidth, boxWidth);
				temp.setBackground(Color.white);
				temp.setOpaque(true);
				temp.setFont(new Font("SansSerif", Font.PLAIN, boxWidth - 5));
				frame.add(temp);
				x += boxWidth + 10;
				this.boxes[row][col] = temp;
			}
			y += boxWidth + 10;
		}
	}

	/**
	 * Clears the old boxes
	 */
	public void removeBoxes() {
		for (int row = 0; row < this.boxes.length; row++) {
			for (int col = 0; col < this.boxes[row].length; col++) {
				this.frame.remove(this.boxes[row][col]);
			}
		}
		this.frame.revalidate();
		this.frame.repaint();
	}

	/**
	 * Starts a new game of hangman
	 */
	public void reset() {
		this.game.reset();
		message.setFont(new Font("SansSerif", Font.PLAIN, 36));
		message.setText("Wordle");
		// redacted.setText(game.getCorrectWord());
		submit.setText("Submit Guess");
		this.currRow = 0;
		this.removeBoxes();
		this.createBoxes();
	}

	/**
	 * Gets the guess from the textbox and updates the interface in response
	 */
	public void guess() {
		String next = textPane.getText().toUpperCase();
		textPane.setText("");
		boolean won = false;
		if (next.length() > 0) {
			String mess = game.makeGuess(next);
			if (mess.startsWith("Error")) {
				message.setText("Wordle\n" + mess);
			} else {
				message.setText("Wordle");
				if (mess.equals("G".repeat(mess.length()))) {
					won = true;
				}
				for (int i = 0; i < mess.length(); i++) {
					JLabel currBox = this.boxes[currRow][i];
					char currChar = mess.charAt(i);
					currBox.setText(next.charAt(i) + "");
					if (currChar == 'G') {
						currBox.setBackground(Color.GREEN);
						currBox.getAccessibleContext().setAccessibleDescription(currChar + " is green");
					} else if (currChar == 'Y') {
						currBox.setBackground(Color.YELLOW);
						currBox.getAccessibleContext().setAccessibleDescription(currChar + " is yellow");
					} else if (currChar == 'N') {
						currBox.setBackground(Color.LIGHT_GRAY);
						currBox.getAccessibleContext().setAccessibleDescription(currChar + " is gray");
					}
				}
				currRow++;
				textPane.requestFocus();
				if (game.gameOver()) {
					if (won) {
						message.setText("You Won!");
					} else {
						message.setText("Game Over! The word was " + game.getCorrectWord());
					}
					submit.setText("Play Again");

				}
			}
		}

	}

}
