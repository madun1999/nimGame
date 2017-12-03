package nimGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Board {
	
	private Player playerA;
	
	private Player playerB;
	
	private List<Integer> noOfStone;
	
	private int numberOfPiles;
	
	private int numberOfRounds;
	
	public void setBoard(int size) {
		noOfStone = new ArrayList<>();
		numberOfPiles = size;	
	}
	
	public void setNameAndAI(String newName1, boolean isAI1, 
			String newName2, boolean isAI2) {
		playerA.setName(newName1);
		playerB.setName(newName2);
		playerA.setAI(isAI1);
		playerB.setAI(isAI2);		
	}
	
	public void displayBoard() {
		int cur = numberOfRounds % 2;
		if (cur == 0) {
			System.out.print("Player " + playerA + "'s move");
		} else {
			System.out.print("Player " + playerB + "'s move");
		}
		System.out.println(Arrays.toString(noOfStone.toArray()));
	}
	
	public void play() {
		displayBoard();
		System.out.print("Please choose the stone piles(0 to " + (numberOfPiles - 1) + ")");
		Scanner myScan = new Scanner(System.in);
		String pileIndex =  myScan.nextLine();
		numberOfRounds++;
		
		
	}

	private int[] aIMove() {
		int nimSum = nimSum(noOfStone);
		if (nimSum == 0) {
			int a = (int) Math.random() * noOfStone.size(); 
			int b = (int) Math.random() * noOfStone.get(a) + 1;
			return new int[] {a,b};
			//TODO:better random
		}
		//TODO: difficulty level
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
	private boolean removeZeros(int pIndex) {
		//first check if pIndex is valid
		if (pIndex < noOfStone.size() && noOfStone.get(pIndex) == 0) { 
			noOfStone.remove(pIndex);
			return true;
		}
		return false;
	}
	
	private boolean ifWin() {
		while (removeZeros(0)) {} // May delete this
		return noOfStone.isEmpty();
	}
	
	public static void main(final String[] unused) {
		
	}
}
