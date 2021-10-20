import java.util.ArrayList;

public class LegalMoves {
    static ArrayList<Move> legalMoves = new ArrayList<Move>();
    static int maxCaptures = 0;

    /**
     * getters
     */
    int getMaxCaptures() {
        return maxCaptures;
    }

    static void setLegalMoves(State state, Player player) {
        resetLegalMoves();
        resetMaxCaptures();

        if (player.equals(Player.RED)) {
            // if there is a jump move, it is forced
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (state.getPieceAt(i, j).equals(Piece.RM) || state.getPieceAt(i, j).equals(Piece.RK))
                        addLegalJumpMovesRed(state, i, j, state.getPieceAt(i, j), 0);
                }
            }
            // if there are no jump moves (aka no captures), search for legal simple moves
            if (legalMoves.size() == 0)
                addLegalSimpleMovesRed(state);
        } else if (player.equals(Player.WHITE)) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (state.getPieceAt(i, j).equals(Piece.WM) || state.getPieceAt(i, j).equals(Piece.WK))
                        addLegalJumpMovesWhite(state, i, j, state.getPieceAt(i, j), 0);
                }
            }
            if (legalMoves.size() == 0)
                addLegalSimpleMovesWhite(state);

        }
        // only consider the moves with the largest amount of captures
        updateMaxCaptures();
