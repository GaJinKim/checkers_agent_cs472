public class Move {
    int startRow;
    int startCol;
    int endRow;
    int endCol;
    int captures;

    public Move(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.captures = 0;
    }
    public Move(int startRow, int startCol, int endRow, int endCol, int captures) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.captures = captures;
    }

    int getCaptures() {
        return captures;
    }

    @Override
    public String toString() {
        return "(" + startRow + "," + startCol + ") -> (" + endRow + "," + endCol + ")" + "    " + getCaptures();
    }
}
