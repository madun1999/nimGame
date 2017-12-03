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
	public static void main(final String[] unused) {
		
	}
	
	
	

}
