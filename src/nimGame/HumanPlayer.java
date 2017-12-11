package nimGame;


import java.util.List;
import java.util.Scanner;

import edu.illinois.cs.cs125.lib.zen.Circle;
import edu.illinois.cs.cs125.lib.zen.Zen;

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
	public int[] oneMove(boolean round, List<Integer> noOfStone) {
		int pile = 0;
		int pickNumber = 0;
		final int noOfPiles = noOfStone.size();
		while(Zen.isRunning()) {
			Zen.setColor("white");
			int x = Zen.getMouseClickX();
			int y = Zen.getMouseClickY();
			Zen.drawText("x = " + x, 300, 300);
			Zen.drawText("y = " + y, 300, 350);
			Zen.drawText(this.getName() + ", It's your turn.", 30, 20);
			Zen.drawText("Remaining Stones:", 30, 40);
			Zen.drawText("Pile     Stone", 43, 70);
			Zen.drawLine(90, 75, 90, 75+30 * noOfPiles);
			for (int pileIndex = 0; pileIndex < noOfStone.size(); pileIndex++) {
				for (int stoneIndex = 0; stoneIndex < noOfStone.get(pileIndex); stoneIndex ++) {
					if(Board.clickedIn(x,y,90+15*(stoneIndex+1) - 6 , 75+15+30*pileIndex - 10,90+15*(stoneIndex+1) + 14, 75+15+30*pileIndex + 10)) {
						pile = pileIndex;
						pickNumber = stoneIndex;
						
					}
				}
			}
			for (int pileIndex = 0; pileIndex < noOfStone.size(); pileIndex++) {
				Zen.setColor("white");
				Zen.drawLine(40, 75 + 30 * (1+pileIndex), 1000, 75 + 30 * (1+pileIndex));
				Zen.drawText(String.valueOf(pileIndex+1), 50, 75 + 30 * (1+pileIndex)-5);
				for (int stoneIndex = 0; stoneIndex < noOfStone.get(pileIndex); stoneIndex ++) {
					if (pileIndex == pile && stoneIndex <= pickNumber) {
						Zen.draw(new Circle(90+15*(stoneIndex+1) + 4,75+15+30*pileIndex ,10,"red"));
					} else {
						Zen.draw(new Circle(90+15*(stoneIndex+1) + 4,75+15+30*pileIndex,10,"gray"));
					}
				}
			}
			Zen.setColor("white");
			Zen.drawText("Do you want to take "+(pickNumber+1)+ " stone" + (pickNumber == 0 ? "":"s") + " from pile " + (pile+1) + "?", 60, 75+30 * (2 + noOfPiles)-15 );
			if (round) {
				Zen.drawText("Confirm", 60, 75+30 * (4 + noOfPiles)-15);
				if(Board.clickedIn(x,y,58,75+30 * (4 + noOfPiles)-30,121,75+30 * (4 + noOfPiles)-10)) {
					Zen.flipBuffer();
					return new int[] {pile,pickNumber};
				} 
			} else {
				Zen.drawText("Confirm", 60 + 80, 75+30 * (4 + noOfPiles)-15);
				if(Board.clickedIn(x,y,58+80,75+30 * (4 + noOfPiles)-30,121+80,75+30 * (4 + noOfPiles)-10)) {
					Zen.flipBuffer();
					return new int[] {pile,pickNumber};
				} 
			}
			
			Zen.flipBuffer();
		}
		return null;
	}

}
