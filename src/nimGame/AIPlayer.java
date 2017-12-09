package nimGame;

import java.util.List;

public class AIPlayer extends Player{

	private int difficulty = 100;
	/**
	 * @return the difficulty
	 */
	
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/* (non-Javadoc)
	 * @see nimGame.Player#move(java.util.List)
	 */
	@Override
	public int[] move(List<Integer> noOfStone) {
		int nimSum = nimSum(noOfStone);
		int sum = sum(noOfStone);
		if (nimSum == 0 || sum > difficulty) {
			int a = (int) Math.random() * noOfStone.size();
			int t = noOfStone.get(a);
			int b = (int) Math.random() * t;
			return new int[] {a, t-b};
			//TODO:better random
		}
		int firstZero = Integer.SIZE - Integer.numberOfLeadingZeros(nimSum);
		for (int index = 0; index < noOfStone.size(); index++) {
			int n  = noOfStone.get(index);
			if ((n & (1 << (firstZero - 1))) != 0) {
				//TODO: randomly choose from a eligible pile 
				return new int[] {index, n - (n ^ nimSum)};
			}
		}
		return null;
	}
	
	private int nimSum(List<Integer> noOfStone) {
		int sum = 0;
		for (int n : noOfStone) {
			sum = sum ^ n;
		}
		return sum;
	}
	
	private int sum(List<Integer> noOfStone) {
		int sum = 0;
		for (int n : noOfStone) {
			sum = sum + n;
		}
		return sum;
	}
}
