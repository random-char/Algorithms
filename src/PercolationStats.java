import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n;
    private final int trials;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trials) {
        if (!(n > 0 && trials > 0)) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        double[] fractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            fractions[i] = this.testPercolation(new Percolation(n));
        }

        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - (1.96 * stddev) / Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        return mean + (1.96 * stddev) / Math.sqrt(this.trials);
    }

    private double testPercolation(Percolation percolation) {
        while (!percolation.percolates()) {
            int row, col;
            do {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
            } while (percolation.isOpen(row, col));
            percolation.open(row, col);
        }
        return (double) percolation.numberOfOpenSites() / (double) (n * n);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}