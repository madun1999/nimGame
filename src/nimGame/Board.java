package nimGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import edu.illinois.cs.cs125.lib.zen.*;
public class Board {

	private Player playerA;
	private Player playerB;
	private int tempNumberOfPiles;
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
		boolean con = true;
		while(con) {
			Board newboard = new Board();
			newboard.playerA = setUpFirstPlayer();
			newboard.playerB = setUpSecondPlayer();
			newboard.setNumerOfPiles();
			newboard.setPiles();
			Player win = newboard.oneGameNew();
			con = newboard.finish(win);
		}
		Zen.setFont("Times-30");
		Zen.drawText("Goodbye!", 150, 150);
		Zen.flipBuffer();
	}

	public Player oneGameNew() {
		Player win = null;
		Player curPlayer = playerA;
		while(win == null) {
			int[] pick = curPlayer.oneMove(curPlayer.equals(playerA), noOfStone);
			noOfStone.set(pick[0], noOfStone.get(pick[0])-pick[1]-1);
			removeZeros(pick[0]);
			if(ifWin()) {
				return curPlayer;
			}
			if(curPlayer.equals(playerA)) curPlayer = playerB;
			else curPlayer = playerA;
		}
		
		return win;
	}
	private boolean finish(Player player) {
		boolean again = false;
		if(Zen.isRunning()) {
			boolean confirmed = false;
			while (!confirmed) {
				
				int x = Zen.getMouseClickX();
				int y = Zen.getMouseClickY();
				Zen.drawText(player.getName() + " wins!", 200, 200);
				Zen.drawText("Play again?", 200, 230);
				Zen.drawText("Yes", 200, 260);
				Zen.drawText("No", 240, 260);
				Zen.drawText("Confirm", 200,290);
				
				if (clickedIn(x,y,200,242,230,262)) {
					again = true;
				}
				if (clickedIn(x,y,240,242,265,262)) {
					again = false;
				}
				
				if (again) {
					Zen.draw(new Rectangle(200,262,30,3));
				} else {
					Zen.draw(new Rectangle(240,262,25,3));
				}
				
				if (clickedIn(x,y,200,275,265,290)) {
					Zen.flipBuffer();
					return again;
					
				}
				Zen.flipBuffer();
				Zen.waitForClick();
			}
		}
		return false;
		
	}

	public static Player setUpFirstPlayer() {
		Zen.setEditText("");
		boolean choice = false;
		while(Zen.isRunning()) {
			showManualButton();
			Zen.drawText("Set Up First Player:", 100, 50);
			Zen.drawText("Name: ", 100, 100);
			Zen.setFont("Times-12");
			Zen.drawText("(Type to enter your name)", 140,120);
			Zen.setFont("Times-18");
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
				String name = Zen.getEditText().trim().equals("") ? "Player 1" : Zen.getEditText();
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
			showManualButton();
			Zen.drawText("Set Up Second Player:", 100, 50);
			Zen.drawText("Name: ", 100, 100);
			Zen.drawText("Is AI?: ", 100, 150);
			Zen.setFont("Times-12");
			Zen.drawText("(Type to enter your name)", 140,120);
			Zen.setFont("Times-18");
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
			Zen.drawText("Confirm", 250, 250);
			String userInputName = Zen.getEditText();
			Zen.drawText(userInputName, 150, 100);
			Zen.flipBuffer();
			if(clickedIn(x,y,98+150,235,163+150,256)) {
				String name = Zen.getEditText().trim().equals("") ? "Player 2" : Zen.getEditText().trim();
				Player player;
				if (choice) player = new AIPlayer();
				else player = new HumanPlayer();
				player.setName(name);
				return player;
			}
		}
		return new AIPlayer();
	}
	public static void showManualButton() {
		Zen.drawText("Click me to read the Guide",400, 20);
		int x = Zen.getMouseClickX();
		int y = Zen.getMouseClickY();
		if (clickedIn(x,y,400,0,500,20)) {
			Zen.flipBuffer();
			showManual();
		}
	}
	public static void showManual() {
		int a = 60;
		int b = 30;
		int c = 30;
		Zen.setFont("Times",20);
		Zen.drawText("The Nim Game", 60, 30);
		Zen.setFont("Times",16);
		Zen.drawText("This is a game about picking stones.", c, a);
		Zen.drawText("There are some piles of stones on the ground.", c, a+b);
		Zen.drawText("Two players take turns to pick up the stones.", c, a+3*b);
		Zen.drawText("In each turn, the player should pick up at least one stone from only one pile.", c, a+4*b);
		Zen.drawText("There are no upper limit for each pick.", c, a+5*b);
		Zen.drawText("The player who picks up the last stone on the ground wins.", c, a+6*b);
		Zen.drawText("You will need to set the number of stones in each pile.", c, a+8*b);
		Zen.drawText("The AI is a perfect player when there is less than 60 stones in total.", c, a+10*b);
		Zen.drawText("Try to beat it!", c, a+11*b);
		Zen.drawText("Click anywhere to return to the last screen.", c, a+13*b);
		Zen.setFont("Times",18);
		Zen.flipBuffer();
		Zen.waitForClick();
	}
	
	public void setNumerOfPiles() {
		Zen.setEditText("");
		int p = 3;
		
		while(Zen.isRunning()) {
			showManualButton();
			
			Zen.drawText("How many piles?", 100, 50);
			for (int i = 1; i < 9; i ++) {
				Zen.drawText(i + "", 100 + (i-1)*30, 100);
			}
			int x = Zen.getMouseClickX();
			int y = Zen.getMouseClickY();
			for (int i = 1; i < 9; i ++) {
				if(clickedIn(x,y,100 + (i-1)*30 - 10,100 - 10,100 + (i-1)*30 + 10, 100 + 10)) {
					
					p = i;
				}
			}
			Zen.draw(new Rectangle(100 + (p-1)*30, 105, 10, 3));
			Zen.drawText("Confirm", 150, 250);
			if(clickedIn(x,y,150 - 10,250 - 10, 210 ,260)) {
				tempNumberOfPiles = p;
				noOfStone = new ArrayList<>();
				return;
			}
			Zen.flipBuffer();
		}
	}
	
	public void setPiles() {
		for (int i = 1; i <= tempNumberOfPiles; i++) {
			setEachPile(i);
		}
	}
	public void setEachPile(int index) {
		Zen.setEditText("");
		while(Zen.isRunning()) {
			showManualButton();
			Zen.drawText("Number of Stones for pile " + index + ": ", 100, 50);
			Zen.drawText(index + ":", 100, 100);
			Zen.setFont("Times-12");
			Zen.drawText("(Type number)", 140,120);
			Zen.setFont("Times-18");
			String userInput = Zen.getEditText();
			Zen.drawText(userInput, 150 , 100);
			Zen.drawText("Confirm", 300, 100 + (index % 2) * 30);
			int x = Zen.getMouseClickX();
			int y = Zen.getMouseClickY();
			if(clickedIn(x,y,290,100 + (index % 2) * 30 - 10,360, 120 + (index % 2) * 30)) {
				if (userInput.trim().matches("\\d+")) {
					noOfStone.add(Integer.valueOf(userInput));
					Zen.flipBuffer();
					return;
				} else {
					Zen.drawText("Please input an integer", 100, 200);
				}
			}
			Zen.flipBuffer();
		}
		
		
		
	}

	public String getString() {
		String userInput;
		while(Zen.isRunning()) {
			userInput = Zen.getEditText();
			if(userInput.contains("\n")) {
				return userInput;
			}
			Zen.flipBuffer();
		}
		return "";
	}
	
	public static boolean clickedIn(int x, int y, int x1, int y1, int x2,int y2) {
		if ( y <= y2 && y >= y1) {
			if (x <= x2 && x >= x1) {
				return true;
			}
		}
		return false;
	}
}
