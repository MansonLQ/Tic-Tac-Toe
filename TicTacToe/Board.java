package TicTacToe;

import java.util.Arrays;

public class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    public static final int EMPTY = 0;
    public static final int HUMAN = 1;
    public static final int COMPUTER = 2;
    public static final int WIN_CONDITION = 4;

    private int[][] board = new int[ROWS][COLUMNS];

    public static void main(String[] args) {
        int[][] test = {
                { 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
        };
        Board board = new Board(test);

        board.placeMove(0, 1, HUMAN);
        board.placeMove(0, 2, COMPUTER);
        board.placeMove(0, 3, HUMAN);

        board.undoMove(0, 3);

        board.displayBoard();

        switch (board.checkWinner()) {
            case 0:
                System.out.println("No winner yet");
                break;
            case 1:
                System.out.println("Human won");
                break;
            case 2:
                System.out.println("AI won");
                break;
        }

    }

    public Board() {
    }

    public Board(int[][] boardState) {
        for (int i = 0; i < ROWS; i++) {
            board[i] = Arrays.copyOf(boardState[i], COLUMNS);
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void displayBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] == EMPTY) {
                    System.out.print("- ");
                } else if (board[i][j] == COMPUTER) {
                    System.out.print("x ");
                } else if (board[i][j] == HUMAN) {
                    System.out.print("o ");
                }
            }
            System.out.println();
        }
    }
    

    private boolean isValidSpot(int row, int column) {
        if (row < 0 || row >= ROWS || column < 0 || column >= COLUMNS || board[row][column] != EMPTY) {
            System.out.println("Invalid location, try again.");
            return false;
        }

        return true; // spot is empty and within 2d array bounds
    }

    public boolean placeMove(int row, int column, int player) {
        if (isValidSpot(row, column)) {
            board[row][column] = player;
            return true;
        }

        return false;
    }

    public boolean undoMove(int row, int column) {
        if (board[row][column] != EMPTY) {
            board[row][column] = EMPTY;
            return true;
        }

        System.out.println("Space is already empty: Board.java");
        return false;
    }

    public int emptySpaces() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == EMPTY) {
                    count++;
                }
            }
        }
        return count;
    }

    // Check if the move results in a win
    public boolean isWinningMove(int row, int col, int player) {
        return countInDirection(row, col, player, 1, 0) >= WIN_CONDITION || countInDirection(row, col, player, 0, 1) >= WIN_CONDITION;
    }

    // Count the number of pieces in a given direction
    private int countInDirection(int row, int col, int player, int dRow, int dCol) {
        int count = 0;
        for (int i = -WIN_CONDITION + 1; i < WIN_CONDITION; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && board[r][c] == player) {
                count++;
                if (count == WIN_CONDITION) {
                    return count;
                }
            } else {
                count = 0;
            }
        }
        return count;
    }

    // Evaluate the board state
    public int evaluateBoard() {
        int score = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == HUMAN) {
                    score -= evaluatePosition(row, col, HUMAN); // Decrease score for player's pieces
                } else if (board[row][col] == COMPUTER) {
                    score += evaluatePosition(row, col, COMPUTER); // Increase score for computer's pieces
                }
            }
        }

        return score; // Return the evaluated score
    }

    // Evaluate the position for a given player
    private int evaluatePosition(int row, int col, int player) {
        int score = 0;
        score += countInDirection(row, col, player, 1, 0); // Count pieces in the row direction
        score += countInDirection(row, col, player, 0, 1); // Count pieces in the column direction
        return score;
    }

    public int checkWinner() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] != EMPTY && isWinningMove(row, col, board[row][col])) {
                    return board[row][col];
                }
            }
        }
        return 0; // no winner yet
    }
}
