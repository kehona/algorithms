import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author konadip
 * 
 */
public class PercolationStats {
	private double[] means;
	private int trials;
	private int size;

	/**
	 * 
	 * @param n
	 *            is used to create an n by n grid
	 * @param trials
	 *            is the number of independent experiments to be performed
	 * @throws IllegalAccessException
	 *             if {@code n <= 0} or {@code trials <= 0} perform trials
	 *             independent experiments on an n-by-n grid
	 */
	public PercolationStats(int n, int t) throws IllegalArgumentException {
		if (n <= 0 || t <= 0) {
			throw new IllegalArgumentException("Either n or trials is less than or equal to zero");
		}
		means = new double[t];
		trials = t;
		size = n;
		
		for (int i = 0; i < t; i++) {
			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!p.isOpen(row, col)) {
					p.open(row, col);
				}	
			}
			int openSites = p.numberOfOpenSites();
			double mean = (double) openSites / (n * n);
			means[i] = mean;
		}
	}

	/**
	 * 
	 * @return sample mean of percolation threshold
	 */
	public double mean() {
		return StdStats.mean(means);
	}

	/**
	 * 
	 * @return sample standard deviation of percolation threshold
	 */
	public double stddev() {
		if (size == 1) return Double.NaN;
		return StdStats.stddev(means);
	}

	/**
	 * @return low endpoint of 95% confidence interval
	 */
	public double confidenceLo() {
		double mean = mean();
		double stddev = stddev();
		return mean - (1.96 * stddev) / Math.sqrt(trials);
	}

	/**
	 * @return high endpoint of 95% confidence interval
	 */
	public double confidenceHi() {
		double mean = mean();
		double stddev = stddev();
		return mean + (1.96 * stddev) / Math.sqrt(trials);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int n = StdIn.readInt();
		int t = StdIn.readInt();

		PercolationStats ps = new PercolationStats(n, t);

		System.out.println("mean = " + ps.mean());
		System.out.println("stddev = " + ps.stddev());
		System.out.println("95% confidence interval = [" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
	}
}
