import java.util.ArrayList;
import java.util.Arrays;

public class State {
    private Piece[][] board = new Piece[8][8];
    private State parent;
    private ArrayList<State> children;
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
        this.children = new ArrayList<State>();
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
        this.parent = state.parent;
        this.children = state.getChildren();
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
        this.children = new ArrayList<>();
        this.evaluation = evaluationFunction();
    }

    /**
     * getters
     */
    Piece[][] getBoard() {
        return board;
    }
    State getParent() {
        return parent;
    }
    ArrayList<State> getChildren() {
        return children;
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
    void setParent(State parent) {
        this.parent = parent;
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

    void clearChildren() {
        this.children.clear();
    }
    void addChildren(State state) {
        this.getChildren().add(state);
    }

    void updateEvaluation() {
        setEvaluation(evaluationFunction());
    }

    /**
     * white - max player
     * red - min player
     */
    int evaluationFunction() {
        return getNumOfWhitePieces() - getNumOfRedPieces();
    }

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

    void printTileIndexes() {
        System.out.println(" _______________________________________");
        for (int i = 7; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + i + j + " |");
            }
            System.out.println("\n|____|____|____|____|____|____|____|____|");
        }
    }

    void updateLeaves(State state) {
        if (state.getChildren().size() == 0) {
            leaves.add(state);
            return;
        }
        for (int i = 0; i < state.getChildren().size(); i++) {
            updateLeaves(state.getChildren().get(i));
        }
    }

    void refreshLeaves() {
        leaves = new ArrayList<State>();
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

}
