import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int lastIndex;
    private int numOpen = 0;

    private boolean[] isOpenArray;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        this.lastIndex = n * n + 1;

        isOpenArray = new boolean[lastIndex + 1];
        isOpenArray[0] = true;
        isOpenArray[lastIndex] = true;

        weightedQuickUnionUF = new WeightedQuickUnionUF(lastIndex + 1);
    }

    private void checkArgs(int row, int col) {
        if (row < 1 || row > n ||
            col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private int calcCellIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    public void open(int row, int col) {
        this.checkArgs(row, col);
        int cellIndex = calcCellIndex(row, col);

        if (isOpenArray[cellIndex]) return;

        isOpenArray[cellIndex] = true;
        numOpen++;
        int[] neighbourIndexes = getCellNeighbours(row, col);
        for (int neighbourIndex: neighbourIndexes) {
            if (neighbourIndex == lastIndex) {
                if (weightedQuickUnionUF.connected(cellIndex, 0)) weightedQuickUnionUF.union(cellIndex, neighbourIndex);
            } else if (isOpenArray[neighbourIndex]) {
                weightedQuickUnionUF.union(cellIndex, neighbourIndex);
            }
        }
    }

    private int[] getCellNeighbours(int row, int col) {
        int numNeighbours = 4;
        int neighbourIndex = 0;

        if (col == 1 || col == n) numNeighbours--;

        int[] neighbours = new int[numNeighbours];

        if (row != 1) neighbours[neighbourIndex++] = calcCellIndex(row, col) - n;
        if (row != n) neighbours[neighbourIndex++] = calcCellIndex(row, col) + n;

        if (col != 1) neighbours[neighbourIndex++] = calcCellIndex(row, col) - 1;
        if (col != n) neighbours[neighbourIndex++] = calcCellIndex(row, col) + 1;

        if (row == 1) neighbours[neighbourIndex++] = 0;
        if (row == n) neighbours[neighbourIndex] = lastIndex;

        return neighbours;
    }

    public boolean isOpen(int row, int col) {
        this.checkArgs(row, col);
        return isOpenArray[calcCellIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        this.checkArgs(row, col);
        return weightedQuickUnionUF.connected(calcCellIndex(row, col), 0);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(lastIndex, 0);
    }
}
