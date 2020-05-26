package algorithms.union_find.java;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Percolation {

    private final int topVirtualNode;
    private final int bottomVirtualNode;

    private final Site[][] array;
    private final WeightedQuickUnionUF qu;
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        topVirtualNode = n * n;
        bottomVirtualNode = n * n + 1;

        array = new Site[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = new Site(j + (n * i), false);
            }
        }

         qu = new WeightedQuickUnionUF((n * n) + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        Site site = array[row][col];

        if (site.isOpen())
            return;

        array[row][col] = new Site(site.n, true);
        openSites++;
        connectSiteToOpenNeighbor(site, row - 1, col);
        connectSiteToOpenNeighbor(site, row, col - 1);
        connectSiteToOpenNeighbor(site, row, col + 1);
        connectSiteToOpenNeighbor(site, row + 1, col);

        if (row == 0)
            qu.union(topVirtualNode, site.n);
        if (row == array.length - 1)
            qu.union(bottomVirtualNode, site.n);
    }

    private void connectSiteToOpenNeighbor(Site site, int row, int col) {
        if (!(isValidIndex(row) && isValidIndex(col))) return;
        Site neighbor = array[row][col];
        if (neighbor.open)
            qu.union(neighbor.n, site.n);
    }

    private boolean isValidIndex(int i) {
        return i >= 0 && i < array.length;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return array[row-1][col-1].isOpen();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && qu.find(topVirtualNode) == qu.find(array[row-1][col-1].n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qu.find(topVirtualNode) == qu.find(bottomVirtualNode);
    }

    private void validateRowCol(int row, int col) {
        if (!(insideBounds(row) && insideBounds(col)))
            throw new IllegalArgumentException();
    }

    private boolean insideBounds(int i) {
        return i > 0 && i <= array.length;
    }

    private String show() {
        StringBuilder s = new StringBuilder();
        return String.join("\n",
         Arrays.stream(array)
                .map(x -> String.join(" ", Arrays.stream(x).map(a -> a.toString())
                        .collect(Collectors.toList()))).collect(Collectors.toList())
                );
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(2, 3);
        percolation.open(1, 3);
        System.out.println(percolation.isFull(3, 2));
        System.out.println(percolation.show());
        System.out.println(percolation.percolates());
    }

    private class Site {
        private final int n;
        private final boolean open;

        public Site(int n, boolean open) {
            this.n = n;
            this.open = open;
        }

        public int getN() {
            return n;
        }

        public boolean isOpen() {
            return open;
        }

        @Override
        public String toString() {
            return "Site(" +
                    "n=" + n +
                    ", open=" + open +
                    ')';
        }
    }
}