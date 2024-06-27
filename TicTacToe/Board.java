package TicTacToe;

import java.util.Arrays;

public class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    // empty spaces are marked 0 on the board array
    public static final int EMPTY = 0;
    // the player's spaces are marked 1 on the board array
    public static final int HUMAN = 1;
    // the computer's spaces are marked 2 on the board array
    public static final int COMPUTER = 2;

    private int[][] board = new int[ROWS][COLUMNS];

    public static void main(String[] args) {
        Board board = new Board();

        board.placeMove("H4", HUMAN);
        board.placeMove("H5", HUMAN);
        board.placeMove("H6", HUMAN);
        board.placeMove("Z7", HUMAN);

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
        // initialize the game board with 0s denoting empty spaces
        for (int[] row : board) {
            Arrays.fill(row, EMPTY);
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

    private boolean isValidSpot(String coords, int row, int column) {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            System.out.println("Invalid location: " + coords);
            return false;
        }

        return board[row][column] == EMPTY;
    }

    public boolean placeMove(String coords, int player) { // checks if coords are out of bounds or spot is taken
        coords = coords.toLowerCase();
        char row = coords.charAt(0);
        char col = coords.charAt(1);
        // get the integer value of the char (i.e. a = 0, b = 1, etc.)
        int r = row - 'a';
        int c = col - '0';

        if (isValidSpot(coords, r, c)) {
            board[r][c] = player;
            return true; // move made successfully
        }

        return false; // move not valid

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
