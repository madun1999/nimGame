package nimGame;


import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player{

	/* (non-Javadoc)
	 * @see nimGame.Player#move(java.util.List)
	 */
	private Scanner scanner = new Scanner(System.in);
	@Override
	public int[] move(List<Integer> noOfStone) {
		int numberOfPiles = noOfStone.size();
		int pileIndex = -1;
		while (pileIndex < 0 || pileIndex > (numberOfPiles - 1)) {
			System.out.println("Note : Enter a number in the range that is given");
			System.out.print("Please choose the stone piles(0 to " + (numberOfPiles - 1) + ")");
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
		return new int[] {pileIndex,stonePicked};
//		removeZeros(pileIndex);
	}

}
