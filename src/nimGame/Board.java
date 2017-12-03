package nimGame;

import java.util.List;
public class Board {
	
	private List<Integer> noOfStone;
	
	private int[] aIMove() {
		int nimSum = nimSum(noOfStone);
		if (nimSum == 0) {
			int a = (int) Math.random() * noOfStone.size(); 
			int b = (int) Math.random() * noOfStone.get(a) + 1;
			return new int[] {a,b};
			//TODO:better random
		}
		int firstZero = Integer.SIZE - Integer.numberOfLeadingZeros(nimSum);
		for (int index = 0; index < noOfStone.size(); index++) {
			int n  = noOfStone.get(index);
			if ((n & (1 << (firstZero - 1))) != 0) {
				 return new int[] {index, n ^ nimSum};
			}
		}
		return new int[]{0,0};
	}
	
	private int nimSum(List<Integer> noOfStone) {
		int sum = 0;
		for (int n : noOfStone) {
			sum = sum ^ n;
		}
		return sum;
	}
}
