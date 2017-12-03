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
	
	public void move() {
		int pileIndex = -1;
		Scanner myScan;
		while (pileIndex < 0 || pileIndex > (numberOfPiles - 1)) {
			System.out.println("Note : Enter anumber in the range that is given");
			System.out.print("Please choose the stone piles(0 to " + (numberOfPiles - 1) + ")");
			myScan = new Scanner(System.in);
			pileIndex =  myScan.nextInt();
		}
		System.out.print("Successfully choose pile index:" + pileIndex);
		int stonePicked = -1;
		while (stonePicked <= 0 || stonePicked > noOfStone.get(pileIndex)) {
			System.out.println("Note : Enter anumber in the range that is given");
			System.out.print("Plear choose the number of stones "
					+ "that you can going to pick up(1 + " + noOfStone.get(pileIndex) + ")");
			myScan = new Scanner(System.in);
			stonePicked = myScan.nextInt();	
		}
		System.out.print("Successfully choose stone picked:" +stonePicked);
	}
	public void play() {
		displayBoard();
		int cur = numberOfRounds % 2;
		if (cur == 0) {
			if(playerA.isAI()) {
				aIMove();
			} else {
				move();
			}
		} else {
			if(playerB.isAI()) {
				aIMove();
			} else {
				move();
			}	
		}
		
		
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
	
	public static void main(final String[] unused) {
		
	}
}
