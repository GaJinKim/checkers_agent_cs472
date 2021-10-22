import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        State state = new State();
        Scanner scan = new Scanner(System.in);
        LegalMoves legalMoves = new LegalMoves();
        Search search = new Search();

        // testing adding a random piece
        state.clearAllPieces();
        state.setPieceAt(4,0, Piece.RM);
        state.setPieceAt(7,1, Piece.WM);

//        state.setPieceAt(2,0, Piece.WM);
//        state.setPieceAt(6, 2, Piece.WM);
//        state.setPieceAt(6, 4, Piece.WM);
//        state.setPieceAt(2,4, Piece.WM);
//        state.setPieceAt(2,6, Piece.WM);
//        state.setPieceAt(4,6, Piece.WM);

//        System.out.println("[How to play]\nAt each state, you will be asked to pick between a list of legal moves.");
//        System.out.print("\n[You are first to act (red)]\nType \"start\" to begin: ");
//        scan.next();

        System.out.println("\n[Initial Board State]");
        state.printBoard();

        State curState = new State();
        while (true) {
            // Red's Turn
            state.setPlayer(Player.RED);
            System.out.println("\n[Red Player's Legal Moves]");
            ArrayList<Piece[][]> redLegalMoves = legalMoves.setLegalMoves(state);

            if (redLegalMoves.isEmpty()) {
                System.out.println("Red has no legal moves!");
                break;
            }

            for (Piece[][] a : redLegalMoves) {
                curState = new State(a);
                curState.setPlayer(Player.RED);
            }

            // TODO: just print out the last state in redLegalMoves for now
            System.out.println("Red's selected move");
//            state = new State(curState);
            state = new State(redLegalMoves.get(new Random().nextInt(redLegalMoves.size())));
            state.printBoard();

            redLegalMoves.clear();

            // White's Turn
            state.setPlayer(Player.WHITE);
            System.out.println("\n[White Player's Legal Moves]");
            ArrayList<Piece[][]> whiteLegalMoves = legalMoves.setLegalMoves(state);

            if (whiteLegalMoves.isEmpty()) {
                System.out.println("White has no legal moves!");
                break;
            }

            ArrayList<State> bestPossibleMoves = search.alphaBetaSearch(state);
            System.out.println("White's selected move");
            state = bestPossibleMoves.get(randomValidIndex(bestPossibleMoves));
            state.printBoard();

            whiteLegalMoves.clear();


//            state.refreshLeaves();
//            state.updateLeaves(state);

//            for (int i = 0; i < state.getLeaves().size(); i++) {
//                System.out.println("\nType \"" + i + "\" to move:");
//                state.getLeaves().get(i).printBoardMini();
//            }
//            int decision = scan.nextInt();
//            if (decision <  0 || decision >= state.getLeaves().size()) {
//                System.out.println("Invalid move, please enter again: ");
//                decision = scan.nextInt();
//            }
//            state = new State(state.getLeaves().get(decision));

//            state = new State(state.getLeaves().get(getValidRandomInd(state.getChildren())));
//            state.printBoard();
        }
    }

    /**
     * retrieves a valid random index for a given array list
     * @return random valid index
     */
    static int randomValidIndex(ArrayList<State> stateArrayList) {
        return new Random().nextInt(stateArrayList.size());
    }

    /**
     * end conditions:
     *  1. game is over (with victor) if either number of pieces equals 0
     *  2. game is over (tie) if current player has no legal moves (state.getChildren().size equals 0)
     *
     * @param state to be checked for end conditions
     * @return true if is terminal state, false otherwise
     */
    static boolean endDetected(State state) {
        boolean redWin = state.getNumOfWhitePieces() == 0;
        boolean whiteWin = state.getNumOfRedPieces() == 0;
        boolean tie = state.getChildren().size() == 0;

        if (redWin || whiteWin || tie) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n[Final Board State]");
            state.printBoard();
        }
        if (redWin)
            System.out.println("Game Over: Red Wins");
        else if (whiteWin)
            System.out.println("Game Over: White Wins");
        else if (tie)
            System.out.println("Game Over: Stalemate");

        return redWin || whiteWin || tie;
    }
}
