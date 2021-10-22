import java.util.ArrayList;

public class Search {


    ArrayList<State> alphaBetaSearch(State state) {
        LegalMoves legalMoves = new LegalMoves();
        ArrayList<Piece[][]> possibleMoves = legalMoves.setLegalMoves(state);
        System.out.println("I have " + possibleMoves.size() + " moves in this position!" + " i am player " + state.getPlayer());

        ArrayList<State> bestPossibleStates = new ArrayList<>();
        int v = 0;

        // white is max player
        if (state.getPlayer().equals(Player.WHITE)) {
            v = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        // red is min player
        else if (state.getPlayer().equals(Player.RED)) {
            v = minValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        for (Piece[][] move : possibleMoves) {
            State stateCopy = new State(move);
            System.out.println("current value of v is: " + v);
            System.out.println("current value of stateCopy is: " + stateCopy.getEvaluation());
            bestPossibleStates.add(stateCopy);
        }
        return bestPossibleStates;
    }

    /**
     * pick move with highest evaluation amongst all legal moves
     */
    int maxValue(State state, int alpha, int beta) {
        LegalMoves legalMoves = new LegalMoves();
        ArrayList<Piece[][]> possibleMoves = legalMoves.setLegalMoves(state);
        if (isTerminal(state)) {
            return utilityFunction(state);
        }

        int v = Integer.MIN_VALUE;
        for (Piece[][] move : possibleMoves) {
            State stateCopy = new State(move);
            v = Math.max(v, minValue(stateCopy, alpha, beta));
            state.setEvaluation(v);
            if (v >= beta)
                return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    /**
     * pick move with lowest evaluation amongst all legal moves
     */
    int minValue(State state, int alpha, int beta) {
        LegalMoves legalMoves = new LegalMoves();
        ArrayList<Piece[][]> possibleMoves = legalMoves.setLegalMoves(state);
        if (isTerminal(state)) {
            return utilityFunction(state);
        }

        int v = Integer.MAX_VALUE;
        for (Piece[][] move : possibleMoves) {
            State stateCopy = new State(move);
            v = Math.min(v, maxValue(stateCopy, alpha, beta));
            state.setEvaluation(v);
            if (v <= alpha)
                return v;
            beta = Math.min(beta, v);
        }
        return v;
    }

    int utilityFunction(State state) {
        return state.evaluationFunction();
    }

    boolean isTerminal(State state) {
        return state.getLeaves().size() == 0;
    }
}
