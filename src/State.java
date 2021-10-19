public class State {
    char playerToMove;

    // initial state
    public State() {
        playerToMove = 'r';
    }

    int getStateEvaluation() {
        return Board.getNumOfRedPieces() - Board.getNumOfWhitePieces();
    }

}
