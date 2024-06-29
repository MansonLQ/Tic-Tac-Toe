package TicTacToe;

public class MiniMax {
    private static final int LOWEST = -1000;
    private static final int HIGHEST = 1000;
    // private static final int MAX_DEPTH = 6;

    private static int evaluateBoard(Board board) {
        int winner = board.checkWinner();

        if (winner == Board.COMPUTER) { // AI winner
            return 10;
        } else if (winner == Board.HUMAN) { // human winner
            return -10;
        } else { // no winner yet
            return 0;
        }
    }

    private static int max(Board board, int alpha, int beta, int depth, long endTime) {
        if (depth == 0 || System.currentTimeMillis() >= endTime) {
            return evaluateBoard(board);
        }

        int bestScore = LOWEST;
        int[][] boardState = board.getBoard();

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = 0; column < Board.COLUMNS; column++) {

                if (boardState[row][column] == Board.EMPTY) {

                    board.placeMove(row, column, Board.COMPUTER);

                    // bestScore = Math.min(bestScore, miniMax(board, depth - 1, !isMaxTurn));
                    bestScore = Math.max(bestScore, min(board, alpha, beta, depth - 1, endTime));

                    board.undoMove(row, column);

                    if (bestScore >= beta) {
                        return bestScore;
                    }
                    alpha = Math.max(alpha, bestScore);
                }
            }
        }
        return bestScore;
    }

    private static int min(Board board, int alpha, int beta, int depth, long endTime) {
        if (depth == 0 || System.currentTimeMillis() >= endTime) {
            return evaluateBoard(board);
        }

        int bestScore = HIGHEST;
        int[][] boardState = board.getBoard();

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = 0; column < Board.COLUMNS; column++) {

                if (boardState[row][column] == Board.EMPTY) {

                    board.placeMove(row, column, Board.HUMAN);

                    bestScore = Math.min(bestScore, max(board, alpha, beta, depth - 1, endTime));

                    board.undoMove(row, column);

                    if (bestScore <= alpha) {
                        return bestScore;
                    }
                    alpha = Math.min(beta, bestScore);
                }
            }
        }
        return bestScore;
    }

    public static int[] alphaBetaPruning(Board board, int depth, long endTime) {
        int[] bestMove = new int[] { -1, -1 };
        int bestScore = LOWEST;

        int[][] boardState = board.getBoard();

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = 0; column < Board.COLUMNS; column++) {

                if (boardState[row][column] == Board.EMPTY) {

                    board.placeMove(row, column, Board.COMPUTER);

                    int boardScore = min(board, LOWEST, HIGHEST, depth - 1, endTime);

                    board.undoMove(row, column);

                    if (boardScore > bestScore) { // boardScore
                        bestMove[0] = row;
                        bestMove[1] = column;

                        bestScore = boardScore;
                    }
                }
            }
        }
        return bestMove;
    }

    // public static int[] findBestMove(Board board) {
    // int bestScore = LOWEST;
    // int[] bestMove = new int[] { -1, -1 };

    // int[][] boardState = board.getBoard();

    // for (int row = 0; row < Board.ROWS; row++) {
    // for (int column = 0; column < Board.COLUMNS; column++) {

    // if (boardState[row][column] == Board.EMPTY) {

    // board.placeMove(row, column, Board.COMPUTER);

    // int boardScore = miniMax(board, 0, false);
    // // int boardScore = miniMax(board, MAX_DEPTH, false);

    // board.undoMove(row, column);

    // if (boardScore > bestScore) { // boardScore
    // bestMove[0] = row;
    // bestMove[1] = column;

    // bestScore = boardScore;
    // }
    // }
    // }
    // }
    // return bestMove;
    // }

    // private static int miniMax(Board board, int depth, boolean isMaxTurn) {
    // int score = evaluateBoard(board);

    // // System.out.println(depth + " " + score);
    // // System.out.println(board.emptySpaces());

    // if (score == 10 || score == -10) {
    // return score; // computer or human win
    // }

    // if (board.emptySpaces() == 0) {
    // return 0; // draw
    // }

    // if (depth >= MAX_DEPTH) {
    // return score; // limit depth to avoid infinite recursion
    // }

    // if (isMaxTurn) {
    // int bestScore = LOWEST;

    // int[][] boardState = board.getBoard();

    // for (int row = 0; row < Board.ROWS; row++) {
    // for (int column = 0; column < Board.COLUMNS; column++) {

    // if (boardState[row][column] == Board.EMPTY) {

    // board.placeMove(row, column, Board.COMPUTER);

    // // bestScore = Math.max(bestScore, miniMax(board, depth - 1, !isMaxTurn));
    // bestScore = Math.max(bestScore, miniMax(board, depth + 1, !isMaxTurn));

    // board.undoMove(row, column);
    // }
    // }
    // }

    // return bestScore;
    // } else {
    // int bestScore = HIGHEST;

    // int[][] boardState = board.getBoard();

    // for (int row = 0; row < Board.ROWS; row++) {
    // for (int column = 0; column < Board.COLUMNS; column++) {

    // if (boardState[row][column] == Board.EMPTY) {

    // board.placeMove(row, column, Board.HUMAN);

    // // bestScore = Math.min(bestScore, miniMax(board, depth - 1, !isMaxTurn));
    // bestScore = Math.min(bestScore, miniMax(board, depth + 1, !isMaxTurn));

    // board.undoMove(row, column);
    // }
    // }
    // }

    // return bestScore;
    // }
    // }
}