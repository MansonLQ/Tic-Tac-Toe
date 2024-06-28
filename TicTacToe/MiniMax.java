package TicTacToe;

public class MiniMax {

    public static String findBestMove(Board board) { // min max function, without alpha beta pruning yet
        int[][] boardState = board.getBoard();
        int bestValue = -1000;
        String bestMove = "INITIALIZED";

        for (int row = 0; row < boardState.length; row++) { // iterate through all tiles
            for (int col = 0; col < boardState[row].length; col++) {
                if (boardState[row][col] == 0) { // if the space is empty
                    boardState[row][col] = 2; // ai will "evaluate" this move

                    // create a copy of the board
                    int moveScore = miniMax(new Board(boardState), 0, false); // return score for possible
                                                                              // ai move

                    boardState[row][col] = 0; // undo the ai move so it doesnt change the actual board

                    // Debugging statement to print move and score
                    System.out.println("Evaluating move: " + row + col + " with score: " + moveScore);

                    if (moveScore > bestValue) {
                        bestValue = moveScore;

                        // keep track of what placement will get best score
                        bestMove = "" + row + col;

                    }
                }
            }
        }
        // Debugging statement to print best move and value
        System.out.println("Best move found: " + bestMove + " with value: " + bestValue);
        return bestMove;
    }

    private static int evaluateBoardState(Board board) {
        int winner = board.checkWinner();

        if (winner == 2) { // AI winner
            return 10;
        } else if (winner == 1) { // human winner
            return -10;
        } else { // no winner yet
            return 0;
        }
    }

    public static int MAX_DEPTH = 5;

    private static int miniMax(Board board, int depth, boolean isMaxTurn) {
        return 1;
    }

}
