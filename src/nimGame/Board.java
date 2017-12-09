package nimGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import edu.illinois.cs.cs125.lib.zen.*;
public class Board {

	private Player playerA;
	private Player playerB;

	private List<Integer> noOfStone;
	private int getNumberOfPiles() {return noOfStone == null ? 0 : noOfStone.size();}

	private int numberOfRounds;
	private Scanner scanner;
	public void setBoard() {
		scanner = new Scanner(System.in);
		System.out.println("How many piles?");
		int temp = scanner.nextInt();
		noOfStone = new ArrayList<>();
		//TODO:Random number of stones
		for(int index = 0; index < temp; index++) {
			System.out.println("How many stones for pile " + index);
			noOfStone.add(scanner.nextInt());
			scanner.nextLine();
		}
	}
	public void oneGame() {
		while(!this.ifWin()) {
			this.play();
		}
	}
	public void setNameAndAI() {
		System.out.println("What is the name for the first player? He will go first.");
		String name = scanner.nextLine();
		System.out.println("Is he an AI?");
		if (scanner.nextBoolean()) playerA = new AIPlayer();
		else playerA = new HumanPlayer();
		playerA.setName(name);
		scanner.nextLine();
		
		System.out.println("What is the name for the second player? He will go second.");
		name = scanner.nextLine();
		System.out.println("Is he an AI?");
		if (scanner.nextBoolean()) playerB = new AIPlayer();
		else playerB = new HumanPlayer();
		playerB.setName(name);
		scanner.nextLine();
	}

	public void displayBoard() {
		int cur = numberOfRounds % 2;
		if (cur == 0) {
			System.out.println("Player " + playerA.getName() + "'s move");
		} else {
			System.out.println("Player " + playerB.getName() + "'s move");
		}
		System.out.println("The current board: " + Arrays.toString(noOfStone.toArray()));
	}

	public void move() {
		int pileIndex = -1;
		while (pileIndex < 0 || pileIndex > (getNumberOfPiles() - 1)) {
			System.out.println("Note : Enter anumber in the range that is given");
			System.out.print("Please choose the stone piles(0 to " + (getNumberOfPiles() - 1) + ")");
			scanner = new Scanner(System.in);
			pileIndex =  scanner.nextInt();
			scanner.nextLine();
		}
		System.out.println("Successfully choose pile index:" + pileIndex);
		int stonePicked = -1;
		while (stonePicked <= 0 || stonePicked > noOfStone.get(pileIndex)) {
			System.out.println("Note : Enter a number in the range that is given");
			System.out.print("Please choose the number of stones "
					+ "that you can going to pick up(1 to " + noOfStone.get(pileIndex) + ")");
			scanner = new Scanner(System.in);
			stonePicked = scanner.nextInt();
			scanner.nextLine();
		}
		System.out.println("Successfully choose stone picked:" + stonePicked);
		noOfStone.set(pileIndex,(noOfStone.get(pileIndex) - stonePicked));
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
		int[] pick = curPlayer.move(noOfStone);
		if (pick == null) {
			System.out.println(curPlayer.getName() + "resigned");
			System.out.println("Game Over");
			noOfStone = new ArrayList<>();
			return;
		}
		noOfStone.set(pick[0],(noOfStone.get(pick[0]) - pick[1]));
		if(ifWin()) {
			System.out.println("End Of the Game :)");
			System.out.println(curPlayer.getName() + "wins!");
		}
		numberOfRounds++;
	}

//	private void aIMove() {
//		int nimSum = nimSum(noOfStone);
//		if (nimSum == 0) {
//			int a = (int) Math.random() * noOfStone.size();
//			int t = noOfStone.get(a);
//			int b = (int) Math.random() * t;
//			noOfStone.set(a, b);
//			removeZeros(a);
//			System.out.println("AI removed " + (t - b) + " stones from pile " + a);
//			//TODO:better random
//		}
//		//TODO: difficulty level
//		int firstZero = Integer.SIZE - Integer.numberOfLeadingZeros(nimSum);
//		for (int index = 0; index < noOfStone.size(); index++) {
//			int n  = noOfStone.get(index);
//			if ((n & (1 << (firstZero - 1))) != 0) {
//				noOfStone.set(index, n ^ nimSum);
//				removeZeros(index);
//				System.out.println("AI removed " + (n - (n ^ nimSum)) + " stones from pile " + index);
//				return;
//			}
//		}
//	}
//
//	private int nimSum(List<Integer> noOfStone) {
//		int sum = 0;
//		for (int n : noOfStone) {
//			sum = sum ^ n;
//		}
//		return sum;
//	}
	
	private boolean removeZeros(int pIndex) {
		//first check if pIndex is valid
		if (pIndex < noOfStone.size() && noOfStone.get(pIndex) == 0) {
			noOfStone.remove(pIndex);
			return true;
		}
		return false;
	}

	private boolean ifWin() {
		while (removeZeros(0)) {} //May delete this
		return noOfStone.isEmpty();
	}

	public static void main(final String[] unused) {
		if (Zen.isRunning()) {
			Zen.fillOval(20, 30, 4, 4);
			Board newboard = new Board();
			newboard.setBoard();
			newboard.setNameAndAI();
			newboard.oneGame();
		}
	}
}
