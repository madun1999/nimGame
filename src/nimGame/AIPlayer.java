package nimGame;

import java.util.List;

import edu.illinois.cs.cs125.lib.zen.Circle;
import edu.illinois.cs.cs125.lib.zen.Zen;

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
	public int[] oneMove(boolean round, List<Integer> noOfStone) {
		int pile = 0;
		int pickNumber = 0;
		int[] out = move(noOfStone);
		out[1] -= 1;
		pile = out[0];
		pickNumber = out[1];
		final int noOfPiles = noOfStone.size();
		if(Zen.isRunning()) {
			Zen.setColor("white");
			Zen.drawText("It is AI " + this.getName()+ "'s move.", 30, 20);
			Zen.drawText("Remaining Stones:", 30, 40);
			Zen.drawText("Pile     Stone", 43, 70);
			Zen.drawLine(90, 75, 90, 75+30 * noOfPiles);
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
			Zen.drawText(this.getName() + " took "+(pickNumber+1)+ " stone" + (pickNumber == 0 ? "":"s") + " from pile " + (pile+1) + ".", 60, 75+30 * (2 + noOfPiles)-15 );
			boolean clicked = true;
			if (round) {
				Zen.drawText("Confirm", 60, 75+30 * (4 + noOfPiles)-15); 
			} else {
				Zen.drawText("Confirm", 60 + 80, 75+30 * (4 + noOfPiles)-15);
			}
			Zen.flipBuffer();
			while(clicked) {
				
				Zen.waitForClick();
				int x = Zen.getMouseClickX();
				int y = Zen.getMouseClickY();
				if (round) {
					
					if(Board.clickedIn(x,y,58,75+30 * (4 + noOfPiles)-30,121,75+30 * (4 + noOfPiles)-10)) {
						
						return out;
					} 
				} else {
					
					if(Board.clickedIn(x,y,58+80,75+30 * (4 + noOfPiles)-30,121+80,75+30 * (4 + noOfPiles)-10)) {
						
						return out;
					} 
				}
			}
		}
		return out;
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
