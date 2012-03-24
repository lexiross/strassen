/*
import java.util.List;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
*/
import java.io.*;
import java.util.ArrayList;

public class Matrix {
	public int[][] rows;
	public int n;
	
	
	
	public Matrix(int[][] rows) {
		this.rows = rows;
		this.n = rows[0].length;
	}
	
	public Matrix[] createFromFile(String filename, boolean firstHalf) {
		int count = 0;
		Matrix[] ret = new Matrix[2];
		ArrayList<String> lines = new ArrayList<String>();
		try {
			FileReader input = new FileReader(filename);
			BufferedReader reader = new BufferedReader(input);
			String line = reader.readLine();
			count++;
			
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
				count++;
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] linesArray = (String[]) lines.toArray();
		int d = count/2;
		int n = (int) Math.sqrt(d);
		int k = 0;
		int l = 0;
		for (int i = 0; i < 2; i++) {
			int[][] rows = new int[n][n];
			int[] row = new int[n];
			k = 0;
			l = 0;
			for (int j = d*i; j < d*i+d; j++) {
				row[k] = Integer.parseInt(linesArray[j]);
				k++;
				if (k == n) {
					k = 0;
					rows[l] = row;
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
