public class PercolationStats {

    public PercolationStats(int n, int trials) {
        if (!(n > 0 && trials > 0)) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    public double mean() {
        return 0.0;
    }

    public double stddev() {
        return 0.0;
    }

    public double confidenceLo() {
        return 0.0;
    }

    public double confidenceHi() {
        return 0.0;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
    }
}