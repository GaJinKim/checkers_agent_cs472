import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        State state = new State();
        Scanner scan = new Scanner(System.in);

        // testing adding a random piece
//        state.clearAllPieces();
//        state.setPieceAt(3,3, Piece.RK);
//        state.setPieceAt(4,4, Piece.WM);
//        state.setPieceAt(4,2, Piece.WM);
//        state.setPieceAt(6, 2, Piece.WM);
//        state.setPieceAt(6, 4, Piece.WM);
//        state.setPieceAt(4, 6, Piece.WM);
//        state.setPieceAt(3,1,Piece.WM);
//        state.setPieceAt(6,2,Piece.E);

//        state.setPieceAt(2,2, Piece.WM);
//        state.setPieceAt(1,3, Piece.RM);
//        state.setPieceAt(1,5, Piece.RM);

        System.out.println("[Initial Board State]");
        state.printBoard();

        /**
         * before my dumbass forgets, i have it set so that (0,0) is bottom left, (0,7) is bottom right
         * it's still (row, col)
         *
         * Board.printTileIndexes();
         */
        while (true) {
//            System.out.print("\n[Your Move]");
            LegalMoves.setLegalMoves(state, Player.RED);

            if (endDetected(state))
                break;
            state.refreshLeaves();
            state.updateLeaves(state);

            ///////
            for (int i = 0; i < state.getLeaves().size(); i++) {
                System.out.println("\nType \"" + i + "\" to move:");
                state.getLeaves().get(i).printBoardMini();
            }

            int decision = scan.nextInt();
            if (decision <  0 || decision >= state.getLeaves().size()) {
                System.out.println("Invalid move, please enter again: ");
                decision = scan.nextInt();
            }
            state = new State(state.getLeaves().get(decision));
            ///////
//            state = new State(state.getLeaves().get(getValidRandomInd(state.getChildren())), Player.RED);

            System.out.println("\n[White's Move]");
            LegalMoves.setLegalMoves(state, Player.WHITE);
            if (endDetected(state))
                break;
            state.refreshLeaves();
            state.updateLeaves(state);

            // Determine move (pick state) based on alpha beta min max
            state = new State(state.getLeaves().get(getValidRandomInd(state.getChildren())), Player.WHITE);
            state.printBoard();
        }
    }

    /**
     * retrieves a valid random index for a given array list
     * @return random valid index
     */
    static int getValidRandomInd(ArrayList<State> stateArrayList) {
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

        if (redWin)
            System.out.println("Game Over: Red Wins");
        else if (whiteWin)
            System.out.println("Game Over: White Wins");
        else if (tie)
            System.out.println("Game Over: Stalemate");

        return redWin || whiteWin || tie;
    }
}
