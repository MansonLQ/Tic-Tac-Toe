package TicTacToe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final int EMPTY = 0;
    public static final int PLAYER = 1;
    public static final int COMPUTER = 2;
    private static List<String> moves = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize the game board and start the game
        Board board = new Board();
        playGame(board);
    }

    public static void playGame(Board board) {
        Scanner kb = new Scanner(System.in);
        boolean gameOver = false;
        int turn = 0;

        while (!gameOver) {
            board.displayBoard();
            if (turn % 2 == 0) {
                // Human's turn
                boolean validMove = false;
                while (!validMove) {
                    System.out.print("Enter board location: ");
                    String move = kb.nextLine();
                    validMove = board.placeMove(move, PLAYER);
                    if (!validMove) {
                        System.out.println("Invalid move, try again.");
                    } else {
                        // Player move was valid, continue
                        moves.add("Player: " + move.toLowerCase());
                        switch (board.checkWinner()) {
                            case 0:
                                break;
                            case 1:
                                System.out.println("You win!");
                                gameOver = true;
                                break;
                            case 2:
                                System.out.println("You lose!");
                                gameOver = true;
                                break;
                        }
                    }
                }
            } else {
                // Computer's turn
                System.out.println("Computer is thinking...");
                String bestMove = MiniMax.pickBestMove(board);
                int row = Character.getNumericValue(bestMove.charAt(0));
                int col = Character.getNumericValue(bestMove.charAt(1));
                board.placeMove("" + (char) ('a' + row) + (col + 1), COMPUTER);  // Fix the letter case for consistency
                moves.add("Computer: " + bestMove);
                switch (board.checkWinner()) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("You win!");
                        gameOver = true;
                        break;
                    case 2:
                        System.out.println("You lose!");
                        gameOver = true;
                        break;
                }
            }
            turn++;
        }
        kb.close();
    }
}
