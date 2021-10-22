import java.util.ArrayList;
import java.util.Arrays;

public class State {
    private Piece[][] board = new Piece[8][8];
    private int evaluation;
    private Player player;
    private ArrayList<State> leaves = new ArrayList<State>();

    /**
     * constructors
     */
    public State() {
        initializeEmptyBoard();
        initialRedPieces();
        initialWhitePieces();
        this.evaluation = evaluationFunction();
        this.player = Player.RED;
        this.leaves = new ArrayList<State>();
    }
    public State(State state) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = state.getBoard()[i][j];
            }
        }
        this.evaluation = state.evaluationFunction();
        this.player = state.getPlayer();
        this.leaves = state.getLeaves();
    }
    public State(Piece[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = board[i][j];
            }
        }
        this.evaluation = evaluationFunction();
        this.player = null;
        this.leaves = new ArrayList<State>();
    }
    public State(Piece[][] board, Player player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = board[i][j];
            }
        }
        this.evaluation = evaluationFunction();
        this.player = player;
        this.leaves = new ArrayList<State>();
    }

    /**
     * getters
     */
    Piece[][] getBoard() {
        return board;
    }
    ArrayList<State> getLeaves() { return leaves; }
    int getEvaluation() {
        return evaluation;
    }
    Player getPlayer() {
        return player;
    }

    /**
     * setters
     */
    void setPlayer(Player player) {
        this.player = player;
    }
    void setBoard(Piece[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
                this.board[i][j] = board[i][j];
        }
    }
    void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * helpers
     */
    Piece[][] deepCopyBoard() {
        Piece[][] newBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = getBoard()[i][j];
            }
        }
        return newBoard;
    }
    void initializeEmptyBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
                board[i][j] = Piece.E;
        }
    }
    void initialRedPieces() {
        for (int i = 0; i < 8; i = i + 2) {
            board[0][i] = Piece.RM;
            board[1][i + 1] = Piece.RM;
            board[2][i] = Piece.RM;
        }
    }
    void initialWhitePieces() {
        for (int i = 0; i < 8; i = i + 2) {
            board[5][i + 1] = Piece.WM;
            board[6][i] = Piece.WM;
            board[7][i + 1] = Piece.WM;
        }
    }
    void clearAllPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setPieceAt(i, j, Piece.E);
            }
        }
    }

    Piece getPieceAt(int row, int col) {
        return board[row][col];
    }
    void setPieceAt(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    /**
     * white - max player
     * red - min player
     */
    int evaluationFunction() {
        return getNumOfWhitePieces() - getNumOfRedPieces();
    }

    /**
     * gets the number of red pieces at the current state
     *
     * @return number of red pieces
     */
    int getNumOfRedPieces() {
        int redPieces = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals(Piece.RM) || board[i][j].equals(Piece.RK)) {
                    redPieces++;
                }
            }
        }
        return redPieces;
    }

    /**
     * gets the number of white pieces at the current state
     *
     * @return number of white pieces
     */
    int getNumOfWhitePieces() {
        int whitePieces = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals(Piece.WM) || board[i][j].equals(Piece.WK)) {
                    whitePieces++;
                }
            }
        }
        return whitePieces;
    }

    /**
     * prints board representation of current state
     */
    void printBoard() {
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

    /**
     * prints board (miniaturized) presentation of current state
     */
    void printBoardMini() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                switch (board[i][j]) {
                    case RM:
                        System.out.print("RM ");
                        break;
                    case RK:
                        System.out.print("RK ");
                        break;
                    case WM:
                        System.out.print("WM ");
                        break;
                    case WK:
                        System.out.print("WK ");
                        break;
                    default:
                        System.out.print("__ ");
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     * adds the state of a legal move (leaf) to the current state
     *
     * @param state to add to current state leaves
     */
    void addLeaf(State state) {
        leaves.add(state);
    }

    /**
     * updates leaf values to a corresponding list of legal moves (board states)
     *
     * @param legalMoves from which to update leaves
     */
    void refreshLeaves(ArrayList<Piece[][]> legalMoves) {
        clearLeaves();
        for (Piece[][] a : legalMoves) {
            State leaf = getPlayer().equals(Player.RED) ? new State(a, Player.WHITE) : new State(a, Player.RED);
            addLeaf(leaf);
        }
    }

    void clearLeaves() {
        leaves.clear();
    }

    void printLeaves() {
        for (State state : leaves) {
            state.printBoardMini();
            System.out.println();
        }
    }

    void refreshBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++)
                board[i][j] = Piece.E;
        }
    }

    boolean gameOver() {
        return (getNumOfRedPieces() == 0 || getNumOfWhitePieces() == 0);
    }
}
