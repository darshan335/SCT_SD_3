import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverGUI extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver - SkillCraft Technology");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font font = new Font("Arial", Font.BOLD, 20);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(font);
                gridPanel.add(cells[row][col]);
            }
        }

        JButton solveButton = new JButton("Solve Sudoku");
        solveButton.addActionListener(e -> {
            int[][] board = new int[SIZE][SIZE];
            try {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        String text = cells[i][j].getText().trim();
                        board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
                    }
                }

                if (solveSudoku(board)) {
                    for (int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++) {
                            cells[i][j].setText(String.valueOf(board[i][j]));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No solution exists!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Only numbers 1-9 are allowed.");
            }
        });

        add(gridPanel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num ||
                    board[3*(row/3) + x/3][3*(col/3) + x%3] == num)
                return false;
        }
        return true;
    }

    private boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board))
                                return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverGUI::new);
    }
}
