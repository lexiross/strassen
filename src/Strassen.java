
public class Strassen {
	
	/*
	 * Takes the dot product of two vectors.
	 * Just realized we may not actually need this, but
	 * I'm keeping it here just in case.
	 */
	private int dotProduct(int[] v1, int[] v2) {
		int n = v1.length;
		int total = 0;
		for (int i = 0; i < n; i++) {
			total += v1[i] + v2[i];
		}
		return total;
	}
	
	/*
	 * Adds/subtracts two matrices and returns the result. If the last
	 * parameter is set to true, the second matrix will be subtracted
	 * from the first.
	 */
	private Matrix add(Matrix m1, Matrix m2, boolean subtract) {
		// TODO
		int n = m1.n;
		int[][] ret = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (subtract) {
					ret[i][j] = m1.rows[i][j] - m2.rows[i][j];
				} else { 
					ret[i][j] = m1.rows[i][j] + m2.rows[i][j];
				}
			}
		}
		return new Matrix(ret);
	}
	
	/*
	 * Multiplies two matrices using the conventional method.
	 */
	private Matrix conventionalMultiply(Matrix m1, Matrix m2) {
		int n = m1.n;
		int[][] ret = new int[n][n];
		// i is row, j is column
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int dotProduct = 0;
				for (int k = 0; k < n; k++) {
					dotProduct += m1.rows[i][k]*m2.rows[k][j];
				}
				ret[i][j] = dotProduct;
			}
		}
		return new Matrix(ret);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
