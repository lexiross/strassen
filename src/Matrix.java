import java.io.*;

public class Matrix {
	public int[][] rows;
	public int n;
	
	
	public Matrix(int[][] rows) {
		this.rows = rows;
		this.n = rows[0].length;
	}
	
	public static Matrix[] createFromFile(String filename, int n) {
		int numLines = 2*n*n;
		Matrix[] ret = new Matrix[2];
		//ArrayList<String> lines = new ArrayList<String>();
		String[] lines = new String[numLines];
		try {
			FileReader input = new FileReader(filename);
			BufferedReader reader = new BufferedReader(input);
			for (int i = 0; i < numLines; i++) {
				String line = reader.readLine();
				lines[i] = line;
			}
						
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found! Make sure it's in the same directory as the Makefile");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int l = 0;
		for (int i = 0; i < 2; i++) {
			int[][] rows = new int[n][n];
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					rows[j][k] = Integer.parseInt(lines[l]);
					l++;
				}
			}
			ret[i] = new Matrix(rows);
		}
		return ret;
	}
	
	/*
	 * Prints a matrix to System.out.
	 * If test is set to true, print all elements of the matrix. 
	 * Else print only the diagonals.
	 */
	public void print(boolean test) {
		if (test) {
			for (int i = 0; i < this.n; i++) {
				String line = "";
				for (int j = 0; j < this.n; j++) {
					line += this.rows[i][j] + "\t";
				}
				System.out.println(line);
			}
		} else {
			for (int i = 0; i < this.n; i++) {
				System.out.println(this.rows[i][i]);
			}
		}
		System.out.println();
	}
}
