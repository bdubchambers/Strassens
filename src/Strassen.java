
/**
 * Strassen's Matrix Multiplication that is
 * supposed to be more efficient...
 * 
 * @author Brandon Chambers
 *
 */
public class Strassen
{
	//fields for timing runtime
	protected long startTime;
	protected long endTime;
	protected long totalTime;
	
	//fields for multiplication and addition counters
	protected int addCnt = 0;
	protected int multCnt = 0;

	/**
	 * Strassen's Matrix Multiplication
	 * Each iteration, we must 'split' our array into equal
	 * quadrants until we are left with only quadrants of 
	 * order 2 (four total elements per quadrant).  This is
	 * a divide and conquer algo because we are breaking 
	 * the matrix down into ever smaller problem sizes and 
	 * solving those smaller sub problems, combining the 
	 * results (along with the help of some 'magic' Strassen
	 * formulas!), and ending up with the same product array
	 * that we would have gotten with a Naive M.M. algorithm.
	 * 
	 * @param A is a 2-D array
	 * @param B is a 2-D array
	 * @return 
	 * 		the 2D array product of A * B
	 */
	public int[][] strassMM(int[][] A, int[][] B)
	{
		//start timing Strassen's Matrix Multiplication
		startTime = System.nanoTime();
		
		int n = A.length;

		// Our resultant array after multiplication
		int[][] C = new int[n][n];

		//'base case' 
		if (n == 1)
			C[0][0] = A[0][0] * B[0][0];

		else // n > 1
		{

			// declaring partial array quadrants
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			// splitting matrix A in four quadrants
			split(A, A11, 0, 0);
			split(A, A12, 0, n / 2);
			split(A, A21, n / 2, 0);
			split(A, A22, n / 2, n / 2);

			// splitting matrix B in four quadrants
			split(B, B11, 0, 0);
			split(B, B12, 0, n / 2);
			split(B, B21, n / 2, 0);
			split(B, B22, n / 2, n / 2);

			/*
			 *  recursively multiply the partial arrays
			 */
			int[][] M1 = strassMM(add(A11, A22), add(B11, B22));
			int[][] M2 = strassMM(add(A21, A22), B11);
			int[][] M3 = strassMM(A11, subtract(B12, B22));
			int[][] M4 = strassMM(A22, subtract(B21, B11));
			int[][] M5 = strassMM(add(A11, A12), B22);
			int[][] M6 = strassMM(subtract(A21, A11), add(B11, B12));
			int[][] M7 = strassMM(subtract(A12, A22), add(B21, B22));
			multCnt+=7;
			
			/*
			 *  perform addition/subtraction on partial arrays
			 */
			int[][] C11 = add(subtract(add(M1, M4), M5), M7);
			int[][] C12 = add(M3, M5);
			int[][] C21 = add(M2, M4);
			int[][] C22 = add(subtract(add(M1, M3), M2), M6);

			// joining partial arrays
			join(C11, C, 0, 0);
			join(C12, C, 0, n / 2);
			join(C21, C, n / 2, 0);
			join(C22, C, n / 2, n / 2);
		}
		
		//stop timing 
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		return C;
	}

	/**
	 * does the subtraction
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public int[][] subtract(int[][] A, int[][] B)
	{
		// matrix order
		int n = A.length;
		// declare partial array
		int[][] C = new int[n][n];

		// subtract split array
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++, addCnt++)
				C[i][j] = A[i][j] - B[i][j];

		// return partial array
		return C;
	}

	/**
	 * does the addition
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public int[][] add(int[][] A, int[][] B)
	{

		// getting matrix order
		int n = A.length;

		// declaring partial array
		int[][] C = new int[n][n];

		// adding splitted array
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++, addCnt++)
				C[i][j] = A[i][j] + B[i][j];

		// returing partial array
		return C;
	}

	/**
	 * splitting parent matrix into child matrices
	 * 
	 * @param P
	 * @param C
	 * @param iB
	 * @param jB
	 */
	public void split(int[][] P, int[][] C, int iB, int jB)
	{
		for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
			for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
				C[i1][j1] = P[i2][j2];
	}

	/**
	 * joining child matrices into parent matrix
	 * @param C
	 * @param P
	 * @param iB
	 * @param jB
	 */
	public void join(int[][] C, int[][] P, int iB, int jB)
	{
		for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
			for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
				P[i2][j2] = C[i1][j1];
	}

}