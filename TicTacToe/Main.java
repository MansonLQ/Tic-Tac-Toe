package TicTacToe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        startGame(board);
    }

    private static boolean isGameOver(Board board) {
        switch (board.checkWinner()) {
            case 0:
                return false;
            case 1:
                System.out.println("You win!");
                return true;
            case 2:
                System.out.println("You lose!");
                return true;
        }
        System.out.println("Check bug");
        return false;
    }

    public static void startGame(Board board) {
        Scanner scan = new Scanner(System.in);
        boolean gameOver = false;

        board.displayBoard();

        while (!gameOver) {

            // Human's turn
            boolean validMove = false;
            while (!validMove) {
                System.out.print("Enter board location: ");
                String humanMove = scan.nextLine();
                System.out.println();

                int row = Character.toLowerCase(humanMove.charAt(0)) - 'a';
                int column = Character.getNumericValue(humanMove.charAt(1));
                validMove = board.placeMove(row, column, Board.HUMAN);
            }

            gameOver = isGameOver(board);
            if (gameOver) {
                board.displayBoard();
                break;
            }

            // Computer's turn
            System.out.println("Computer's turn...\n");

            long endTime = System.currentTimeMillis() + 5000; // end time is 5 seconds later

            int depth = 1; // IDS start at depth 1
            int[] bestMove = new int[] { -1, -1 };
            while (System.currentTimeMillis() < endTime) {
                bestMove = MiniMax.alphaBetaPruning(board, depth, endTime);
                depth++;
            }

            int row = bestMove[0];
            int column = bestMove[1];

            String computerMove = "" + Character.toUpperCase((char) ('a' + row)) + (column);
            board.placeMove(row, column, Board.COMPUTER);

            board.displayBoard();
            System.out.println("Computer placed: " + computerMove);

            gameOver = isGameOver(board);

        }

        scan.close();
    }
}
