import java.util.ArrayList;

public class EgyptianFraction {

	private int numerator, denominator, numberOfFractions;
	private long maxDenominator;

	public EgyptianFraction(int numerator, int denominator, int numberOfFractions, long maxDenominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		this.numberOfFractions = numberOfFractions;
		this.maxDenominator = maxDenominator;
	}

	public static void main(String[] args) {
		long time = System.nanoTime();
		EgyptianFraction kevin = new EgyptianFraction(4, 11, 3, 100000);
		kevin.findEgyptian();
		time = System.nanoTime() - time;
		System.out.println("Time elapsed in seconds: " + (double) time / 1000000000);
	}

	public void findEgyptian() {
		ArrayList<int[]> solutions = recursiveFraction(1, 0, 0, denominator / numerator + 1);
		if (solutions == null) {
			System.out.println("no values");
			return;
		}
		for (int[] i : solutions) {
			// double target = numerator * 1.0 / denominator;
			// double answer = 0;
			for (int j : i) {
				System.out.print("1/" + j + " ");
				// answer += 1.0 / j;
			}
			System.out.println();
		}
	}

	/**
	 * Stack structure:
	 * Pos 0: Curr Denom
	 * Pos 1: Total Num
	 * Pos 2: Total Denom
	 * 
	 * @return
	 */
	public ArrayList<int[]> iterativeFraction() {
		ArrayList<int[]> solutions = new ArrayList<int[]>();

		long[][] stack = new long[numberOfFractions][3];
		long currDenom;
		int currPosition = 0;

		currDenom = Math.max(denominator / numerator - 1, 1);
		long[] b = { currDenom, 1, currDenom };
		stack[0] = b;

		while (numberOfFractions * 1.0 / stack[0][0] > numerator * 1.0 / denominator) {
			long maxNum = stack[currPosition][0] * stack[currPosition][1]
					+ (numberOfFractions - currPosition) * stack[currPosition][2];
			long totalDenom = stack[currPosition][0] * stack[currPosition][2];
			if (maxNum * denominator < totalDenom * numerator) {
				if (currPosition == 0) {
					return solutions;
				}
				currPosition--;
				stack[currPosition][0]++;

				if (currPosition == 0) {
					stack[currPosition][1] = 1;
					stack[currPosition][2] = stack[currPosition][0];
				}

				else {
					stack[currPosition][1] = stack[currPosition - 1][1] * stack[currPosition][0]
							+ stack[currPosition - 1][2];
					stack[currPosition][2] = stack[currPosition - 1][2] * stack[currPosition][0];
				}
			}

		}

		return solutions;
	}

	public ArrayList<int[]> recursiveFraction(int currentDenominator, int currentNumberOfFractions,
			long currentTotalNumerator, long currentTotalDenominator) {

		ArrayList<int[]> solutions = new ArrayList<int[]>();

		long maxNum = currentDenominator * currentTotalNumerator
				+ (numberOfFractions - currentNumberOfFractions) * currentTotalDenominator;
		long totalDenom = currentDenominator * currentTotalDenominator;
		if (maxNum * denominator < totalDenom * numerator) {
			return null;
		}

		long totalNum = currentDenominator * currentTotalNumerator + currentTotalDenominator;

		if (totalNum * denominator > totalDenom * numerator) {
		}

		else if (currentNumberOfFractions == numberOfFractions - 2) {
			long subtractedNum = (numerator * totalDenom - denominator * totalNum);
			if (subtractedNum != 0 && (denominator * totalDenom) % subtractedNum == 0) {
				int[] temp = new int[numberOfFractions];
				int temp2 = (int) ((denominator * totalDenom) / subtractedNum);
				if (temp2 <= maxDenominator && temp2 > currentDenominator) {
					temp[numberOfFractions - 1] = temp2;
					temp[numberOfFractions - 2] = currentDenominator;
					solutions.add(temp);
				}
			}
		}

		else if (currentNumberOfFractions < numberOfFractions) {
			ArrayList<int[]> temp = recursiveFraction(currentDenominator + 1, currentNumberOfFractions + 1, totalNum,
					totalDenom);
			if (temp != null) {
				for (int[] array : temp) {
					for (int i = numberOfFractions - 1; i >= 0; i--) {
						if (array[i] == 0) {
							array[i] = currentDenominator;
							i = -1;
						}
					}
					solutions.add(array);
				}
			}
		}

		if (currentDenominator < maxDenominator) {
			ArrayList<int[]> temp = recursiveFraction(currentDenominator + 1, currentNumberOfFractions,
					currentTotalNumerator, currentTotalDenominator);
			if (temp != null) {
				for (int[] array : temp) {
					solutions.add(array);
				}
			}
		}

		if (solutions.isEmpty()) {
			return null;
		}
		return solutions;
	}

}
