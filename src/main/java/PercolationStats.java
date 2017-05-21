import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] means;
	private int T;
	
	/**
	 * 
	 * @param n is used to create an n by n grid
	 * @param trials is the number of independent experiments to be performed
	 * @throws IllegalAccessException if {@code n <= 0} or {@code trials <= 0}
	 */
	public PercolationStats(int n, int trials) throws IllegalAccessException {    // perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0) {
			throw new IllegalAccessException("Either n or trials is less than or equal to zero");
		}
		means = new double[trials];
		T = trials;
	}
	
	public double mean() {                          // sample mean of percolation threshold

//		double sumOfMeans = 0;
//		for (double d : means) {
//			sumOfMeans += d;
//		}
//		return sumOfMeans/T;
		
		return StdStats.mean(means);
	}
	public double stddev() {                       // sample standard deviation of percolation threshold
		return StdStats.stddev(means);
	}
	
	public double confidenceLo() {                 // low  endpoint of 95% confidence interval
		double mean = mean();
		double stddev = stddev();
		double lo = mean - (1.96 * stddev)/Math.sqrt(T);
		return lo;
	}
	
	public double confidenceHi(){
		double mean = mean();
		double stddev = stddev();
		double hi = mean + (1.96 * stddev)/Math.sqrt(T);
		return hi;
	}
	
	public static void main(String[] args) throws IllegalAccessException {
		int n =  2 ;//StdIn.readInt();
		int T = 1000000; //StdIn.readInt();
		System.out.println("running");
		PercolationStats ps = new PercolationStats(n, T);
		for (int i = 0; i < T; i++) {
			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				p.open(row, col);
			}
			int openSites = p.numberOfOpenSites();
			double mean = (double)openSites/(n * n);
			ps.means[i] = mean;
		}
		
		System.out.println("mean = " + ps.mean());
		System.out.println("stddev = " + ps.stddev());
		System.out.println("95% confidence interval = [" + ps.confidenceLo() +"," + ps.confidenceHi() + "]" );
	}
}
