package TicTacToe;
import java.util.concurrent.*;

public class MiniMax {
    // initialize bestMove to a known value to make debug easier
    private static volatile String bestMove = "00";
    private static volatile int bestValue = -1000;

    public static String pickBestMove(Board board) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            findBestMove(board);
            return bestMove;
        });

        try {
            // Wait for 5 seconds to get the best move
            return future.get(5, TimeUnit.SECONDS);
        } 
        catch (TimeoutException e) {
            System.out.println("Time limit exceeded. Using the best move found so far.");
            // Return the best move found so far within the time limit
        } 
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } 
        finally {
            executor.shutdown();
        }

        System.out.println("Best move returned: " + bestMove); 
        return bestMove;
    }


    public static String findBestMove(Board board) { // min max function, without alpha beta pruning yet
        int[][] boardState = board.getBoard();
        bestValue = -1000;
        bestMove = "00";

        for (int row = 0; row < boardState.length; row++) { // iterate through all tiles
            for (int col = 0; col < boardState[row].length; col++) {
                if (boardState[row][col] == 0) { // if the space is empty
                    boardState[row][col] = 2; // ai will "evaluate" this move

                    // create a copy of the board
                    int moveScore = miniMax(new Board(boardState), 0, false, -1000, 1000); // return score for possible ai move

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

                        // Debugging statement to print the board state
                        System.out.println("Minimax (Max): Depth: " + depth + ", Move: " + row + col);
                        printBoardState(boardState);

                        best = Math.max(best, miniMax(new Board(boardState), depth + 1, !isMaxTurn, alpha, beta));                        
                        boardState[row][col] = 0;
                        alpha = Math.max(alpha, best);

                        if (beta <= alpha) {
                            break;
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

                        // Debugging statement to print the board state
                        System.out.println("Minimax (Min): Depth: " + depth + ", Move: " + row + col);
                        printBoardState(boardState);              

                        best = Math.min(best, miniMax(new Board(boardState), depth + 1, !isMaxTurn, alpha, beta));
                        boardState[row][col] = 0; // undo the human move to not chance the actual board
                        beta = Math.min(beta, best);

                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return best;
        }
    }

    private static void printBoardState(int[][] boardState) {
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                System.out.print(boardState[i][j] + " ");
            }
            System.out.println();
        }
    }

}
