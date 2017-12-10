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
		Board newboard = new Board();
		
		newboard.playerA = setUpFirstPlayer();
		newboard.playerB = setUpSecondPlayer();
		newboard.noOfStone = new ArrayList<>();
		newboard.noOfStone.add(1);
		newboard.noOfStone.add(3);
		newboard.noOfStone.add(2);
//		newboard.setBoard();
		newboard.oneGameNew();
		newboard.finish();
	}
	public Player oneGameNew() {
		Player win = null;
		Player curPlayer = playerA;
		while(win == null) {
			oneMove(curPlayer);
			if(curPlayer.equals(playerA)) curPlayer = playerB;
			else curPlayer = playerA;
		}
		
		return win;
	}
	private int[] oneMove(Player curPlayer) {
		int pile = 0;
		int pickNumber = 0;
		final int noOfPiles = noOfStone.size();
		while(Zen.isRunning()) {
			Zen.setColor("white");
			int x = Zen.getMouseClickX();
			int y = Zen.getMouseClickY();
			Zen.drawText("x = " + x, 300, 300);
			Zen.drawText("y = " + y, 300, 350);
			Zen.drawText(curPlayer.getName() + ", It's your turn.", 30, 20);
			Zen.drawText("Remaining Stones:", 30, 40);
			Zen.drawLine(90, 75, 90, 75+30 * noOfPiles);
			for (int pileIndex = 0; pileIndex < noOfStone.size(); pileIndex++) {
				Zen.setColor("white");
				Zen.drawLine(40, 75 + 30 * (1+pileIndex), 1000, 75 + 30 * (1+pileIndex));
				Zen.drawText(noOfStone.get(pileIndex).toString(), 50, 75 + 30 * (1+pileIndex)-5);
				for (int stoneIndex = 0; stoneIndex < noOfStone.get(pileIndex); stoneIndex ++) {
					if(clickedIn(x,y,90+15*(stoneIndex+1) - 6 , 75+15+30*pileIndex - 10,90+15*(stoneIndex+1) + 14, 75+15+30*pileIndex + 10)) {
						pile = pileIndex;
						pickNumber = stoneIndex;
						
					}
					if (pileIndex == pile && stoneIndex <= pickNumber) {
						Zen.draw(new Circle(90+15*(stoneIndex+1) + 4,75+15+30*pileIndex ,10,"gold"));
						
					}
					Zen.draw(new Circle(90+15*(stoneIndex+1) + 4,75+15+30*pileIndex,10,"gray"));
				}
			}
			Zen.setColor("white");
			Zen.drawText("Confirm", 60, 75+30 * (2 + noOfPiles)-15);
			if(clickedIn(x,y,58,75+30 * (2 + noOfPiles)-20,121,75+30 * (2 + noOfPiles)-10)) {
				return new int[] {pile,pickNumber};
			} 
			
			Zen.flipBuffer();
		}
		return null;
	}
	private void finish() {
		// TODO Auto-generated method stub
		
	}
	public static Player setUpFirstPlayer() {
		Zen.setEditText("");
		boolean choice = false;
		while(Zen.isRunning()) {
			Zen.drawText("Set Up First Player:", 100, 50);
			Zen.drawText("Name: ", 100, 100);
			Zen.drawText("Is AI?: ", 100, 150);
			int x = Zen.getMouseClickX();
			int y = Zen.getMouseClickY();
			if ( y <= 153 && y >= 135) {
				if (x <= 230 && x >= 200) {
					choice = true;
				}
				if (x<=273 && x>=248) {
					choice = false;
				}
			}
			if (choice) {
				Zen.draw(new Rectangle(200,152,30,3));
			} else {
				Zen.draw(new Rectangle(248,152,25,3));
			}
			Zen.drawText("Yes", 200, 150);
			Zen.drawText("No", 250, 150);
			Zen.drawText("Confirm", 100, 250);
			String userInputName = Zen.getEditText();
			Zen.drawText(userInputName, 150, 100);
			Zen.flipBuffer();
			if(clickedIn(x,y,98,235,163,256)) {
				String name = Zen.getEditText().trim().equals("") ? "Player 1" : Zen.getEditText().trim();
				System.out.println("hello " + name);
				Player player;
				if (choice) player = new AIPlayer();
				else player = new HumanPlayer();
				player.setName(name);
				return player;
			}
		}
		return new AIPlayer();
	}
	public static Player setUpSecondPlayer() {
		Zen.setEditText("");
		boolean choice = false;
		while(Zen.isRunning()) {
			Zen.drawText("Set Up Second Player:", 100, 50);
			Zen.drawText("Name: ", 100, 100);
			Zen.drawText("Is AI?: ", 100, 150);
			int x = Zen.getMouseClickX();
			int y = Zen.getMouseClickY();
			Zen.drawText("x = " + x, 300, 300);
			Zen.drawText("y = " + y, 300, 350);
			if ( y <= 153 && y >= 135) {
				if (x <= 230 && x >= 200) {
					choice = true;
				}
				if (x<=273 && x>=248) {
					choice = false;
				}
			}
			if (choice) {
				Zen.draw(new Rectangle(200,152,30,3));
			} else {
				Zen.draw(new Rectangle(248,152,25,3));
			}
			Zen.drawText("Yes", 200, 150);
			Zen.drawText("No", 250, 150);
			Zen.drawText("Confirm", 250, 250);
			String userInputName = Zen.getEditText();
			Zen.drawText(userInputName, 150, 100);
			Zen.flipBuffer();
			if(clickedIn(x,y,98+150,235,163+150,256)) {
				String name = Zen.getEditText().trim().equals("") ? "Player 2" : Zen.getEditText().trim();
				System.out.println("hi " + name);
				Player player;
				if (choice) player = new AIPlayer();
				else player = new HumanPlayer();
				player.setName(name);
				return player;
			}
		}
		return new AIPlayer();
	}
	
	public static boolean clickedIn(int x, int y, int x1,int y1, int x2,int y2) {
		if ( y <= y2 && y >= y1) {
			if (x <= x2 && x >= x1) {
				return true;
			}
		}
		return false;
	}
}
