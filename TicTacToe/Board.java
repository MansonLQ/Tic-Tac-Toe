package TicTacToe;

import java.util.Arrays;

public class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    public static final int EMPTY = 0;
    public static final int HUMAN = 1;
    public static final int COMPUTER = 2;

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
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isValidSpot(int row, int column) {
        if (row < 0 || row > 7 || column < 0 || column > 7 || board[row][column] != EMPTY) {
            // System.out.println("Invalid location: (" + row + ", " + column + "):
            // Board.java");
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

        // "Moved failed to be place: Board.java
        return false;
    }

    public boolean undoMove(int row, int column) { // coords both ints
        if (board[row][column] != 0) {
            board[row][column] = 0;
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

    public int checkWinner() { // returns 1 if human won, 2 if ai won, 0 if no winner
        // check horizontally
        for (int col = 0; col < COLUMNS - 3; col++) {
            for (int row = 0; row < ROWS; row++) {
                int player = board[row][col]; // check which player it is checking for winning state

                if (board[row][col] == player && board[row][col + 1] == player && board[row][col + 2] == player &&
                        board[row][col + 3] == player && player != EMPTY) {
                    return player;
                }
            }
        }
        // check vertically
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                int player = board[row][col];

                if (board[row][col] == player && board[row + 1][col] == player && board[row + 2][col] == player &&
                        board[row + 3][col] == player && player != EMPTY) {
                    return player;
                }
            }
        }

        return 0; // no one has won yet
    }

}
