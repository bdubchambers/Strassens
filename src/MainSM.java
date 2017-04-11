
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Main driver class for implementing matrix multiplication algorithms: Naive
 * and Strassen's
 * 
 * @author Brandon Chambers
 *
 */
public class MainSM
{
	// for randNum generator range of ints
	protected static final int MIN = 0;
	protected static final int MAX = 10;

	/**
	 * main driver
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{

		// to take user input for matrix order
		Scanner input = new Scanner(System.in);
		// output user input message and gather matrix size int
		int order = getMatrixOrder(input);

		// for random number generation for population of matrices
		Random rand = new Random();
		// instantiate matrix multiplication objects
		Naive naive = new Naive();
		Strassen strass = new Strassen();

		// operand arrays that are matrices to be multiplied.
		int[][] A = new int[order][order];
		int[][] B = new int[order][order];

		// -------------------------------------------------------------------
		// populate matrix A and B arrays
		// ------------------------------------------------------------------
		populateMatrix(A, order, rand);
		populateMatrix(B, order, rand);

		// print our operand matrices out to screen
		printMatrix(A, order, "A");
		printMatrix(B, order, "B");

		// -------------------------------------------------------------------
		// Call Matrix Multiplication Methods and Store in "RESULT"
		// -------------------------------------------------------------------
		int[][] resultA = naive.naiveMM(A, B, order);
		int[][] resultB = strass.strassMM(A, B);

		printMatrixResult(resultA, order, "Naive");
		displayNaiveStats(naive, order);

		printMatrixResult(resultB, order, "Strassen's");
		displayStrassStats(strass, naive, order);

		/*
		 * Output ALL data to a txt file
		 */
		String fName = "matrixMult.txt";
		try
		{
			PrintWriter outStream = new PrintWriter(fName);
			outputData(outStream, order, A, B, resultA, resultB, naive, strass);
			outStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		input.close();
	}

	/**
	 * Writes to a txt file -- all data
	 * 
	 * @param outStream
	 * @param order
	 * @param A
	 * @param B
	 * @param resultA
	 * @param resultB
	 * @param naive
	 * @param strass
	 */
	private static void outputData(PrintWriter outStream, int order, int A[][], int B[][], 
			int resultA[][], int resultB[][], Naive naive, Strassen strass)
	{
		outStream.println("Matrix A\n");
		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
				outStream.printf("%4d", A[i][j]);
			outStream.println();
		}
		outStream.println();
		outStream.println("Matrix B\n");
		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
				outStream.printf("%4d", B[i][j]);
			outStream.println();
		}
		outStream.println();
		outStream.println("\nProduct of matrices A and B using Naive Algorithm: ");
		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
				outStream.printf("%5d", resultA[i][j]);
			outStream.println("\n");
		}
		
		outStream.println("Execution time for Naive algorithm: " + (naive.totalTime / 1000000d)
		        + " ms\n# of additions: " + naive.addCnt + "\n# of multiplications: " 
				+ naive.multCnt + "\nMatrix Order = " + order + ", n cubed (for reference) = "
		        + order * order * order);
		
		outStream.println("\nProduct of matrices A and B using Strassen's Algorithm: ");
		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
				outStream.printf("%5d", resultB[i][j]);
			outStream.println("\n");
		}
		
		outStream.println("Execution time for Strassen's algorithm: " 
				+ (strass.totalTime / 1000000d) 
				+ " ms\nDifference from naive algo execute time: " 
				+ (strass.totalTime - naive.totalTime) + " ms\n# of additions/subtractions: " 
				+ strass.addCnt + ", Difference from naive algo: " 
				+ (strass.addCnt - naive.addCnt) + "\n# of multiplications: " 
				+ strass.multCnt + ", Difference from naive algo: " 
				+ (strass.multCnt - naive.multCnt) + "\nMatrix Order = " + order
		        + ", n cubed (for reference)= " + order * order * order);
	}

	/**
	 * Simply displays an assortment of statistics, like runtime in
	 * milliseconds, for multiplying our arrays, A and B, with the Naive, or
	 * traditional Algorithm.
	 * 
	 * @param naive
	 * @param order
	 */
	private static void displayNaiveStats(Naive naive, int order)
	{
		System.out.println("Execution time for Naive algorithm: " + (naive.totalTime / 1000000d)
		        + " ms\n# of additions: " + naive.addCnt + "\n# of multiplications: " + naive.multCnt
		        + "\nMatrix Order = " + order + ", n cubed (for reference) = " + order * order * order);
	}

	/**
	 * Simply displays an assortment of statistics, like runtime in
	 * milliseconds, for multiplying our arrays, A and B, with Strassen's
	 * Algorithm
	 * 
	 * @param strass
	 * @param naive
	 * @param order
	 */
	private static void displayStrassStats(Strassen strass, Naive naive, int order)
	{
		System.out.println("Execution time for Strassen's algorithm: " + (strass.totalTime / 1000000d)
		        + " ms\nDifference from naive algo execute time: " + (strass.totalTime - naive.totalTime)
		        + " ms\n# of additions/subtractions: " + strass.addCnt + ", Difference from naive algo: "
		        + (strass.addCnt - naive.addCnt) + "\n# of multiplications: " + strass.multCnt
		        + ", Difference from naive algo: " + (strass.multCnt - naive.multCnt) + "\nMatrix Order = " + order
		        + ", n cubed (for reference)= " + order * order * order);
	}

	/**
	 * Uses a random number generator and global constants MIN and MAX to
	 * populate given array with integers in the range between MIN and MAX
	 * 
	 * @param M
	 *            input array/matrix
	 * @param order
	 *            size of array/matrix
	 * @param rand
	 *            Random obj to generate random integers
	 */
	private static void populateMatrix(int[][] M, int order, Random rand)
	{
		for (int i = 0; i < order; i++)
			for (int j = 0; j < order; j++)
				M[i][j] = rand.nextInt((MAX - MIN) + 1) + MIN;
	}

	/**
	 * Outputs greeting and prompt for user to type in the size (order) of the
	 * matrices to be multiplied. Returns nextInt() from scanner that is in turn
	 * stored in a variable in main.
	 * 
	 * @param input
	 *            pass in Scanner obj so we can properly close it in main()
	 * @return the int that will be our matrix size/order
	 */
	private static int getMatrixOrder(Scanner input)
	{

		// allow user to input matrix order
		System.out.println("Greetings, and welcome to the Matrix!" + "\nWhat size matrices do you wish to multiply?\n"
		        + "For instance, if you desire a 64x64 matrix, simply enter '64':");

		return input.nextInt();
	}

	/**
	 * For printing the original matrices prior to multiplication.
	 * 
	 * @param M
	 * @param order
	 * @param s
	 */
	private static void printMatrix(int M[][], int order, String s)
	{
		// string s is plugged in: "Strassen's" or "Naive"
		System.out.println("\nMatrix " + s);
		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
				System.out.printf("%4d", M[i][j]);
			System.out.println();
		}
	}

	/**
	 * For printing the resultant matrix after multiplication
	 * 
	 * @param result
	 *            a two dimensional product of matrix multiplication
	 * @param order
	 *            an int that is the 'size' squared of the matrix
	 * @param s
	 *            a String that represents the matrix mult. algo in use
	 */
	private static void printMatrixResult(int result[][], int order, String s)
	{
		// string s is plugged in: "Strassen's" or "Naive"
		System.out.println("\nProduct of matrices A and  B using " + s + " Algorithm: ");
		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
				System.out.printf("%5d", result[i][j]);
			// double newline for even spacing 'tween elements
			System.out.println("\n");
		}
	}

}