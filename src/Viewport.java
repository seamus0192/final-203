public final class Viewport
{
    private int row;
    private int col;
    private int numRows;
    private int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getNumRows() {
        return numRows;
    }

    public void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }


    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    public Point worldToViewport( int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

}
