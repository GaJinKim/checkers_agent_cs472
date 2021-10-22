import java.util.ArrayList;

public class LegalMoves {
    static ArrayList<Piece[][]> legalMoves = new ArrayList<>();

    /**
     * updates legalMoves with legal moves
     *
     * note: jump moves are forced - take priority (override) simple moves
     *
     * @param state from which to derive legal moves
     * @return array of legal moves
     */
    static ArrayList<Piece[][]> setLegalMoves(State state) {
        legalMoves.clear();
        jumpMoves(state);

        // add simple moves should no jump moves be available
        if (legalMoves.size() == 0)
            simpleMoves(state);

        return legalMoves;
    }

    /**
     * updates legalMoves with legal simple moves
     * @param state from which to derive simple moves
     */
    static void simpleMoves(State state) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (state.getPlayer().equals(Player.RED)) {
                    switch (state.getPieceAt(row, col)) {
                        // red man (moves towards row 7)
                        case RM:
                            // up left
                            if (col != 0 && state.getPieceAt(row + 1, col - 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;

                                if (row + 1 == 7)
                                    board[row + 1][col - 1] = Piece.RK;
                                else
                                    board[row + 1][col - 1] = Piece.RM;

                                legalMoves.add(board);
                            }
                            // up right
                            if (col != 7 && state.getPieceAt(row + 1, col + 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;

                                if (row + 1 == 7)
                                    board[row + 1][col + 1] = Piece.RK;
                                else
                                    board[row + 1][col + 1] = Piece.RM;

                                legalMoves.add(board);
                            }
                            break;

                        // red king
                        case RK:
                            // up left
                            if (row != 7 && col != 0 && state.getPieceAt(row + 1, col - 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row + 1][col - 1] = Piece.RK;
                                legalMoves.add(board);
                            }
                            // up right
                            if (row != 7 && col != 7 && state.getPieceAt(row + 1, col + 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row + 1][col + 1] = Piece.RK;
                                legalMoves.add(board);
                            }
                            // down left
                            if (row != 0 && col != 0 && state.getPieceAt(row - 1, col - 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row - 1][col - 1] = Piece.RK;
                                legalMoves.add(board);
                            }
                            // down right
                            if (row != 0 && col != 7 && state.getPieceAt(row - 1, col + 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row - 1][col + 1] = Piece.RK;
                                legalMoves.add(board);
                            }
                            break;
                        default:
                            break;
                    }
                } else if (state.getPlayer().equals(Player.WHITE)) {
                    switch (state.getPieceAt(row, col)) {
                        // white man (moves towards row 7)
                        case WM:
                            // down left
                            if (col != 0 && state.getPieceAt(row - 1, col - 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;

                                if (row - 1 == 0)
                                    board[row - 1][col - 1] = Piece.WK;
                                else
                                    board[row - 1][col - 1] = Piece.WM;

                                legalMoves.add(board);
                            }
                            // down right
                            if (col != 7 && state.getPieceAt(row - 1, col + 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;

                                if (row - 1 == 0)
                                    board[row - 1][col + 1] = Piece.WK;
                                else
                                    board[row - 1][col + 1] = Piece.WM;

                                legalMoves.add(board);
                            }
                            break;

                        // white king
                        case WK:
                            // up left
                            if (row != 7 && col != 0 && state.getPieceAt(row + 1, col - 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row + 1][col - 1] = Piece.WK;
                                legalMoves.add(board);
                            }
                            // up right
                            if (row != 7 && col != 7 && state.getPieceAt(row + 1, col + 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row + 1][col + 1] = Piece.WK;
                                legalMoves.add(board);
                            }
                            // down left
                            if (row != 0 && col != 0 && state.getPieceAt(row - 1, col - 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row - 1][col - 1] = Piece.WK;
                                legalMoves.add(board);
                            }
                            // down right
                            if (row != 0 && col != 7 && state.getPieceAt(row - 1, col + 1).equals(Piece.E)) {
                                Piece[][] board = state.deepCopyBoard();
                                board[row][col] = Piece.E;
                                board[row - 1][col + 1] = Piece.WK;
                                legalMoves.add(board);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * updates legalMoves with legal jump moves
     * @param state from which to derive jump moves
     */
    static void jumpMoves(State state) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (state.getPlayer().equals(Player.RED))
                    jumpMovesRed(state, row, col);
                if (state.getPlayer().equals(Player.WHITE))
                    jumpMovesWhite(state, row, col);
            }
        }
    }

    /**
     * jumpMoves helper functions
     */
    static void jumpMovesRed(State state, int row, int col) {
        switch (state.getPieceAt(row, col)) {
            case RM:
                // capture up left
                if (row <= 5 && col >= 2) {
                    if ((state.getPieceAt(row + 1, col - 1).equals(Piece.WM) || state.getPieceAt(row + 1, col - 1).equals(Piece.WK)) && state.getPieceAt(row + 2, col - 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row + 1][col - 1] = Piece.E;

                        // promote to king if capturing into last row
                        if (row + 2 == 7)
                            board[row + 2][col - 2] = Piece.RK;
                        else
                            board[row + 2][col - 2] = Piece.RM;
                        legalMoves.add(board);
                    }
                }
                // capture up right
                if (row <= 5 && col <= 5) {
                    if ((state.getPieceAt(row + 1, col + 1).equals(Piece.WM) || state.getPieceAt(row + 1, col + 1).equals(Piece.WK)) && state.getPieceAt(row + 2, col + 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row + 1][col + 1] = Piece.E;

                        // promote to king if capturing into last row
                        if (row + 2 == 7)
                            board[row + 2][col + 2] = Piece.RK;
                        else
                            board[row + 2][col + 2] = Piece.RM;
                        legalMoves.add(board);
                    }
                }
                break;
            case RK:
                // capture down left
                if (row >= 2 && col >= 2) {
                    if ((state.getPieceAt(row - 1, col - 1).equals(Piece.WM) || state.getPieceAt(row - 1, col - 1).equals(Piece.WK)) && state.getPieceAt(row - 2, col - 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row - 1][col - 1] = Piece.E;
                        board[row - 2][col - 2] = Piece.RK;
                        legalMoves.add(board);
                    }
                }
                // capture down right
                if (row >= 2 && col <= 5) {
                    if ((state.getPieceAt(row - 1, col + 1).equals(Piece.WM) || state.getPieceAt(row - 1, col + 1).equals(Piece.WK)) && state.getPieceAt(row - 2, col + 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row - 1][col + 1] = Piece.E;
                        board[row - 2][col + 2] = Piece.RK;
                        legalMoves.add(board);
                    }
                }
                // capture up left
                if (row <= 5 && col >= 2) {
                    if ((state.getPieceAt(row + 1, col - 1).equals(Piece.WM) || state.getPieceAt(row + 1, col - 1).equals(Piece.WK)) && state.getPieceAt(row + 2, col - 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row + 1][col - 1] = Piece.E;
                        board[row + 2][col - 2] = Piece.RK;
                        legalMoves.add(board);
                    }
                }
                // capture up right
                if (row <= 5 && col <= 5) {
                    if ((state.getPieceAt(row + 1, col + 1).equals(Piece.WM) || state.getPieceAt(row + 1, col + 1).equals(Piece.WK)) && state.getPieceAt(row + 2, col + 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row + 1][col + 1] = Piece.E;
                        board[row + 2][col + 2] = Piece.RK;
                        legalMoves.add(board);
                    }
                }
                break;
        }

    }
    static void jumpMovesWhite(State state, int row, int col) {
        switch (state.getPieceAt(row, col)) {
            case WM:
                // capture down left
                if (row >= 2 && col >= 2) {
                    if ((state.getPieceAt(row - 1, col - 1).equals(Piece.RM) || state.getPieceAt(row - 1, col - 1).equals(Piece.RK)) && state.getPieceAt(row - 2, col - 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row - 1][col - 1] = Piece.E;

                        // promote to king if capturing into last row
                        if (row - 2 == 0)
                            board[row - 2][col - 2] = Piece.WK;
                        else
                            board[row - 2][col - 2] = Piece.WM;
                        legalMoves.add(board);
                    }
                }
                // capture down right
                if (row >= 2 && col <= 5) {
                    if ((state.getPieceAt(row - 1, col + 1).equals(Piece.RM) || state.getPieceAt(row - 1, col + 1).equals(Piece.RK)) && state.getPieceAt(row - 2, col + 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row - 1][col + 1] = Piece.E;

                        // promote to king if capturing into last row
                        if (row - 2 == 0)
                            board[row - 2][col + 2] = Piece.WK;
                        else
                            board[row - 2][col + 2] = Piece.WM;
                        legalMoves.add(board);
                    }
                }
                break;
            case WK:
                // capture down left
                if (row >= 2 && col >= 2) {
                    if ((state.getPieceAt(row - 1, col - 1).equals(Piece.RM) || state.getPieceAt(row - 1, col - 1).equals(Piece.RK)) && state.getPieceAt(row - 2, col - 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row - 1][col - 1] = Piece.E;
                        board[row - 2][col - 2] = Piece.WK;
                        legalMoves.add(board);
                    }
                }
                // capture down right
                if (row >= 2 && col <= 5) {
                    if ((state.getPieceAt(row - 1, col + 1).equals(Piece.RM) || state.getPieceAt(row - 1, col + 1).equals(Piece.RK)) && state.getPieceAt(row - 2, col + 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row - 1][col + 1] = Piece.E;
                        board[row - 2][col + 2] = Piece.WK;
                        legalMoves.add(board);
                    }
                }
                // capture up left
                if (row <= 5 && col >= 2) {
                    if ((state.getPieceAt(row + 1, col - 1).equals(Piece.RM) || state.getPieceAt(row + 1, col - 1).equals(Piece.RK)) && state.getPieceAt(row + 2, col - 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row + 1][col - 1] = Piece.E;
                        board[row + 2][col - 2] = Piece.WK;
                        legalMoves.add(board);
                    }
                }
                // capture up right
                if (row <= 5 && col <= 5) {
                    if ((state.getPieceAt(row + 1, col + 1).equals(Piece.RM) || state.getPieceAt(row + 1, col + 1).equals(Piece.RK)) && state.getPieceAt(row + 2, col + 2).equals(Piece.E)) {
                        Piece[][] board = state.deepCopyBoard();
                        board[row][col] = Piece.E;
                        board[row + 1][col + 1] = Piece.E;
                        board[row + 2][col + 2] = Piece.WK;
                        legalMoves.add(board);
                    }
                }
                break;
        }

    }
}
