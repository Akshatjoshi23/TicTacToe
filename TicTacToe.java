import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Main class for the Tic-Tac-Toe game
public class TicTacToe {
    // Window dimensions
    int boardWidth = 600;
    int boardHeight = 650; // 50px reserved for the text panel on top

    // Main GUI components
    JFrame frame = new JFrame("Tic-Tac-Toe"); // Game window
    JLabel textLabel = new JLabel(); // Label for showing the current status or winner
    JPanel textPanel = new JPanel(); // Panel for holding the label
    JPanel boardPanel = new JPanel(); // Panel for the 3x3 grid of buttons

    // Game logic variables
    JButton[][] board = new JButton[3][3]; // 3x3 board of buttons
    String playerX = "X"; // Represents player X
    String playerO = "O"; // Represents player O
    String currentPlayer = playerX; // Tracks the current player's turn

    boolean gameOver = false; // Becomes true when the game ends
    int turns = 0; // Tracks the number of moves made (max 9)

    // Constructor: Sets up the game window and board
    TicTacToe() {
        // Configure the game window
        frame.setVisible(true); // Makes the window visible
        frame.setSize(boardWidth, boardHeight); // Sets window size
        frame.setLocationRelativeTo(null); // Centers the window on the screen
        frame.setResizable(false); // Prevents resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the program on exit
        frame.setLayout(new BorderLayout()); // Uses a BorderLayout to organize components

        // Configure the text label at the top
        textLabel.setBackground(Color.darkGray); // Dark gray background
        textLabel.setForeground(Color.white); // White text
        textLabel.setFont(new Font("Arial", Font.BOLD, 50)); // Bold font with size 50
        textLabel.setHorizontalAlignment(JLabel.CENTER); // Center-aligns the text
        textLabel.setText("Tic-Tac-Toe"); // Initial label text
        textLabel.setOpaque(true); // Makes the background color visible

        // Add the label to the text panel and place it at the top of the window
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Configure the game board panel
        boardPanel.setLayout(new GridLayout(3, 3)); // Creates a 3x3 grid
        boardPanel.setBackground(Color.darkGray); // Dark gray background
        frame.add(boardPanel); // Add the panel to the center of the window

        // Create buttons for the 3x3 grid
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton(); // Create a button
                board[r][c] = tile; // Store the button in the board array
                boardPanel.add(tile); // Add the button to the board panel

                // Style each button
                tile.setBackground(Color.darkGray); // Dark gray background
                tile.setForeground(Color.white); // White text
                tile.setFont(new Font("Arial", Font.BOLD, 120)); // Bold font with size 120
                tile.setFocusable(false); // Removes focus highlight

                // Add functionality to the button (on click)
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return; // If the game is over, do nothing
                        JButton tile = (JButton) e.getSource(); // Get the clicked button
                        if (tile.getText() == "") { // If the button is empty
                            tile.setText(currentPlayer); // Set the current player's symbol
                            turns++; // Increment the turn count
                            checkWinner(); // Check if there's a winner
                            if (!gameOver) { // If the game is still ongoing
                                // Switch to the next player
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn."); // Update the label
                            }
                        }
                    }
                });
            }
        }
    }

    // Method to check for a winner or a tie
    void checkWinner() {
        // Check rows for a winner
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue; // Skip empty rows

            // If all 3 buttons in a row have the same symbol
            if (board[r][0].getText() == board[r][1].getText() &&
                board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]); // Highlight the winning buttons
                }
                gameOver = true; // End the game
                return;
            }
        }

        // Check columns for a winner
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue; // Skip empty columns

            // If all 3 buttons in a column have the same symbol
            if (board[0][c].getText() == board[1][c].getText() &&
                board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]); // Highlight the winning buttons
                }
                gameOver = true; // End the game
                return;
            }
        }

        // Check the main diagonal (top-left to bottom-right)
        if (board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]); // Highlight the winning buttons
            }
            gameOver = true; // End the game
            return;
        }

        // Check the anti-diagonal (top-right to bottom-left)
        if (board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true; // End the game
            return;
        }

        // Check for a tie (all buttons filled, no winner)
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]); // Highlight all buttons
                }
            }
            gameOver = true; // End the game
        }
    }

    // Method to handle winning buttons
    void setWinner(JButton tile) {
        tile.setForeground(Color.green); // Set text color to green
        tile.setBackground(Color.gray); // Set background to gray
        textLabel.setText(currentPlayer + " is the winner!"); // Display winner message
    }

    // Method to handle a tie
    void setTie(JButton tile) {
        tile.setForeground(Color.orange); // Set text color to orange
        tile.setBackground(Color.gray); // Set background to gray
        textLabel.setText("Tie!"); // Display tie message
    }
}
