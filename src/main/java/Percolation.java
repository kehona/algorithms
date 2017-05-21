import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Kehinde Onadipe
 * 
 *         This is the solution to the percolation problem in Princeton
 *         Algorithms class. The Percolation Datastructure uses the
 *         WeightedQuickUnion datastructure to maintain states of connected
 *         sites. In Percolation, we using another grid to maintain sites
 */
public class Percolation {
	private WeightedQuickUnionUF uf;
	private int columns;
	private int[] arr;
	private int imaginarySite1;
	private int imaginarySite2;

	/**
	 * 
	 * @param n
	 *            create n-by-n grid, with all sites blocked adding extra two
	 *            virtual sites
	 */
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		uf = new WeightedQuickUnionUF(n * n + 2);
	
		arr = new int[n * n + 2];
		columns = n;
		imaginarySite1 = n * n;
		imaginarySite2 = n * n + 1;
		// keep track of open sites with array
		for (int i = 0; i < n * n + 2; i++) {
			arr[i] = i;
		}
		// set top and bottom rows of weightedQU array to imaginary sites
		for (int i = 0; i < n; i++) {
			uf.union(imaginarySite1, i);
			//uf.union(imaginarySite2, columns * columns - (n - i));
		}
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * 
	 *            open site (row, col) if it is not open already
	 */
	public void open(int row, int col) {
		validate(row, col);
		if (!isOpen(row, col)) {
			int id = xyToID(row, col);
			// open site by setting its value to something other than id
			arr[id] = id + 1;
			// check adjacent site to see if its open and connect
			if ((col + 1 <= columns) && isOpen(row, col + 1)) {
				int idRight = xyToID(row, col + 1);
				uf.union(id, idRight);
			}
			if (col - 1 > 0 && isOpen(row, col - 1)) {
				int idLeft = xyToID(row, col - 1);
				uf.union(id, idLeft);
			}
			if (row + 1 <= columns && isOpen(row + 1, col)) {
				int idUp = xyToID(row + 1, col);
				uf.union(idUp, id);
			}
			if (row - 1 > 0 && isOpen(row - 1, col)) {
				int idDown = xyToID(row - 1, col);
				uf.union(idDown, id);
			}
		}
		connectToBottomImaginarySite(row, col);
	}

	/**
	 * @param row
	 * @param col
	 * @return true is site (row, col) open?
	 */
	public boolean isOpen(int row, int col) {
		validate(row, col);
		int id = xyToID(row, col);
		return arr[id] != id;
	}

	/**
	 * @param row
	 * @param col
	 * 
	 * @return public true if site is full
	 */
	public boolean isFull(int row, int col) {
		validate(row, col);
		int id = xyToID(row, col);
		if (isOpen(row, col)) {
			return uf.connected(id, imaginarySite1);
		}
		return false;
	}

	/**
	 * @return
	 */
	public int numberOfOpenSites() {
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != i) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @return true if the system percolates and false if it does not
	 */
	public boolean percolates() {
		if (columns == 1) {
			return isOpen(1, 1);
		}
		return uf.connected(imaginarySite1, imaginarySite2);
	}

	/**
	 * @param row
	 * @param col
	 * 
	 * @return the mapped 1 dimensional ID of a 2 dimensional grid
	 */
	private int xyToID(int row, int col) {
		return (row - 1) * columns + (col - 1);
	}

	private void validate(int row, int col) {
		int id = xyToID(row, col);
		if (row > columns || col > columns || row < 1 || col < 1) {
			throw new IndexOutOfBoundsException("row " + row + " or column " + col + " is not between  and " + columns);
		}
	}
	
	private void connectToBottomImaginarySite(int row, int col) {
		
		if (row == columns && isOpen(row, col)) {
			int id = xyToID(row, col);
			uf.union(imaginarySite2, id);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
