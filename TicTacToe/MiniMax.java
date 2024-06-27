package TicTacToe;

public class MiniMax {

    public static String pickBestMove(Board board) { // min max function, without alpha beta pruning yet
        int[][] boardState = board.getBoard();
        int bestValue = -1000;
        String bestMove = "";

        for (int row = 0; row < boardState.length; row++) { // iterate through all tiles
            for (int col = 0; col < boardState[row].length; col++) {
                if (boardState[row][col] == 0) { // if the space is empty
                    boardState[row][col] = 2; // ai will "evaluate" this move

                    int moveScore = miniMax(board, 0, false, -1000, 1000); // return score for possible ai move

                    boardState[row][col] = 0; // undo the ai move so it doesnt change the actual board

                    if (moveScore > bestValue) {
                        bestValue = moveScore;

                        // keep track of what placement will get best score
                        bestMove = "" + row + col;

                    }
                }
            }
        }

        return bestMove;
    }

    private static int evaluateBoardState(Board board) {
        int winner = board.checkWinner();

        if (winner == 2) { // AI winner
            return 1;
        } else if (winner == 1) { // human winner
            return -1;
        } else { // no winner yet
            return 0;
        }
    }

    public static int MAX_DEPTH = 5;

    private static int miniMax(Board board, int depth, boolean isMaxTurn, int alpha, int beta) {
        int score = evaluateBoardState(board);

        if (score == 1 || score == -1 || depth == MAX_DEPTH) { // AI win or human win
            return score;
        }

        if (!board.hasMovesLeft()) {
            return 0; // draw
        }

        int[][] boardState = board.getBoard();

        if (isMaxTurn) {
            int best = -1000;

            for (int row = 0; row < boardState.length; row++) {
                for (int col = 0; col < boardState[row].length; col++) {
                    if (boardState[row][col] == 0) {
                        boardState[row][col] = 2;
                        best = Math.max(best, miniMax(board, depth + 1, !isMaxTurn, alpha, beta));
                        boardState[row][col] = 0;
                        alpha = Math.max(alpha, best);

                        if (beta <= alpha) {
                            return best;
                        }
                    }
                }
            }
            return best;
        } else {
            int best = 1000;

            for (int row = 0; row < boardState.length; row++) {
                for (int col = 0; col < boardState[row].length; col++) {
                    if (boardState[row][col] == 0) {
                        boardState[row][col] = 1; // assume human will try to maximize its value
                        best = Math.min(best, miniMax(board, depth + 1, !isMaxTurn, alpha, beta));

                        boardState[row][col] = 0; // undo the human move to not chance the actual board
                        beta = Math.min(beta, best);

                        if (beta <= alpha) {
                            return best;
                        }
                    }
                }
            }
            return best;
        }
    }

}
