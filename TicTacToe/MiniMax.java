package TicTacToe;

public class MiniMax {
    private static final int LOWEST = -1000;
    private static final int HIGHEST = 1000;
    // public static final int MAX_DEPTH = 6;

    public static int[] findBestMove(Board board) {
        int bestScore = LOWEST;
        int[] bestMove = new int[] { -1, -1 };

        int[][] boardState = board.getBoard();

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = 0; column < Board.COLUMNS; column++) {

                if (boardState[row][column] == Board.EMPTY) {

                    board.placeMove(row, column, Board.COMPUTER);

                    int boardScore = miniMax(board, 0, false);
                    // int boardScore = miniMax(board, MAX_DEPTH, false);

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

    private static int miniMax(Board board, int depth, boolean isMaxTurn) {
        int score = evaluateBoard(board);

        System.out.println(depth + " " + score);

        if (score == 10 || score == -10 || depth == 0) {
            return score; // computer or human win
        }

        if (board.emptySpaces() == 0) {
            return score; // draw
        }

        if (isMaxTurn) {
            int bestScore = LOWEST;

            int[][] boardState = board.getBoard();

            for (int row = 0; row < Board.ROWS; row++) {
                for (int column = 0; column < Board.COLUMNS; column++) {

                    if (boardState[row][column] == Board.EMPTY) {

                        board.placeMove(row, column, Board.COMPUTER);

                        // bestScore = Math.max(bestScore, miniMax(board, depth - 1, !isMaxTurn));
                        bestScore = Math.max(bestScore, miniMax(board, depth + 1, !isMaxTurn));

                        board.undoMove(row, column);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = HIGHEST;

            int[][] boardState = board.getBoard();

            for (int row = 0; row < Board.ROWS; row++) {
                for (int column = 0; column < Board.COLUMNS; column++) {

                    if (boardState[row][column] == Board.EMPTY) {

                        board.placeMove(row, column, Board.HUMAN);

                        // bestScore = Math.min(bestScore, miniMax(board, depth - 1, !isMaxTurn));
                        bestScore = Math.min(bestScore, miniMax(board, depth + 1, !isMaxTurn));

                        board.undoMove(row, column);
                    }
                }
            }

            return bestScore;
        }
    }

}
