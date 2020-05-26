package algorithms.union_find.java;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE = 1.29;
    private static final double ZERO = 0.0;
    private final double[] thresholds;
    private final int n;
    private final int trials;
    private double mean;
    private double stddev;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        this.trials = trials;
        thresholds = new double[trials];
        for (int i = 0; i < thresholds.length; i++)
            thresholds[i] = this.runSimulation(new Percolation(n));
    }

    private double runSimulation(Percolation percolation) {
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
        }
        return percolation.numberOfOpenSites() / (double) (n * n);
    }


    // sample mean of percolation threshold
    public double mean() {
        if (mean == ZERO)
            mean = StdStats.mean(thresholds);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == ZERO)
            stddev = StdStats.stddev(thresholds);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((CONFIDENCE * this.stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((CONFIDENCE * this.stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
