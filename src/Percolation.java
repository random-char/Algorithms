import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int lastIndex;

    private int numOpen = 0;

    private int[] checkLastRowCellColumns = new int[] {-1};

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
            if (isOpenArray[neighbourIndex]) {
                weightedQuickUnionUF.union(cellIndex, neighbourIndex);
            }
        }

        if (checkLastRowCellColumns[0] != -1) {
            for (int i = 0; i < checkLastRowCellColumns.length; i++) {
                if (isFull(n, checkLastRowCellColumns[i])) {
                    weightedQuickUnionUF.union(calcCellIndex(n, checkLastRowCellColumns[i]), lastIndex);
                    removeFromArray(checkLastRowCellColumns[i]);
                }
            }
        }
    }

    private int[] getCellNeighbours(int row, int col) {
        int numNeighbours = 4;
        int neighbourIndex = 0;

        int cellIndex = calcCellIndex(row, col);

        if (col == 1) {
            numNeighbours--;
        }
        if (col == n) {
            numNeighbours--;
        }
        if (row == n) {
            addToArray(col);
            numNeighbours--;
        }

        int[] neighbours = new int[numNeighbours];

        if (row != 1) neighbours[neighbourIndex++] = cellIndex - n;
        if (row != n) neighbours[neighbourIndex++] = cellIndex + n;

        if (col != 1) neighbours[neighbourIndex++] = cellIndex - 1;
        if (col != n) neighbours[neighbourIndex++] = cellIndex + 1;

        if (row == 1) neighbours[neighbourIndex] = 0;

        return neighbours;
    }

    private void addToArray(int cellsColumn) {
        if (checkLastRowCellColumns.length == 1 && checkLastRowCellColumns[0] == -1) {
            checkLastRowCellColumns[0] = cellsColumn;
            return;
        }
        int[] a = new int[checkLastRowCellColumns.length + 1];
        System.arraycopy(checkLastRowCellColumns, 0, a, 0, checkLastRowCellColumns.length);
        a[a.length - 1] = cellsColumn;
        checkLastRowCellColumns = a;
    }

    private void removeFromArray(int cellsColumn) {
        for (int i = 0; i < checkLastRowCellColumns.length; i++) {
            if (checkLastRowCellColumns[i] == cellsColumn) {
                if (i == 0) {
                    checkLastRowCellColumns[0] = -1;
                } else {
                    int[] a = new int[checkLastRowCellColumns.length - 1];
                    System.arraycopy(checkLastRowCellColumns, 0, a, 0, i);
                    System.arraycopy(checkLastRowCellColumns, i + 1, a, i, checkLastRowCellColumns.length - i - 1);
                    checkLastRowCellColumns = a;
                }
                return;
            }
        }
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
