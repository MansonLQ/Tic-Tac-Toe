package TicTacToe;

public class MiniMax {
    private static final int LOWEST = -1000;
    private static final int HIGHEST = 1000;
    private static final int MAX_DEPTH = 6;

    public static int[] findBestMove(Board board, long timeLimit) {
        return minimax(board, timeLimit, LOWEST, HIGHEST, true);
    }

    private static int[] minimax(Board board, long timeLimit, int alpha, int beta, boolean maximizingPlayer) {
        int[] bestMove = {-1, -1};
        int bestValue = maximizingPlayer ? LOWEST : HIGHEST;

        long startTime = System.currentTimeMillis();

        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLUMNS; col++) {
                if (board.getBoard()[row][col] == Board.EMPTY) {
                    board.placeMove(row, col, maximizingPlayer ? Board.COMPUTER : Board.HUMAN);

                    int moveValue = minimaxValue(board, timeLimit - (System.currentTimeMillis() - startTime), alpha, beta, 0, !maximizingPlayer);

                    board.undoMove(row, col);

                    System.out.println("Evaluated move: (" + row + ", " + col + "), Score: " + moveValue);

                    if (maximizingPlayer) {
                        if (moveValue > bestValue) {
                            bestValue = moveValue;
                            bestMove[0] = row;
                            bestMove[1] = col;
                        }
                        alpha = Math.max(alpha, moveValue);
                    } else {
                        if (moveValue < bestValue) {
                            bestValue = moveValue;
                            bestMove[0] = row;
                            bestMove[1] = col;
                        }
                        beta = Math.min(beta, moveValue);
                    }

                    if (beta <= alpha || System.currentTimeMillis() - startTime >= timeLimit) {
                        break;
                    }
                }
            }
        }

        System.out.println("Best move found: (" + bestMove[0] + ", " + bestMove[1] + "), Best Score: " + bestValue);
        return bestMove;
    }

    private static int minimaxValue(Board board, long timeLimit, int alpha, int beta, int depth, boolean maximizingPlayer) {
        int score = board.evaluateBoard();
        if (score == 10 - depth || score == depth - 10 || depth == MAX_DEPTH || board.emptySpaces() == 0) {
            return score;
        }

        if (System.currentTimeMillis() - timeLimit >= timeLimit) {
            return score;
        }

        if (maximizingPlayer) {
            int bestValue = LOWEST;

            for (int row = 0; row < Board.ROWS; row++) {
                for (int col = 0; col < Board.COLUMNS; col++) {
                    if (board.getBoard()[row][col] == Board.EMPTY) {
                        board.placeMove(row, col, Board.COMPUTER);

                        int value = minimaxValue(board, timeLimit, alpha, beta, depth + 1, false);
                        bestValue = Math.max(bestValue, value);
                        alpha = Math.max(alpha, value);

                        board.undoMove(row, col);

                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestValue;
        } else {
            int bestValue = HIGHEST;

            for (int row = 0; row < Board.ROWS; row++) {
                for (int col = 0; col < Board.COLUMNS; col++) {
                    if (board.getBoard()[row][col] == Board.EMPTY) {
                        board.placeMove(row, col, Board.HUMAN);

                        int value = minimaxValue(board, timeLimit, alpha, beta, depth + 1, true);
                        bestValue = Math.min(bestValue, value);
                        beta = Math.min(beta, value);

                        board.undoMove(row, col);

                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestValue;
        }
    }
}
