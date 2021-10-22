public class Move {
    int startRow;
    int startCol;
    int endRow;
    int endCol;

    public Move(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    @Override
    public String toString() {
        return "(" + startRow + "," + startCol + ") -> (" + endRow + "," + endCol + ")";
    }
}
