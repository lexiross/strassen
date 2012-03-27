
public class Strassen {
	
	/*
	 * Takes the dot product of two vectors.
	 * Just realized we may not actually need this, but
	 * I'm keeping it here just in case.
	 */
	private static int dotProduct(int[] v1, int[] v2) {
		int n = v1.length;
		int total = 0;
		for (int i = 0; i < n; i++) {
			total += v1[i] + v2[i];
		}
		return total;
	}
	
	/*
	 * Adds/subtracts two matrices and returns the result. If the last
	 * parameter is set to false, the second matrix will be subtracted
	 * from the first.
	 */
	private static Matrix add(Matrix m1, Matrix m2, boolean addition) {
		int n = m1.n;
		int[][] ret = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (addition) {
					ret[i][j] = m1.rows[i][j] + m2.rows[i][j];
				} else { 
					ret[i][j] = m1.rows[i][j] - m2.rows[i][j];
				}
			}
		}
		return new Matrix(ret);
	}
	
	private static int[] concat(int[] a, int[] b) {
		int[] c = new int[a.length+b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		
		return c;
	}
	
	private static Matrix combine(Matrix a, Matrix b, Matrix c, Matrix d) {
		int half = a.n;		
		int n = 2*half;
		int[][] rowsA = a.rows;
		int[][] rowsB = b.rows;
		int[][] rowsC = c.rows;
		int[][] rowsD = d.rows;
		int[][] ret = new int[n][n];
		for (int i = 0; i < n; i++) {
			if (i < half) {
				ret[i] = concat(rowsA[i], rowsB[i]);
			} else {
				ret[i] = concat(rowsC[i-half], rowsD[i-half]);
			}
		}
		return new Matrix(ret);
	}
	
	/*
	 * Multiplies two matrices using the conventional method.
	 */
	private static Matrix conventionalMultiply(Matrix m1, Matrix m2) {
		int n = m1.n;
		int[][] ret = new int[n][n];
		// i is row, j is column
		/*
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int dotProduct = 0;
				for (int k = 0; k < n; k++) {
					dotProduct += m1.rows[i][k]*m2.rows[k][j];
				}
				ret[i][j] = dotProduct;
			}
		}
		*/
		// improved caching performance
		int x;
		int i,j;
		for (int k = 0; k < n; k++) {
			for (i = 0; i < n; i++) {
				x = m1.rows[i][k];
				for (j = 0; j < n; j++) {
					ret[i][j] += x * m2.rows[k][j];
				}
			}
		}
		return new Matrix(ret);
	}
	
	private static Matrix strassenMultiply(Matrix m1, Matrix m2, int n, int crossover) {
		if (n <= crossover) {
			return conventionalMultiply(m1, m2);
		} else {
			Matrix[] matrices = {m1, m2};
			
			// divide both matrices into four submatrices
			Matrix a,b,c,d,e,f,g,h;
			int half = n/2;
			int count = 0;
			int[][][] submatrices = new int[8][half][half];
			for (Matrix m : matrices) {
				//TODO account for odd n
				
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (i < half) {
							if (j < half) {
								submatrices[0+4*count][i][j] = m.rows[i][j];
							} else {
								submatrices[1+4*count][i][j-half] = m.rows[i][j];
							}
						} else {
							if (j < half) {
								submatrices[2+4*count][i-half][j] = m.rows[i][j];
							} else {
								submatrices[3+4*count][i-half][j-half] = m.rows[i][j];
							}
						}
					}
				}
				count++;
			}
			a = new Matrix(submatrices[0]);
			b = new Matrix(submatrices[1]);
			c = new Matrix(submatrices[2]);
			d = new Matrix(submatrices[3]);
			e = new Matrix(submatrices[4]);
			f = new Matrix(submatrices[5]);
			g = new Matrix(submatrices[6]);
			h = new Matrix(submatrices[7]);
			Matrix p1 = strassenMultiply(a, add(f,h,false), half, crossover);
			Matrix p2 = strassenMultiply(add(a,b,true), h, half, crossover);
			Matrix p3 = strassenMultiply(add(c,d,true), e, half, crossover);
			Matrix p4 = strassenMultiply(d, add(g,e,false), half, crossover);
			Matrix p5 = strassenMultiply(add(a,d,true), add(e,h,true), half, crossover);
			Matrix p6 = strassenMultiply(add(b,d,false), add(g,h,true), half, crossover);
			Matrix p7 = strassenMultiply(add(a,c,false), add(e,f,true), half, crossover);
			Matrix topLeft = add(add(p5, add(p4,p2,false), true), p6, true);
			Matrix topRight = add(p1,p2,true);
			Matrix bottomLeft = add(p3,p4,true);
			Matrix bottomRight = add(p5, add(p1, add(p3,p7,true), false), true);
			return combine(topLeft, topRight, bottomLeft, bottomRight);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String usage = "Usage: ./strassen <version> <dimension> <inputfile>";
        if (args.length != 3) {
            System.out.println("Wrong number of arguments.");
            System.out.println(usage);
            return;
        }
        
        int version = Integer.parseInt(args[0]);
        int dimension = Integer.parseInt(args[1]);
        String filename = args[2];
        
        switch (version) {
        
        	// grading version
        	case 0:
        		Matrix[] matrices = Matrix.createFromFile(filename);
        		Matrix a = matrices[0];
        		Matrix b = matrices[1];
        		int crossover = 2;
        		if (dimension != a.n || dimension != b.n) {
        			System.out.println("Sanity check failed.");
        		} else {
        			Matrix product = strassenMultiply(a, b, dimension, crossover);
        			product.print(false);
        		}
        		break;
        		
        	// testing helper functions
        	case 1:
        		int[][] rows1 = {{1,2},{3,4}};
        		int[][] rows2 = {{5,6},{7,8}};
        		Matrix m1 = new Matrix(rows1);
        		Matrix m2 = new Matrix(rows2);
        		Matrix sum = add(m1, m2, true);
        		Matrix diff = add(m1, m2, false);
        		Matrix product = conventionalMultiply(m1, m2);
        		Matrix combine = combine(m1, m1, m2, m2);
        		m1.print(true);
        		m2.print(true);
        		System.out.println("Sum:");
        		sum.print(true);
        		System.out.println("Difference:");
        		diff.print(true);
        		System.out.println("Product:");
        		product.print(true);
        		System.out.println("Combination:");
        		combine.print(true);
        		break;
        		
        	case 2:
        		int[][] rows3 = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{25,26,27,28}};
        		int[][] rows4 = {{13,14,15,16},{17,18,19,20},{21,22,23,24},{29,30,31,32}};
        		Matrix m3 = new Matrix(rows3);
        		Matrix m4 = new Matrix(rows4);
        		Matrix convProduct = conventionalMultiply(m3,m4);
        		Matrix strassenProduct = strassenMultiply(m3,m4,4,2);
        		convProduct.print(true);
        		strassenProduct.print(true);
        		break;
        }
		
		
		
		
	}

}
