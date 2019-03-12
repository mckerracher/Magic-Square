import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * MagicSquare is a class that allows the user to create a 2D array of ints that
 * makes a magic square. The user can create a 2D array, write it to a plain
 * text file, and check a 2D array in a plain text file.
 * 
 * @author Joshua McKerracher
 *
 */

public class MagicSquare {

	private static final int ERROR_CODE = 1;
	static String input = new String();
	static File file = new File(input);
	public static int[][] magicSquare;
	private static int[] readMatrixArray;
	private static int magicNumber;
	private static int n; // Size of the matrix.
	private static int num;

	/*
	 * This method reads in the contents of a file and stores said contents in the
	 * 2D array called magicSquare. The data in the file being read by this method
	 * must be all integers. The first number in the file must indicate the size of
	 * the matrix being read in from the file. For example, if the first number that
	 * occurs in the file is 5, the matrix in the file should be five rows by five
	 * columns. Also calculates the value of the magicNumber, which is used in the
	 * checkMatrix method.
	 * 
	 * @param file the file to be read.
	 */
	private static void readMatrix(File file) {

		// Try/catch for FileNotFound.
		try {

			Scanner fileScan = new Scanner(file);

			// Counts how many time the while loop runs.
			int counter = 0;

			while (fileScan.hasNext()) {

				// Try/catch for non-integer data.
				try {
					String number = fileScan.next();
					num = Integer.parseInt(number); // Converts string to int.
					if (counter == 0) {
						n = num; // Size of the matrix is stored with 'n'.
						magicSquare = new int[n][n]; // Creates a 2D array to track all values.
						readMatrixArray = new int[n * n + 1];
						magicNumber = (n * ((n * n) + 1)) / 2;
					}
					if (counter > 0) {
						readMatrixArray[counter - 1] = num;
					}
					counter++;
				} catch (NumberFormatException i) {
					System.out.println("The file " + file + " has data that is not an integer.");
					System.out.println(i.getMessage());
					System.exit(ERROR_CODE); // exit the program with an error status
				}
			}

			// Make the 2D array from the values stored by readMatrix.
			int count = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					magicSquare[i][j] = readMatrixArray[count];
					count++;
				}
			}
			fileScan.close();
		} catch (FileNotFoundException errorObject) {
			System.out.println("Your file could not be opened.");
			System.out.println(errorObject.getMessage());
			System.exit(ERROR_CODE); // exit the program with an error status
		} // end catch
	}

	/*
	 * This method checks the matrix stored in the 2D array called magicSquare for
	 * the properties of a magic square: the sum of all rows, columns, and diagonals
	 * are each equal to the magic number (calculated by a formula in the readMatrix
	 * method). It also checks for two other properties: that each number from 1 to
	 * n^2 occurs in the matrix and that each number in the matrix is less than or
	 * equal to n^2. Returns true if the square is a magic square and false if it is
	 * not. Employs the failSquareMessage method to print the fail message when a
	 * square fails a check.
	 * 
	 * @param checkFile the name of the file containing the square to be checked.
	 */
	public static boolean checkMatrix(String checkFile) {
		File chFile = new File(checkFile);
		readMatrix(chFile);

		// Asesigns the value of n to matrixSize.
		int nClone = n;

		// Checks that each of the integers 1, 2, 3, ..., n*n occur.
		int tempVar;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tempVar = magicSquare[i][j];

				// Second for loop to check if tempVar is anywhere else in the square.
				for (int a = 0; a < n; a++) {
					for (int b = 0; b < n; b++) {

						// Checks if the number at a,b equals the one at i,j.
						if ((magicSquare[a][b] == tempVar)) {

							// Ensures that tempVar isn't being checked against itself. Three cases.

							// Checks for case 1: (i == a) && (j != b)).
							if ((i == a) && (j != b)) {
								// Prints out the fail message.
								failSquareMessage();
								return false;
							}

							// Checks for case 2: ((i != a) && (j == b))
							if ((i != a) && (j == b)) {
								// Prints out the fail message.
								failSquareMessage();
								return false;
							}

							// Checks for case 3: ((i != a) && (j != b).
							if ((i != a) && (j != b)) {
								// Prints out the fail message.
								failSquareMessage();
								return false;
							}

						}
					}
				}
			}
		}

		// Checks if each value of the 2D array is less than n*n.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (magicSquare[i][j] > (nClone * nClone)) {
					failSquareMessage();
					return false;
				}
			}
		}

		// Finds the right diagonal sum. (rightDiagSum)
		int rightDiagSum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// Gets the value from each index in the left diagonal and adds to variable.
				if (i == j) {
					rightDiagSum += magicSquare[i][j];
				}
			}
		}

		// Checks if the right diagonal sum equals the magic number.
		if (rightDiagSum != magicNumber) {
			failSquareMessage();
			return false;
		}

		// Finds the left diagonal sum. (leftDiagSum)
		int leftDiagSum = 0;
		int iLoopTracker = 0;
		for (int i = 0; i < n; i++) {
			iLoopTracker++;
			for (int j = 0; j < n; j++) {
				// Gets the value from each index in the left diagonal and adds to variable.
				// The right diagonal *row* formula is: n - (n -1++).
				// The right diagonal *column* formula is: n - 1++.
				if ((i == (nClone - (nClone - i)) && (j == (nClone - iLoopTracker)))) {
					leftDiagSum += magicSquare[i][j];
				}
			}
		}

		// Checks if the left diagonal sum equals the magic number.
		if (leftDiagSum != magicNumber) {
			failSquareMessage();
			return false;
		}

		// Finds the row & column sums and checks if they're equal to the magic
		// number.
		int rowSum = 0;
		int colSum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {

				// Finds the row sums
				rowSum += magicSquare[i][j];

				// If this is the last loop for j, check rowSum against magicNumber.
				if (j == (nClone - 1)) {
					if (rowSum != magicNumber) {
						failSquareMessage();
						return false;
					}
					rowSum = 0;
				}

				// Finds the column sums. The i value goes up while j doesn't change.
				colSum += magicSquare[j][i];
				if (j == (nClone - 1)) {
					if (colSum != magicNumber) {
						failSquareMessage();
						return false;
					}
					colSum = 0;
				}
			}
		}

		// Prints if the 2D array is a magic square
		System.out.println("The matrix: ");
		System.out.println("");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(Integer.toString(magicSquare[i][j]) + " ");
				if (j == (n - 1)) {
					System.out.print("\n");
				}
			}
		}
		System.out.println("");
		System.out.println("is a magic square.");
		return true;
	}

	/*
	 * Writes the contents of the 2D array magicSquare to a file.
	 * 
	 * @param retFile The file name for the file that this method will write to.
	 */
	private static void writeMatrix(String retFile) {
		PrintWriter outFile;
		FileWriter fileWrite;
		int m = n;

		try {
			File returnFile = new File(retFile);
			fileWrite = new FileWriter(returnFile);

			outFile = new PrintWriter(fileWrite);

			outFile.print(Integer.toString(m));
			outFile.println(" ");
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					outFile.print(Integer.toString(magicSquare[i][j]) + " ");
					if (j == (m - 1) && i < (m - 1)) {
						outFile.println(" ");
					}
				}
			}
			outFile.close();

		} catch (IOException e) {
			System.out.println("The file " + retFile + " could not be found.");
			e.printStackTrace();
			System.exit(ERROR_CODE);
		}
	}

	/*
	 * This method creates a magic square using an algorithm that always produces a
	 * magic square given the dimensions of the square are odd. This method also
	 * uses the writeMatrix method to write the resulting square to a file.
	 * 
	 * @param returnFile The name of the file that the matrix is written to.
	 * 
	 * @param m The size of the square to be created.
	 */
	public static void createMagicSquare(int m, String returnFile) {

		if (((m % 2) == 0) && m == 2) {
			int temp = m % 2;
			System.out.println("The number must be odd!");
		} else if (m > 2) {
			n = m;

			// Create a two-dimensional array of size n.
			magicSquare = new int[m][m];

			// Create two integer values, row and col.
			// Set row = n - 1.
			int row = (m - 1);

			// Set col = n / 2.
			int col = (m / 2);

			// Create two integer values, old-row and old-col.
			int oldRow;
			int oldCol;

			// Loop through the array from index i = 1 to i = n squared.
			for (int i = 1; i <= (m * m); i++) {

				// Place the value of i in the array at the location [row][col].
				magicSquare[row][col] = i;

				// Set old-row = row and old-col = col.
				oldRow = row;
				oldCol = col;

				// Increment row and col.
				row++;
				col++;

				// If row == n, then replace it with 0. If col == n, then replace it with 0.
				if (row == m) {
					row = 0;
				}
				if (col == m) {
					col = 0;
				}

				// Check the value stored at the location [row][col].
				int temp = magicSquare[row][col];

				// If the element at [row][col] has already been filled, Then set row = old-row
				// and col = old-col, And decrement row.
				if (temp != 0) {
					row = oldRow;
					col = oldCol;
					row--;
				}
			}
		}
		writeMatrix(returnFile);
	}

	/*
	 * This purpose of this method is to create the fail message printed to the
	 * console when a magic square fails the checkMatrix method. Its purpose is
	 * basically brevity: it prints out the fail message without using 12 lines of
	 * code each time the fail message needs to be printed.
	 */
	private static void failSquareMessage() {

		String str = new String();
		System.out.println("The matrix: ");
		System.out.println("");
		for (int c = 0; c < n; c++) {
			for (int d = 0; d < n; d++) {
				System.out.print(Integer.toString(magicSquare[c][d]) + " ");
				if (d == (n - 1)) {
					System.out.print("\n");
				}
			}
		}
		System.out.println("");
		System.out.println("is not a magic square.");
	}
}