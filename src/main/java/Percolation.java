import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * 
 * @author Kehinde Onadipe
 *
 */
public class Percolation {
	WeightedQuickUnionUF uf;
	int columns;
	int[] arr;
	int count = 0;
	int imaginarySite1;
	int imaginarySite2;
	public Percolation(int n) { // create n-by-n grid, with all sites blocked
		uf = new WeightedQuickUnionUF(n * n + 2); //adding extra two virtual sites
		arr = new int[n*n];
		columns = n;
		imaginarySite1 = n*n ;
		imaginarySite2 = n*n + 1;
		//set arr elements from 1  to n*n
		for (int i = 0; i < n*n - 1; i++){
			arr[i] = i+1;
		}
	}

	public void open(int row, int col) { // open site (row, col) if it is not								// open already
	    if (!isOpen(row, col)) {
	    	int id = xyToID(row, col);
	    	//to open a site, set the value to n * n;
	    	uf.union(imaginarySite1, id);
	    	if (id < columns) {
	    		//arr[id] = imaginarySite2;
	    		uf.union(imaginarySite2, id);
	    	}
	    	
	    	if ((col + 1 <= columns) || isOpen(row, col + 1)) {
	    		int idRight = xyToID(row, col + 1);
	    		uf.union(id, idRight);
	    	}
	    	if (col - 1 > 0  && isOpen(row, col - 1)) {
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
	   
	}

	public boolean isOpen(int row, int col) { // is site (row, col) open?
		int id = xyToID(row, col);
		int root = uf.find(id);
		return root == imaginarySite1;
	}

	public boolean isFull(int row, int col) { // is site (row, col) full?
		int id = xyToID(row, col);
		return uf.connected(id, imaginarySite2);
	}

	public int numberOfOpenSites() { // number of open sites
		int count = 0; 
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != i+1) {
				count++;
			}
		}
		return count;
	}

	public boolean percolates() { // does the system percolate?
		
		for (int i = 0; i < columns; i++) {
			if (uf.find(columns * (columns -1) + i) == imaginarySite1) {
				return true;
			}
		}
		return false;
	}
	
	private int xyToID(int row, int col) {
		return (row - 1) * columns + (col - 1);
	}
	
	public static void main(String[] args) { // test client (optional)
		Percolation p = new Percolation(1);
		p.open(1, 1);

		int id1 = p.xyToID(1, 1);
		System.out.println(id1);
		
		
		System.out.println(p.uf.find(id1));

		//System.out.println(p.uf.connected(id1, id1));
		
		System.out.println(p.isFull(1, 1));
		
		System.out.println(p.numberOfOpenSites());
		System.out.println(p.percolates());
	
	}

}