//        removeAllButMaxCaptures();
    }

    /**
     * updates for the legal moves with the greatest number of captures
     */
    static void updateMaxCaptures() {
        for (Move m : legalMoves) {
            if (m.getCaptures() > maxCaptures)
                maxCaptures = m.getCaptures();
        }
    }

    /**
     * removes all but the moves with the largest number of viable captures from the list of legal moves
     */
    static void removeIfNotFinalMove() {
        ArrayList<Move> updatedLegalMoves = new ArrayList<Move>();
        for (int i = 0; i < legalMoves.size(); i++) {
            if (legalMoves.get(i).getCaptures() == maxCaptures)
                updatedLegalMoves.add(legalMoves.get(i));
        }
        legalMoves.clear();
        for (Move m : updatedLegalMoves)
            legalMoves.add(m);
    }

    /**
     * clears the list of legalMoves
     */
    static void resetLegalMoves() {
        legalMoves.clear();
    }

    /**
     * resets value of maxCaptures
     */
    static void resetMaxCaptures() {
        maxCaptures = 0;
    }

    static void printLegalMoves() {
        for (Move m : legalMoves) {
            System.out.println(m.toString());
        }
    }

    /**
     * retrieves and adds legal simple moves (single move) to legalMoves
     * @param state
     */
    static void addLegalSimpleMovesRed(State state) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                switch (state.getPieceAt(row, col)) {
                    // red pieces may only move forwards (towards row 7)
                    case RM:
                        if (col != 0 && state.getPieceAt(row + 1, col - 1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row + 1, col - 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);

                            if (row + 1 == 7)
                                childState.setPieceAt(row + 1, col - 1, Piece.RK);
                            else
                                childState.setPieceAt(row + 1, col - 1, Piece.RM);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (col != 7 && state.getPieceAt(row + 1, col + 1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row + 1, col + 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);

                            if (row + 1 == 7)
                                childState.setPieceAt(row + 1, col + 1, Piece.RK);
                            else
                                childState.setPieceAt(row + 1, col + 1, Piece.RM);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        break;

                    // kings can move both forward and backwards (regardless of color)
                    case RK:
                        if (row != 7 && col != 0 && state.getPieceAt(row+1, col-1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row + 1, col - 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row + 1, col - 1, Piece.RK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (row != 7 && col != 7 && state.getPieceAt(row+1, col+1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row + 1, col + 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row + 1, col + 1, Piece.RK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (row != 0 && col != 0 && state.getPieceAt(row-1, col-1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row - 1, col - 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row - 1, col - 1, Piece.RK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (row != 0 && col != 7 && state.getPieceAt(row-1, col+1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row - 1, col + 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row - 1, col + 1, Piece.RK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
    static void addLegalSimpleMovesWhite(State state) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                switch (state.getPieceAt(row, col)) {
                    // white pieces may only move backwards (towards row 0)
                    case WM:
                        if (col != 0 && state.getPieceAt(row - 1, col - 1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row - 1, col - 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);

                            if (row - 1 == 0)
                                childState.setPieceAt(row - 1, col - 1, Piece.WK);
                            else
                                childState.setPieceAt(row - 1, col - 1, Piece.WM);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (col != 7 && state.getPieceAt(row - 1, col + 1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row - 1, col + 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);

                            if (row - 1 == 0)
                                childState.setPieceAt(row - 1, col + 1, Piece.WK);
                            else
                                childState.setPieceAt(row - 1, col + 1, Piece.WM);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        break;
                    case WK:
                        if (row != 7 && col != 0 && state.getPieceAt(row+1, col-1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row + 1, col - 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row + 1, col - 1, Piece.WK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (row != 7 && col != 7 && state.getPieceAt(row+1, col+1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row + 1, col + 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row + 1, col + 1, Piece.WK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (row != 0 && col != 0 && state.getPieceAt(row-1, col-1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row - 1, col - 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row - 1, col - 1, Piece.WK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        if (row != 0 && col != 7 && state.getPieceAt(row-1, col+1).equals(Piece.E)) {
                            legalMoves.add(new Move(row, col, row - 1, col + 1));
                            State childState = new State(state);
                            childState.setPieceAt(row, col, Piece.E);
                            childState.setPieceAt(row - 1, col + 1, Piece.WK);

                            childState.updateEvaluation();
                            establishRelation(state, childState);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // adds legal moves for a specific piece for red player with red man
    static void addLegalJumpMovesRed(State state, int row, int col, Piece piece, int captures) {
        if (row <= 5 && col >= 2) {
            // if valid capture up left
            if ((state.getPieceAt(row + 1, col - 1).equals(Piece.WM) || state.getPieceAt(row + 1, col - 1).equals(Piece.WK)) && state.getPieceAt(row + 2, col - 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row + 2, col - 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row + 1, col - 1, Piece.E);

                if (piece.equals(Piece.RK) || row + 2 == 7) {
                    childState.setPieceAt(row + 2, col - 2, Piece.RK);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesRed(childState, row + 2, col - 2, Piece.RK, captures + 1);
                } else {
                    childState.setPieceAt(row + 2, col - 2, Piece.RM);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesRed(childState, row + 2, col - 2, Piece.RM, captures + 1);
                }
            }
        }
        if (row <= 5 && col <= 5) {
            // if valid capture up right
            if ((state.getPieceAt(row + 1, col + 1).equals(Piece.WM) || state.getPieceAt(row + 1, col + 1).equals(Piece.WK)) && state.getPieceAt(row + 2, col + 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row + 2, col + 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row + 1, col + 1, Piece.E);

                if (piece.equals(Piece.RK) || row + 2 == 7) {
                    childState.setPieceAt(row + 2, col + 2, Piece.RK);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesRed(childState, row + 2, col + 2, Piece.RK, captures + 1);
                } else {
                    childState.setPieceAt(row + 2, col + 2, Piece.RM);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesRed(childState, row + 2, col + 2, Piece.RM, captures + 1);
                }
            }
        }
        if (piece.equals(Piece.RK) && row >= 2 && col >= 2) {
            // if valid capture down left (red king)
            if ((state.getPieceAt(row - 1, col - 1).equals(Piece.WM) || state.getPieceAt(row - 1, col - 1).equals(Piece.WK)) && state.getPieceAt(row - 2, col - 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row - 2, col - 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row - 1, col - 1, Piece.E);
                childState.setPieceAt(row - 2, col - 2, Piece.RK);
                childState.updateEvaluation();
                establishRelation(state, childState);

                addLegalJumpMovesRed(childState, row - 2, col - 2, Piece.RK, captures + 1);
            }
        }
        if (piece.equals(Piece.RK) && row >= 2 && col <= 5) {
            // if valid capture down right (red king)
            if ((state.getPieceAt(row - 1, col + 1).equals(Piece.WM) || state.getPieceAt(row - 1, col + 1).equals(Piece.WK)) && state.getPieceAt(row - 2, col + 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row - 2, col + 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row - 1, col + 1, Piece.E);
                childState.setPieceAt(row - 2, col + 2, Piece.RK);
                childState.updateEvaluation();
                establishRelation(state, childState);

                addLegalJumpMovesRed(childState, row - 2, col + 2, Piece.RK, captures + 1);
            }
        }
    }
    static void addLegalJumpMovesWhite(State state, int row, int col, Piece piece, int captures) {
        if (row >= 2 && col >= 2) {
            // if valid capture bottom left
            if ((state.getPieceAt(row - 1, col - 1).equals(Piece.RM) || state.getPieceAt(row - 1, col - 1).equals(Piece.RK)) && state.getPieceAt(row - 2, col - 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row - 2, col - 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row - 1, col - 1, Piece.E);

                if (piece.equals(Piece.WK) || row - 2 == 0) {
                    childState.setPieceAt(row - 2, col - 2, Piece.WK);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesWhite(childState, row - 2, col - 2, Piece.WK, captures + 1);
                } else {
                    childState.setPieceAt(row - 2, col - 2, Piece.WM);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesWhite(childState, row - 2, col - 2, Piece.WM, captures + 1);
                }
            }
        }
        if (row >= 2 && col <= 5) {
            // if valid capture bottom right
            if ((state.getPieceAt(row - 1, col + 1).equals(Piece.RM) || state.getPieceAt(row - 1, col + 1).equals(Piece.RK)) && state.getPieceAt(row - 2, col + 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row - 2, col + 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row - 1, col + 1, Piece.E);

                if (piece.equals(Piece.WK) || row - 2 == 0) {
                    childState.setPieceAt(row - 2, col + 2, Piece.WK);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesWhite(childState, row - 2, col + 2, Piece.WK, captures + 1);
                } else {
                    childState.setPieceAt(row - 2, col + 2, Piece.WM);
                    childState.updateEvaluation();
                    establishRelation(state, childState);
                    addLegalJumpMovesWhite(childState, row - 2, col + 2, Piece.WM, captures + 1);
                }
            }
        }
        if (piece.equals(Piece.WK) && row <= 5 && col >= 2) {
            // if valid capture up left (white king)
            if ((state.getPieceAt(row + 1, col - 1).equals(Piece.RM) || state.getPieceAt(row + 1, col - 1).equals(Piece.RK)) && state.getPieceAt(row + 2, col - 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row + 2, col - 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row + 1, col - 1, Piece.E);
                childState.setPieceAt(row + 2, col - 2, Piece.WK);
                childState.updateEvaluation();
                establishRelation(state, childState);

                addLegalJumpMovesWhite(childState, row + 2, col - 2, Piece.WK, captures + 1);
            }
        }
        if (piece.equals(Piece.WK) && row <= 5 && col <= 5) {
            // if valid capture up right (white king)
            if ((state.getPieceAt(row + 1, col + 1).equals(Piece.RM) || state.getPieceAt(row + 1, col + 1).equals(Piece.RK)) && state.getPieceAt(row + 2, col + 2).equals(Piece.E)) {
                legalMoves.add(new Move(row, col, row + 2, col + 2, captures + 1));

                State childState = new State(state);
                childState.setPieceAt(row, col, Piece.E);
                childState.setPieceAt(row + 1, col + 1, Piece.E);
                childState.setPieceAt(row + 2, col + 2, Piece.WK);
                childState.updateEvaluation();
                establishRelation(state, childState);

                addLegalJumpMovesWhite(childState, row + 2, col + 2, Piece.WK, captures + 1);
            }
        }
    }

    static void establishRelation(State parent, State child) {
        parent.addChildren(child);
        child.setParent(parent);
    }
}
