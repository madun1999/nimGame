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

	public void setBoard() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("How many piles?");
		int temp = scanner.nextInt();
		noOfStone = new ArrayList<>();
		//TODO:Random number of stones
		for(int index = 0; index < temp; index++) {
			System.out.print("How many stones for pile " + index);
			noOfStone.add(scanner.nextInt());
		}
		scanner.close();
	}

	public void setNameAndAI() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("What is the name for the first player? He will go first.");
		playerA.setName(scanner.nextLine());
		System.out.print("Is he an AI?");
		playerA.setAI(scanner.nextBoolean());
		System.out.print("What is the name for the second player? He will go second.");
		playerB.setName(scanner.nextLine());
		System.out.print("Is he an AI?");
		playerB.setAI(scanner.nextBoolean());
		scanner.close();
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
			myScan.close();
		}
		System.out.print("Successfully choose pile index:" + pileIndex);
		int stonePicked = -1;
		while (stonePicked <= 0 || stonePicked > noOfStone.get(pileIndex)) {
			System.out.println("Note : Enter anumber in the range that is given");
			System.out.print("Plear choose the number of stones "
					+ "that you can going to pick up(1 + " + noOfStone.get(pileIndex) + ")");
			myScan = new Scanner(System.in);
			stonePicked = myScan.nextInt();
			myScan.close();
		}
		System.out.print("Successfully choose stone picked:" +stonePicked);
		removeZeros(pileIndex);
	}

	public void play() {
		displayBoard();
		int cur = numberOfRounds % 2;
		Player curPlayer;
		if (cur == 0) {
			curPlayer = playerA;
		} else {
			curPlayer = playerB;
		}
		if(curPlayer.isAI()) {
			aIMove();
		} else {
			move();
		}
		if(ifWin()) {
			System.out.println("End Of the Game :)");
			System.out.println(curPlayer + "wins!");
		}
		numberOfRounds++;
	}

	private void aIMove() {
		int nimSum = nimSum(noOfStone);
		if (nimSum == 0) {
			int a = (int) Math.random() * noOfStone.size();
			int b = (int) Math.random() * noOfStone.get(a);
			noOfStone.set(a, b);
			removeZeros(a);
			//TODO:better random
		}
		//TODO: difficulty level
		int firstZero = Integer.SIZE - Integer.numberOfLeadingZeros(nimSum);
		for (int index = 0; index < noOfStone.size(); index++) {
			int n  = noOfStone.get(index);
			if ((n & (1 << (firstZero - 1))) != 0) {
				noOfStone.set(index, n ^ nimSum);
				removeZeros(index);
				return;
			}
		}
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
		Board newboard = new Board();
		newboard.setBoard();
		newboard.setNameAndAI();
		newboard.play();
	}
}
