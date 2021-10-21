public class Search {

    private LegalMoves legalMoves = new LegalMoves();
    /**
     * White - max
     * Red - min
     */
    State alphaBetaSearch(State state) {
        int v = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);


        for (State a : state.getLeaves()) {
            if (a.getEvaluation() == v) {
                System.out.println("an optimal solution");
                a.printBoardMini();
                return a;
            }
            System.out.println("a non optimal solution");
            a.printBoardMini();
        }
        return new State();
    }

    /**
     * pick the move with highest evaluation amongst all legal moves
     */
    int maxValue(State state, int alpha, int beta) {
        if (isTerminal(state))
            return utilityFunction(state);

        int v = Integer.MIN_VALUE;

        for (State a : state.getLeaves()) {
            v = Math.max(v, minValue(a, alpha, beta));
            state.setEvaluation(v);
            if (v >= beta)
                return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    /**
     * pick the move with lowest evaluation amongst all legal moves
     */
    int minValue(State state, int alpha, int beta) {
        System.out.println("do i ever reach this point?");
        if (isTerminal(state))
            return utilityFunction(state);

        int v = Integer.MAX_VALUE;

        for (State a : state.getLeaves()) {
            v = Math.min(v, maxValue(a, alpha, beta));
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
        return (state.getLeaves().size() == 0);
    }
}
