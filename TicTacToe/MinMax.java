package TicTacToe;

public class MinMax {

    public static void minMax(Board board, int depth, boolean isMax) {
        boolean isWon = board.isWinCon(depth); // tweak win con to check if the whole state is winCon, no parameters

    }

    public static void pickBestMove(Board state) { // min max function, without alpha beta pruning yet
        int[][] board = state.getBoard();
        int bestValue = -1000;
        int[] bestPlacement = new int[] { -1, -1 };

        for (int i = 0; i < board.length; i++) { // iterate through all tiles
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) { // if the space is empty
                    board[i][j] = 2; // ai will "evaluate" this move

                    int score = minMax(board, 0, true); // return score for possible ai move

                    board[i][j] = 0; // undo the ai move so it doesnt change the actual board

                    if (score > bestValue) {
                        bestValue = score;

                        // keep track of what placement will get best score
                        bestPlacement[0] = i;
                        bestPlacement[1] = j;

                    }
                }
            }
        }

        board[bestPlacement[0]][bestPlacement[1]] = 2; // ai will finalize its move
    }
}
