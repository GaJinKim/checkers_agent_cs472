import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Search {
    private int depth;

    /**
     * class constructor
     *
     * @param depth max depth to search
     */
    public Search(int depth) {
        this.depth = depth;
    }

    /**
     * searches for best move using alpha beta search algorithm
     *
     * @param legalMoves from which to search
     * @return optimal move (possibly chosen randomly between a group)
     */
    public State alphaBetaSearch(ArrayList<State> legalMoves) {
        if (legalMoves.size() == 1) {
            return legalMoves.get(0);
        }

        // if the move in legalMove is red (min player), then the parent is white (maximizing player); therefore bestScore should be initialized to -infinity
        int bestScore = legalMoves.get(0).getPlayer().equals(Player.RED) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ArrayList<State> bestMoves = new ArrayList<>();
        for (State child : legalMoves) {

            // legal moves for each move (child) in legalMoves
            ArrayList<Piece[][]> childLegalMoves = new LegalMoves().setLegalMoves(child);
            child.refreshLeaves(childLegalMoves);

            int val = child.getPlayer().equals(Player.RED) ? maxValue(child, depth, Integer.MIN_VALUE, Integer.MAX_VALUE) : minValue(child, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (child.getPlayer().equals(Player.RED)) {
                if (val > bestScore) {
                    bestScore = val;
                    bestMoves.clear();
                }
            } else {
                if (val < bestScore) {
                    bestScore = val;
                    bestMoves.clear();

                }
            }
            if (val == bestScore) {
                bestMoves.add(child);
            }
        }

        System.out.println("of which " + bestMoves.size() + " (optimal) moves will be considered.");
        for (State s : bestMoves) {
            s.printBoardMini();
            System.out.println();
        }
        System.out.println();
        return randomMove(bestMoves);
    }

    /**
     * utility value for max player (WHITE)
     *
     * @param state from which to find utility value
     * @param depth max depth to search
     * @param alpha value
     * @param beta value
     * @return maximizing utility value
     */
    private int maxValue(State state, int depth, int alpha, int beta) {
        if (depth == 0 || state.gameOver()) {
            return state.getEvaluation();
        }

        int v = Integer.MIN_VALUE;
        for (State child : state.getLeaves()) {
            ArrayList<Piece[][]> childLegalMoves = new LegalMoves().setLegalMoves(child);
            child.refreshLeaves(childLegalMoves);

            v = Math.max(v, minValue(child, depth - 1, alpha, beta));
            alpha = Math.max(alpha, v);

            if (alpha >= beta)
                break;
        }
        return v;
    }

    /**
     * utility value for min player (RED)
     *
     * @param state from which to find utility value
     * @param depth max depth to search
     * @param alpha value
     * @param beta value
     * @return minimizing utility value
     */
    private int minValue(State state, int depth, int alpha, int beta) {
        if (depth == 0 || state.gameOver()) {
            return state.getEvaluation();
        }

        int v = Integer.MAX_VALUE;
        for (State child : state.getLeaves()) {
            ArrayList<Piece[][]> childLegalMoves = new LegalMoves().setLegalMoves(child);
            child.refreshLeaves(childLegalMoves);

            v = Math.min(v, maxValue(child, depth - 1, alpha, beta));
            beta = Math.min(beta, v);

            if (alpha >= beta)
                break;
        }
        return v;
    }

    /**
     * picks a random move from given  list of legal moves
     *
     * @param legalMoves from which to pick a random move
     * @return a random legal move state
     */
    private State randomMove(ArrayList<State> legalMoves) {
        return legalMoves.get(new Random().nextInt(legalMoves.size()));
    }
}
