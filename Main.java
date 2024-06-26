import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final int EMPTY = 0;
    public static final int PLAYER = 1;
    public static final int COMPUTER = 2;
    private static List<String> moves = new ArrayList<>();
    public static void main(String[] args) {
        // TODO: finish main game loop
        Board game = new Board();
        playGame(game);
    }

    public static void playGame(Board game) {
        Scanner kb = new Scanner(System.in);
        boolean gameOver = false;
        int turn = 0;
        
        while (!gameOver) {
            game.printBoard();
            if (turn == 0) {
                // human goes first
                System.out.print("Enter board location: ");
                String in = kb.nextLine();
                // place piece on board
                if (game.isValidSpot(in)) {
                    game.placePiece(in, PLAYER);
                    moves.add(in.toLowerCase());
                    if(game.isWinCon(PLAYER)) {
                        System.out.println("You win!");
                        gameOver = true;
                    }
                }
            }
            else {
                // computer's turn
            }
        }
    }
}