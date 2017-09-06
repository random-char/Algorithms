public class Percolation {

    private int n;
    private int[] grid;

    private int blocked = 0;
    private int open = 1;
    private int full = 2;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        //0 to (n * n - 1) are indexes of the grid cells
        // n * n and n * n + 1 are indexes of top and bottom extra cells
        grid = new int[n * n + 2];
        grid[n * n] = full;
        grid[n * n + 1] = open;
    }

    private void checkArgs(int row, int col) {
        if (row < 1 || row > n ||
            col < 1 || col > n) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private int calcCellIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    public void open(int row, int col) {
        this.checkArgs(row, col);
        int cellIndex = calcCellIndex(row, col);
        if (grid[cellIndex] == blocked) {
            grid[cellIndex] = open;
            fillCellIfNeeded(row, col);
            if (this.isFull(row, col)) {
                fillCellsNeighbours(row, col);
            }
        }
    }

    private void fillCellIfNeeded(int row, int col) {
        int[] neighbours = getCellNeighbours(row, col);
        for (int neighbourIndex: neighbours) {
            if (grid[neighbourIndex] == full) {
                grid[calcCellIndex(row, col)] = full;
                return;
            }
        }
    }

    private void fillCellsNeighbours(int row, int col) {
        int[] neighbours = getCellNeighbours(row, col);
        for (int neighbourIndex: neighbours) {
            if (grid[neighbourIndex] == open) {
                grid[neighbourIndex] = full;
            }
        }
    }

    private int[] getCellNeighbours(int row, int col) {
        int numNeighbours = 4;
        int neighbourIndex = 0;

        if (col == 1 || col == n) numNeighbours--;

        int[] neighbours = new int[numNeighbours];

        if (row == 1) {
            neighbours[neighbourIndex++] = n * n;
        } else if (row == n) {
            neighbours[neighbourIndex++] = n * n + 1;
        }

        if (row != 1) neighbours[neighbourIndex++] = calcCellIndex(row, col) - n;
        if (row != n) neighbours[neighbourIndex++] = calcCellIndex(row, col) + n;

        if (col != 1) neighbours[neighbourIndex] = calcCellIndex(row, col) - 1;
        if (col != n) neighbours[neighbourIndex] = calcCellIndex(row, col) + 1;

        return neighbours;
    }

    private boolean checkCell(int row, int col, int type) {
        this.checkArgs(row, col);
        return grid[(row - 1) * n + (col - 1)] == type;
    }

    public boolean isOpen(int row, int col) {
        return this.checkCell(row, col, open);
    }

    public boolean isFull(int row, int col) {
        return this.checkCell(row, col, full);
    }

    public int numberOfOpenSites() {
        int num = 0;
        for (int i = 0, max = n * n; i < max; i++) {
            if (grid[i] == open) num++;
        }
        return num;
    }

    public boolean percolates() {
        return grid[n * n + 1] == full;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
    }
}
