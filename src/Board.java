public class Board  {
    static Piece[][] board = new Piece[8][8];

    // empty board
    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = Piece.E;
            }
        }
        setInitialRedPieces();
        setInitialWhitePieces();
    }

    /**
     * setters
     */
    static void setInitialRedPieces() {
        for (int i = 0; i < 8; i = i + 2) {
            board[0][i] = Piece.RM;
            board[1][i + 1] = Piece.RM;
            board[2][i] = Piece.RM;
        }
    }
    static void setInitialWhitePieces() {
        for (int i = 0; i < 8; i = i + 2) {
            board[5][i + 1] = Piece.WM;
            board[6][i] = Piece.WM;
            board[7][i + 1] = Piece.WM;
        }
    }

    /**
     * getters
     */
    Piece[][] getBoard() {
        return board;
    }

    static void printBoard() {
        System.out.println(" _______________________________________");
        for (int i = 7; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case RM:
                        System.out.print(" RM |");
                        break;
                    case RK:
                        System.out.print(" RK |");
                        break;
                    case WM:
                        System.out.print(" WM |");
                        break;
                    case WK:
                        System.out.print(" WK |");
                        break;
                    default:
                        System.out.print("    |");
                        break;
                }
            }
            System.out.println("\n|____|____|____|____|____|____|____|____|");
        }
    }

    static void printTileIndexes() {
        System.out.println(" _______________________________________");
        for (int i = 7; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + i + j + " |");
            }
            System.out.println("\n|____|____|____|____|____|____|____|____|");
        }
    }
}
