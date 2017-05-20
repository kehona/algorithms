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
		arr = new int[n*n + 2];
		columns = n;
		imaginarySite1 = n*n ;
		imaginarySite2 = n*n + 1;
		//keep track of open sites with array
		for (int i = 0; i < n * n + 2; i++) {
			arr[i] = i;
		}	
		//set top and bottom rows of weightedQU array to imaginary sites
		for (int i = 0; i < n; i++){
			uf.union(imaginarySite1, i);
			uf.union(imaginarySite2, columns * columns - (n - i));
		}
	}

	public void open(int row, int col) { // open site (row, col) if it is not								// open already
	    if (!isOpen(row, col)) {
	    	int id = xyToID(row, col);
	    	//open site by setting its value to something other than id
	    	arr[id] = id + 1;
	    	//check adjacent site to see if its open and connect
	    	if ((col + 1 <= columns) && isOpen(row, col + 1)) {
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
		return arr[id] != id;

	}

	public boolean isFull(int row, int col) { // is site (row, col) full?
		int id = xyToID(row, col);
		if (isOpen(row, col)) {
			return uf.connected(id, imaginarySite1);
		}
		return false;
	}

	public int numberOfOpenSites() { // number of open sites
		int count = 0; 
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != i) {
				count++;
			}
		}
		return count;
	}

	public boolean percolates() { // does the system percolate?
		return uf.connected(imaginarySite1, imaginarySite2);
	}
	
	private int xyToID(int row, int col) {
		return (row - 1) * columns + (col - 1);
	}
	
	public static void main(String[] args) { // test client (optional)
		Percolation p = new Percolation(2);
		p.open(1, 1);
		p.open(2, 1);
		p.open(2, 2);

		int id1 = p.xyToID(1, 1);
		int id2 = p.xyToID(2, 1);
		System.out.println(id2);
		
		
		System.out.println(p.uf.find(id1));

		//System.out.println(p.uf.connected(id1, id1));
		
		System.out.println(p.isFull(1, 2));
		System.out.println(p.uf.find(id1));
		System.out.println(p.uf.find(id2));
		System.out.println(p.numberOfOpenSites());
		System.out.println(p.percolates());
	
	}

}
