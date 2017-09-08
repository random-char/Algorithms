import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double[] fractions;

    public PercolationStats(int n, int trials) {
        if (!(n > 0 && trials > 0)) {
            throw new java.lang.IllegalArgumentException();
        }
        this.trials = trials;
        this.fractions = new double[trials];
    }

    public double mean() {
        return StdStats.mean(this.fractions);
    }

    public double stddev() {
        return StdStats.stddev(this.fractions);
    }

    public double confidenceLo() {
        double mean = this.mean();
        double stddev = this.stddev();
        return mean - (1.96 * stddev) / Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        double mean = this.mean();
        double stddev = this.stddev();
        return mean + (1.96 * stddev) / Math.sqrt(this.trials);
    }

    private double newTestPercolation(int n) {
        Percolation percolation = new Percolation(n);
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

        for (int i = 0; i < trials; i++) {
            percolationStats.fractions[i] = percolationStats.newTestPercolation(n);
        }

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}