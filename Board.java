import java.util.Arrays;

public class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    // empty spaces are marked 0 on the board array
    public static final int EMPTY = 0;
    // the player's spaces are marked 1 on the board array
    public static final int PLAYER = 1;
    // the computer's spaces are marked 2 on the board array
    public static final int COMPUTER = 2;

    private int[][] board = new int[ROWS][COLUMNS];

    public Board() {
        // initialize the game board with 0s, denoting empty spaces
        for (int[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    // print out the game board
    public void printBoard() {
        for (int i = ROWS - 1; i >= 0; i--) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // check if the given spot on the board is empty
    public boolean isValidSpot(int col) {
        return board[ROWS - 1][col] == EMPTY;
    }

    // find the next open row given a column
    public int getNextOpenRow(int col) {
        for (int row = 0; row < ROWS; row++) {
            if(board[row][col] == EMPTY) {
                return row;
            }
        }
        // if nothing, return -1
        return -1;
    }

    // place a piece on the board
    public void placePiece(int row, int col, int piece) {
        board[row][col] = piece;
    }

    // check for win condition
    public boolean isWinCon(int piece) {
        // check horizontally
        for (int col = 0; col < COLUMNS - 3; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (board[row][col] == piece && board[row][col+1] == piece && board[row][col+2] == piece && 
                board[row][col+3] == piece) {
                    return true;
                }
            }
        }
        // check vertically
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] == piece && board[row+1][col] == piece && board[row+2][col] == piece && 
                board[row+3][col] == piece) {
                    return true;
                }
            }
        }
        // if no win cons found, return false
        return false;
    }

}
