public class Main {
    public static void main(String[] args) {
        State state = new State();
        LegalMoves moves = new LegalMoves();

        // testing adding a random piece
        state.clearAllPieces();
        state.setPieceAt(3,3, Piece.RM);
        state.setPieceAt(4,4, Piece.WM);
        state.setPieceAt(4,2, Piece.WM);
        state.setPieceAt(6, 4, Piece.WM);
        state.setPieceAt(6, 6, Piece.WM);

        state.printBoard();
//        Board.printTileIndexes();

        /**
         * before my dumbass forgets, i have it set so that (0,0) is bottom left, (0,7) is bottom right
         * it's still (row, col)
         *
         * Board.printTileIndexes();
         */

        Player player = Player.RED;

        moves.setLegalMoves(state, player);

        System.out.println("Valid moves for player " + player);

        printLeafs(state);

    }

    static void printLeafs(State state) {
        if (state.getChildren().size() == 0) {
            state.printBoard();
        }

        else {
            for (int i = 0; i < state.getChildren().size(); i++) {
                printLeafs(state.getChildren().get(i));
            }
        }
    }
}
