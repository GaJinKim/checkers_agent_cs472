import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static boolean gameOver = false;

    /**
     * difficulty (depth):
     *  0 - picks a random move
     *  1 - looks one move ahead
     *  3 - looks three moves ahead
     */
    static int redAIDifficulty = 3; // max depth that red AI will search
    static int whiteAIDifficulty = 3; // max depth that white AI will search

    public static void main(String[] args) {
        State state = new State();
        Scanner scan = new Scanner(System.in);
        LegalMoves legalMoves = new LegalMoves();

        // introduction and instructions
        System.out.println("\n[How to play]\nYou will be asked to pick between a list of legal moves.\nYou will be player RED (first to move).");
        System.out.println("\n[Please enter AI's difficulty/depth to begin]");
        System.out.println("0: AI makes random legal move\n1: AI looks 1 move ahead\n3: AI looks 3 moves ahead\n5: AI looks 5 moves ahead\n>5: not recommended as some states may take longer to load");
        System.out.print("\nChoice? ");
        whiteAIDifficulty = scan.nextInt();

        // print initial board state
        System.out.println("\n[Initial Board State]");
        state.printBoard();

        while (!gameOver) {
            // Red's Turn
            state.setPlayer(Player.RED);
            state = humanRedControl(state, scan, legalMoves);
//            state = aiRedControl(state, legalMoves, redAIDifficulty);

            if (gameOver) break;

            // White's Turn
            state.setPlayer(Player.WHITE);
            ArrayList<Piece[][]> whiteLegalMoves = legalMoves.setLegalMoves(state);

            if (endDetected(state, whiteLegalMoves)) {
                gameOver = true;
                break;
            }

            System.out.println("\n[Player White's Legal Moves]");

            // retrieves reachable states
            state.refreshLeaves(whiteLegalMoves);
            System.out.println(state.getPlayer() + " has " + state.getLeaves().size() + " viable moves");
//            state.printLeaves(); // prints board

            state = new State(new Search(whiteAIDifficulty).alphaBetaSearch(state.getLeaves()));
            System.out.println("Resulting Board (now RED's (your) turn)");
            state.printBoard();
            whiteLegalMoves.clear();
        }
    }

    /**
     * ai controlled by red
     *
     * @param state current
     * @param legalMoves possible moves from which the ai can choose
     * @param depth at which the AI will search
     *
     * @return optimally selected state
     */
    static State aiRedControl(State state, LegalMoves legalMoves, int depth) {
        ArrayList<Piece[][]> redLegalMoves = legalMoves.setLegalMoves(state);
        if (endDetected(state, redLegalMoves)) {
            gameOver = true;
            return null;
        }

        state.refreshLeaves(redLegalMoves);
        System.out.println(state.getPlayer() + " has " + state.getLeaves().size() + " viable moves");

        state = new State(new Search(depth).alphaBetaSearch(state.getLeaves()));
        System.out.println("Resulting Board (now WHITE's turn)");
        state.printBoard();
        redLegalMoves.clear();
        return state;
    }

    /**
     * player control red
     */
    static State humanRedControl(State state, Scanner scan, LegalMoves legalMoves) {
        ArrayList<Piece[][]> redLegalMoves = legalMoves.setLegalMoves(state);
        if (endDetected(state, redLegalMoves)) {
            gameOver = true;
            return null;
        }

        System.out.print("\n[Player Red's Legal Moves]");
        for (int i = 0; i < redLegalMoves.size(); i++) {
            System.out.println("\nType \"" + i + "\" to move:");
            new State(redLegalMoves.get(i)).printBoardMini();
        }

        System.out.print("\nYour Move? ");
        int decision = scan.nextInt();
        if (decision <  0 || decision >= redLegalMoves.size()) {
            System.out.print("Invalid move, please enter new move: ");
            decision = scan.nextInt();
        }
        state = new State(redLegalMoves.get(decision));
        System.out.println("Resulting Board (now WHITE's turn)");
        state.printBoard();

        redLegalMoves.clear();
        return state;
    }

    /**
     * end conditions:
     *  1. game is over (with victor) if either number of pieces equals 0
     *  2. game is over (tie) if current player has no legal moves (state.getChildren().size equals 0)
     *
     * @param state to be checked for end conditions
     * @return true if is terminal state, false otherwise
     */
    static boolean endDetected(State state, ArrayList<Piece[][]> legalMoves) {
        boolean redWin = state.getNumOfWhitePieces() == 0;
        boolean whiteWin = state.getNumOfRedPieces() == 0;
        boolean tie = legalMoves.size() == 0;

        if (redWin || whiteWin || tie) {
            gameOver = true;
            System.out.println("\n[Final Board State]");
            state.printBoard();
        }

        if (redWin)
            System.out.println("Game Over: Red Wins!");
        else if (whiteWin)
            System.out.println("Game Over: White Wins!");
        else if (tie)
            System.out.println("Game Over: Stalemate (No valid moves)");

        return redWin || whiteWin || tie;
    }

    /**
     * sets initial board state to a fixed checkers pattern (puzzle)
     *
     * @param state
     */
    static void setPuzzle(State state) {
        state.clearAllPieces();
        state.setPieceAt(2,0, Piece.RM);
        state.setPieceAt(3,1, Piece.RM);
        state.setPieceAt(1,1, Piece.RM);
        state.setPieceAt(0,2, Piece.RM);
        state.setPieceAt(1,3, Piece.RM);
        state.setPieceAt(1,5, Piece.RM);
        state.setPieceAt(5,5, Piece.RM);
        state.setPieceAt(0,6, Piece.RM);
        state.setPieceAt(4,6, Piece.RM);
        state.setPieceAt(1,7, Piece.RM);
        state.setPieceAt(3,7, Piece.RM);

        state.setPieceAt(6, 0, Piece.WM);
        state.setPieceAt(5, 1, Piece.WM);
        state.setPieceAt(4, 2, Piece.WM);
        state.setPieceAt(3, 3, Piece.WM);
        state.setPieceAt(5, 3, Piece.WM);
        state.setPieceAt(7, 3, Piece.WM);
        state.setPieceAt(5, 3, Piece.WM);
        state.setPieceAt(4, 4, Piece.WM);
        state.setPieceAt(3, 5, Piece.WM);
        state.setPieceAt(7, 5, Piece.WM);
        state.setPieceAt(5, 7, Piece.WM);
        state.setPieceAt(7, 7, Piece.WM);
    }
}
