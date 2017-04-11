
/**
 * The traditional, pen and paper style of matrix multiplication.
 * 
 * @author Brandon Chambers
 * 
 *
 */
public class Naive
{
	// fields for timing runtime
	protected long startTime;
	protected long endTime;
	protected long totalTime;

	//fields for multiplication and addition counters
	protected int addCnt = 0;
	protected int multCnt = 0;
	
	/**
	 * Does the actual matrix multiplication
	 * 
	 * @param A
	 * @param B
	 * @param order
	 * @return
	 */
	public int[][] naiveMM(int A[][], int B[][], int order)
	{
		// start timing Matrix Multiplication
		startTime = System.nanoTime();

		int sum = 0;
		int[][] result = new int[order][order];

		for (int i = 0; i < order; i++)
		{
			for (int j = 0; j < order; j++)
			{
				for (int k = 0; k < order; k++, addCnt++, multCnt++)
					sum = sum + A[i][k] * B[k][j];
				result[i][j] = sum;
				sum = 0;
			}
		}

		// stop timing
		endTime = System.nanoTime();
		totalTime = (endTime - startTime);
		return result;
	}

}