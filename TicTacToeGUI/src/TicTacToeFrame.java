import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons;
    private char currentPlayer;
    private int moves;
    private boolean gameOver;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        buttons = new TicTacToeButton[3][3];
        currentPlayer = 'X';
        moves = 0;
        gameOver = false;

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToeButton clickedButton = (TicTacToeButton) e.getSource();
                int row = -1, col = -1;
                // Finding the clicked button's position
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (buttons[i][j] == clickedButton) {
                            row = i;
                            col = j;
                            break;
                        }
                    }
                }
                if (row != -1 && col != -1 && !gameOver && clickedButton.getState() == ' ') {
                    clickedButton.setState(currentPlayer);
                    moves++;
                    if (moves >= 5 && checkForWin(row, col)) {
                        JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                        gameOver = true;
                        promptPlayAgain();
                    } else if (moves >= 7 && checkForTie()) {
                        JOptionPane.showMessageDialog(null, "It's a tie!");
                        gameOver = true;
                        promptPlayAgain();
                    } else {
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    }
                }
            }
        };

        // Create buttons and add listener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeButton button = new TicTacToeButton();
                button.addActionListener(buttonListener);
                buttons[i][j] = button;
                add(button);
            }
        }

        setSize(300, 300);
        setVisible(true);
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setState(' ');
                buttons[i][j].setEnabled(true); // Re-enable buttons
            }
        }
        currentPlayer = 'X';
        moves = 0;
        gameOver = false;
    }

    private void promptPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private boolean checkForWin(int row, int col) {
        // Check row
        if (buttons[row][(col + 1) % 3].getState() == currentPlayer &&
                buttons[row][(col + 2) % 3].getState() == currentPlayer) {
            return true;
        }
        // Check column
        if (buttons[(row + 1) % 3][col].getState() == currentPlayer &&
                buttons[(row + 2) % 3][col].getState() == currentPlayer) {
            return true;
        }
        // Check diagonals
        if ((row == col && buttons[(row + 1) % 3][(col + 1) % 3].getState() == currentPlayer &&
                buttons[(row + 2) % 3][(col + 2) % 3].getState() == currentPlayer) ||
                (row + col == 2 && buttons[(row + 1) % 3][(col - 1 + 3) % 3].getState() == currentPlayer &&
                        buttons[(row + 2) % 3][(col - 2 + 3) % 3].getState() == currentPlayer)) {
            return true;
        }
        return false;
    }

    private boolean checkForTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getState() == ' ') {
                    return false; // There is an empty space, game can continue
                }
            }
        }
        return true; // No empty space, it's a tie
    }
}
