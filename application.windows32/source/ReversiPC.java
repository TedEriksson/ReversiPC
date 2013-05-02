import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.signals.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ReversiPC extends PApplet {







int W = 1280, H = 720;

Game game;
int gameState = 1;
Button gameButton, gameAIButton, quitButton;
CheckBox soundCheck;
Background bg = new Background();

Minim minim;
AudioPlayer player;
AudioInput input;

public void setup() {
	//W = displayWidth;
	//H = displayHeight;
	size(W,H,P3D);

	minim = new Minim(this);
	player = minim.loadFile("data/dreams.mp3");
	input = minim.getLineIn();
	player.play();
	gameState = 1;
	ortho(0, width, 0, height); 
	game = new Game(false);
	gameButton = new Button("Start Game VS another Player", width/2, height/3);
	gameAIButton = new Button("Start Game VS CPU", width/2, (height/6)*3);
	quitButton = new Button("Quit Game",  width/2, (height/6)*5);
	soundCheck = new CheckBox("Sound on", "Sound off", width/2, (height/6)*4);
}

public void draw() {
	lights();

	switch(gameState) {
		case 1: mainMenu(); break;
		case 2: if(!game.draw()) gameState = 1; break;
	}
}

public void mainMenu() {
	//background(130);
	bg.draw();
	pushMatrix();
	translate(width/2, 0, 0);
		pushMatrix();
			translate(0, height/3, 0);
			gameButton.draw();
		popMatrix();
		pushMatrix();
			translate(0, (height/6)*3, 0);
			gameAIButton.draw();
		popMatrix();
		pushMatrix();
			translate(0, (height/6)*4, 0);
			soundCheck.draw();
		popMatrix();
		pushMatrix();
			translate(0, (height/6)*5, 0);
			quitButton.draw();
		popMatrix();
	popMatrix();
}

public void mousePressed() {
	game.mousePressed();
	if(gameState == 1) {
		if(gameButton.clicked()) {
			game = new Game(false);
			gameState = 2;
		} else if(gameAIButton.clicked()) {
			game = new Game(true);
			gameState = 2;
		} else if(soundCheck.clicked()) {
			if(soundCheck.isChecked()) {
				player.mute();
			} else {
				player.unmute();
			}
			soundCheck.flipTicked();
		} else if(quitButton.clicked()) {
			exit();
		}
	}
}

public void stop() {
	player.close();
	minim.stop();
	super.stop();
}

public boolean sketchFullScreen() {
	return false;
}
class BCell extends Cell {
	int cellSize = 10, state = 0;
	float rot = (random(0.001f, 0.07f)), rotX = (random(0.001f, 0.07f));
	int posX = PApplet.parseInt(random(1, 3));
	int posY = PApplet.parseInt(random(1, 3));
	boolean turning = false;

	BCell(int posX, int posY, int posZ, int cellSize) {
		super(posX,posY,cellSize);
		super.posZ = posZ;
		setup();
	}

	public void setup() {
			
	}

	public void draw() {
		if(super.posX > width + 200)
			super.posX = -PApplet.parseInt(random(200));
		if(super.posY > height + 200)
			super.posY = -PApplet.parseInt(random(200));
		drawCell();
		super.rot+=rot;
		super.rotX+=rotX;
		super.posX +=posX;
		super.posY +=posY;
	}
}
class Background {

	BCell[] bCell = new BCell[40];
	
	Background() {
		for(int i = 0; i <40;i++) {
			int posX = 1500-PApplet.parseInt(random(3000));
			
			int	posY =1500- PApplet.parseInt(random(3000));
			int posZ = -PApplet.parseInt(random(1000));
			int cellSize = PApplet.parseInt(random(100) + 10);
			
			bCell[i] = new BCell(posX,posY,posZ,cellSize);
		}
	}

	public void draw() {
		background(30);
		for(int i = 0; i <40;i++)
			bCell[i].draw();
	}
}
class Board {

	int posX, posY, cellSize;
	Cell[] cell = new Cell[64];

	Board(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
		setup();
	}

	public void setup() {
		int n = 0;
		for(int i = 0; i<8; i++) {
			for(int k = 0; k < 8; k++) {
				cell[n] = new Cell(posX + (cellSize/2) + (cellSize * k), posY + (cellSize/2) + (cellSize * i), cellSize);
				n++;
			}
		}

	}

	public void draw() {
		drawBoard();
		for(int i =0;i<64;i++) {
			cell[i].draw();
		}
	}

	public void drawBoard() {
		
		pushMatrix();
		noFill();
		strokeWeight(cellSize/12);
		translate(posX+cellSize*4, posY+cellSize*4, -cellSize/4);
		//fill(#00D000);
		box(cellSize*8, cellSize*8, cellSize/2);
		popMatrix();

noStroke();
		for(int i = 1; i < 8; i++) {
			pushMatrix();
			fill(40);
			translate(posX + (cellSize*i),(cellSize*9)/2 - cellSize/24,cellSize/24);
			box(cellSize/24,(cellSize*8) + cellSize/24,cellSize/6);
			popMatrix();
		}
		for(int i = 1; i < 8; i++) {
			pushMatrix();
			fill(40);
			translate(cellSize*4.5f - cellSize/12, posY + (cellSize*i),0);
			box(cellSize*8 + cellSize/24, cellSize/24, cellSize/6);
			popMatrix();
		}
	}

	public int mousePressed(boolean isPlayer1Turn) {
		int n = 0;
		for(int i = 0; i<8; i++) {
			for(int k = 0; k < 8; k++) {
				if (mouseX > posX + (cellSize * k) && mouseX < posX + cellSize + (cellSize * k) && 
					mouseY > posY + (cellSize * i) && mouseY < posY + cellSize + (cellSize * i)) {
					return n;
				}
				n++;
			}
		}
		return -1;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void updateCells(int[] cells) {
			for(int k = 0; k < 64; k++) {
				cell[k].setState(cells[k]);
			}
	}
}
class Button {
	String message;
	int fontSize = 32, posX, posY;
	Button(String message, int posX, int posY) {
		this.message = message;
		this.posX = posX;
		this.posY = posY;
	}

	public void draw() {
		pushMatrix();
		noStroke();
		textSize(fontSize);
		fill(230);
		if(mouseOver())
			selected();
		box((fontSize*message.length())/2 + fontSize,fontSize * 1.5f,20);
			pushMatrix();
				fill(40);
				textAlign(CENTER, CENTER);
				translate(0, 0, 10);
				text(message, 0,0,1);
			popMatrix();
		popMatrix();
	}

	public boolean clicked() {
		if(mouseOver()) {
			return true;
		}
		return false;
	}

	public void selected() {
			fill(180);
	}

	public boolean mouseOver() {
		if(mouseX > posX - ((fontSize*message.length())/4  + fontSize/2) && mouseX < posX + ((fontSize*message.length())/4  + fontSize/2) && mouseY > posY - fontSize * 1 && mouseY < posY + fontSize * 1) {
			return true;
		} else {
			return false;
		}
	}

	public void setMessage(String message) {
		this.message = message;
	} 
}
class Cell {
	int posX, posY,posZ, cellSize, state = 0, prevState = 0;
	float rot,rotX;
	boolean turning = false;

	Cell(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
		setup();
	}

	public void setup() {
		
		
	}
	//Black rot = 0
	//White rot = PI
	public void drawCell() {
		noStroke();
		pushMatrix();
		translate(0, 0, posZ);
		pushMatrix();
			translate(posX, posY, cellSize*0.15f);
			rotateY(rot);
			rotateX(rotX);
			translate(0, 0, cellSize*0.05f);
			fill(40);
			box(cellSize*0.8f, cellSize*0.8f, cellSize*0.1f);
		popMatrix();
		pushMatrix();
			translate(posX, posY, cellSize*0.15f);
			rotateY(rot);
			rotateX(rotX);
			translate(0, 0, -cellSize*0.05f);
			fill(255);
			box(cellSize*0.8f, cellSize*0.8f, cellSize*0.1f);
		popMatrix();
		popMatrix();
	}

	public void draw() {
		if(turning) {
			if(state ==2) {
				if(rot < PI) {
					rot += 0.15f;
				} else {
					rot = PI;
					turning = false;
				}
			}
			if(state ==1) {
				if(rot > 0) {
					rot -= 0.15f;
				} else {
					rot = 0;
					turning = false;
				}
			}
		} else {
			if(state == 1)
				rot = 0;
			if(state == 2)
				rot = PI;
		}

		if(state != 0)
			drawCell();	
	}

public void didTurn() {
	if(prevState != 0 && prevState != state)
		turning = true;
		prevState = state;
}

public void setState(int state) {
	prevState = this.state;
	this.state = state;
	didTurn();
}

public void swapState() {
	if(state==1) {
		state = 2;
	} else if (state == 2) {
		state = 1;
	}
}

public int getState() {
	return state;
}
}
class CheckBox extends Button {
	boolean ticked = true;
	String onMessage;
	String offMessage;

	CheckBox(String message, String offMessage, int posX, int posY) {
		super(message,posX,posY);
		this.onMessage = message;
		this.offMessage = offMessage;
	}

	public boolean isChecked() {
		if(ticked)
			return true;
		return false;
	}

	public void flipTicked() {
		if(ticked) {
			ticked = false;
			println("Sound Off!");
			setMessage(offMessage);
		} else {
			ticked = true;
			println("Sound On!");
			setMessage(onMessage);
		}
	}

	public boolean clicked() {
		if(mouseOver()) {
			return true;
		}
		return false;
	}
}
class Game {

	private Board board;
	private Scores scores;
	private boolean isPlayer1Turn = true, gameStart = true, prevGoDidFail = false, banPlay = true;
	private int[] cells = new int[64];
	private int startHeight = height*7;
	private boolean singlePlayer = false, endGame = false;
	Button mainMenu;
	WaitTimer timer = new WaitTimer() {
		public void finished() {
			super.finished();
			cpuPlay();
		}
	};
	
	PopUp endGamePop = new PopUp() {
		public void show() {
			super.show();
			banPlay = true;
		}

		public void hide() {
			super.hide();
			banPlay = false;
		}
		public void button1Pressed() {
			super.button1Pressed();
			endGame();
		}

		public void button2Pressed() {
			super.button2Pressed();
			hide();
		}
	};
	
	Game(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
		setup();
	}

	public void setup() {
		

		board = new Board(height/20,height/20, height/9);
		scores = new Scores(height,height/20,width,height,height/9);
		//starting positions
		cells[27] = 2;
		cells[28] = 1;
		cells[35] = 1;
		cells[36] = 2;
		endGamePop.setUp("Game Over","Player Won","Main Menu", "Cancel");
		mainMenu = new Button("Main Menu",(height/9)*10,(height/8)*7);
		updateStuff();
		banPlay =false;
	}

	public boolean draw() {

		background(0xff448da1);
		pointLight(20, 30, 200, 50, 50, 100);
		timer.draw();
		board.draw();
		scores.drawScores();
		endGamePop.draw();
		if(gameStart) {
			if(startHeight > height/2) {
				camera(width/2, startHeight, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
				startHeight -= 50;
			} else {
				gameStart = false;
				camera(width/2, height/2, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
			}
		}

		//draw buttons
		pushMatrix();
			translate((height/9)*10 , (height/8)*7, 0);
			mainMenu.draw();
		popMatrix();

		if(endGame) {
			return false;
		}
		return true;
	}

	public void endGame() {
		endGame = true;
	}

	public void cpuPlay() {
		Queue queue = new Queue();
		for(int i = 0; i < 64; i++) {
			if(validMove(i,2) != null && cells[i] == 0) {
				queue.add(i);
			}
		}
		int bestMove = -1;
		int bestMoveValue = 0;
		while(!queue.isEmpty()) {
			int move = queue.remove();
			int[] tempBoard = validMove(move,2);
			int moveValue = 0;
			for(int i = 0; i<64; i++) {
				if(cells[i] != 2 && tempBoard[i] == 2) {
					moveValue++;
				}
			}
			if(moveValue > bestMoveValue) {
				bestMove = move;
				bestMoveValue = moveValue;
			}
		}
		if(bestMove != -1) {
			int[] tempCells = validMove(bestMove,2);
			if (tempCells != null) {
				cells = tempCells;
			}
		}
		endTurn();
	}

	public void mousePressed() {
		if(mainMenu.clicked()) {
			endGame();
		}
		endGamePop.mousePressed();
		playMove();
	}

	public void updateStuff() {
		board.updateCells(cells);
		int scoreP1 = 0, scoreP2 = 0;
		for(int i = 0; i <64; i++) {
			if(cells[i] == 1) {
				scoreP1++;
			} else if (cells[i] == 2) {
				scoreP2++;
			}
		}
		scores.setScores(scoreP1,scoreP2);
	}

	private void endTurn() {
		isPlayer1Turn = !isPlayer1Turn;
		updateStuff();
		scores.setTurn(isPlayer1Turn);
		if(checkWinState()) {
			if(prevGoDidFail) {
				println("WIN");
				endGamePop.setMessage((scores.getPlayer1Score() > scores.getPlayer2Score() ? "Player 1 Wins!" : (scores.getPlayer1Score() == scores.getPlayer2Score() ? "It's a Draw!" : "Player 2 Wins!")));
				endGamePop.show();
			} else {
				prevGoDidFail = true;
				endTurn();
			}
		} else {
			prevGoDidFail = false;
		}
		if(!banPlay && !isPlayer1Turn && singlePlayer) {
			timer.start(PApplet.parseInt(random(500, 2000)));
		}
	}

	private boolean checkWinState() {
		if(anyValidMoves(isPlayer1Turn?1:2))
			return false;
		return true;
	}

	private boolean emptyCells() {
		boolean emptyCells = false;

		for (int i = 0; i <64; i++) {
			if(cells[i] == 0)
				emptyCells = true;
		}

		return emptyCells;
	}

	private boolean anyValidMoves(int playerNum) {
		boolean movesLeft = false;

		for (int i = 0; i <64; i++) {
			int[] temp = validMove(i,playerNum);
			if(temp != null)
				movesLeft = true;	
		}
		return movesLeft;
	}

	private void playMove() {
		boolean moveMade = false;
		if(!banPlay && !gameStart && ((singlePlayer && isPlayer1Turn) || !singlePlayer)) {
			int cell = board.mousePressed(isPlayer1Turn); //Pressed Postition is passed here
			if(cell != -1 ) {
				int[] tempCells = validMove(cell, isPlayer1Turn ? 1 : 2);
				if (tempCells != null) {
					moveMade = true;
					cells = tempCells;
				} else {
					println("Not Valid Move");
				}
			}
		}

		if(moveMade)
			endTurn();
	}

	private int[] validMove(int cell, int playerNum) {
		boolean moveMade = false;
		int inverse = (playerNum == 1 ? 2 : 1); 
		int[] tempCells = new int[64];
		for(int i = 0; i <64; i++) {
			tempCells[i] = cells[i];
		}
		if(tempCells[cell] == 0) {
			for (int direction = 1; direction < 9; direction++) {
				int tempCell = cell;
				Queue queue = new Queue();
				while(tempCell != -1) {
					queue.add(tempCell);
					tempCell = checkValidMoveDirection(direction,tempCell);
					if(tempCell != -1 && tempCells[tempCell] == playerNum) {
						queue.add(tempCell);
						tempCell = -1;
					} else if (tempCell != -1 && tempCells[tempCell] == 0) {
						tempCell = -1;
					}
				}

				if(queue.size() > 2 && tempCells[queue.getLast()] == playerNum && tempCells[queue.remove()] == 0) {
					while (!queue.isEmpty()){
						tempCells[queue.remove()] = playerNum;
					}
					moveMade = true;
				}
			}
		}
		if(!moveMade) {
			return null;
		}
		tempCells[cell] = playerNum;
		return tempCells; 
	}

	private int checkValidMoveDirection(int direction, int position) {
		switch (direction) {
			case 1:
				if (Math.floor((float) position / 8) != 0) {
					return position - 8;
				}
				break;
			case 2:
				if (Math.floor((float) position / 8) != 0
						&& position % 8 != 8 - 1) {
					return position - 8 + 1;
				}
				break;
			case 3:
				if (position % 8 != 8 - 1

				) {
					return position + 1;
				}
				break;
			case 4:
				if (position % 8 != 8 - 1
						&& Math.floor((float) position / 8) != 8 - 1) {
					return position + 8 + 1;
				}
				break;
			case 5:
				if (Math.floor((float) position / 8) != 8 - 1) {
					return position + 8;
				}
				break;
			case 6:
				if (position % 8 != 0
						&& Math.floor((float) position / 8) != 8 - 1) {
					return position + 8 - 1;
				}
				break;
			case 7:
				if (position % 8 != 0) {
					return position - 1;
				}
				break;
			case 8:
				if (Math.floor((float) position / 8) != 0
						&& position % 8 != 0) {
					return position - 8 - 1;
				}
				break;
		}

		return -1;
	}
}
class MainMenu {
	

	MainMenu() {
		
	}

	public boolean draw() {
		
		return true;
	}
}
class PopUp {
	String title, message, button1, button2;
	boolean oneButton = true, showPopUp = false, ready = false;
	Button b1, b2;

	PopUp() {
		
	}

	public void setUp(String title, String message, String button1, String button2) {
		this.title = title;
		this.message = message;
		this.button1 = button1;
		this.button2 = button2;
		b1  = new Button(button1,width/2 + width/12,height/2 + height/9);
		if (!button2.equals("")) {
			b2  = new Button(button2,width/2 - width/12,height/2 + height/9);
			oneButton = false;
		}
		ready = true;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}

	public void show() {
		showPopUp = true;
	}

	public void hide() {
		showPopUp = false;
	}

	public void button1Pressed() {
		println("clicked button 1");
	}

	public void button2Pressed() {
		println("clicked button 2");
	}

	public void mousePressed() {
		if(showPopUp) {
			if(b1.clicked())
				button1Pressed();
			if(!oneButton) {
				if(b2.clicked())
					button2Pressed();
			}
		}
	}

	public void draw() {
		if(showPopUp && ready) {
			pushMatrix();
				translate(width/2, height/2, 100);
				pushMatrix();
					fill(40);
					box(width/3,height/3,30);
					pushMatrix();
						fill(255);
						textAlign(CENTER,CENTER);
						textSize(60);
						text(title,0,-(height/9),50);
					popMatrix();
					pushMatrix();
						fill(255);
						textAlign(CENTER,CENTER);
						textSize(30);
						text(message,0,0,50);
					popMatrix();
				popMatrix();

				pushMatrix();
					translate((width/12),height/9 , 20);
					fill(255);
					b1.draw();
				popMatrix();

				if(!oneButton) {
				pushMatrix();
					translate(-(width/12),height/9 , 20);
					fill(255);
					b2.draw();
				popMatrix();
				}
			popMatrix();
		}
	}
}
class Queue {
	
	ArrayList<Integer> queue = new ArrayList();

	Queue() {

	}

	public void add(int pos) {
		queue.add(pos);
	}

	public int remove() {
		return queue.remove(0);
	}

	public int getStart() {
		return queue.get(0);
	}

	public int getLast() {
		return queue.get(queue.size()-1);
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public int size() {
		return queue.size();
	}
}
class Scores {
	private int posX, posY, width, height, cellSize;
	float rotX,rotY;
	Cell player1, player2;

	int player1Score = 2, player2Score = 2;

	String player1Name = "Player 1", player2Name = "Player 2";

	boolean isPlayer1Turn = true;
	
	Scores(int posX, int posY, int width, int height, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.cellSize = cellSize;
		player1 = new Cell(posX + cellSize/2,posY + cellSize/2 ,cellSize);
		player1.setState(1);
		player2 = new Cell(posX + cellSize/2,posY + cellSize + cellSize/2 ,cellSize);
		player2.setState(2);
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setScores(int score1, int score2) {
		player1Score = score1;
		player2Score = score2;

	}

	public void setTurn(boolean p1Turn) {
		isPlayer1Turn = p1Turn;
	}

	public void drawScores() {
		//Draw Player 1 Stuff
		rectMode(CORNER);
		rect(posX + cellSize/2, posY + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6f);
		player1.draw();

		textSize(cellSize/2);
		fill(255);
		rectMode(CENTER);
		pushMatrix();
		translate(posX + cellSize/2, posY + cellSize /2, cellSize*0.3f);
		textAlign(CENTER, CENTER);
		text(Integer.toString(player1Score), 0, 0,cellSize,cellSize);
		popMatrix();

		textSize(cellSize/4);
		fill(255);
		rectMode(CENTER);
		pushMatrix();
		translate(posX + cellSize*2, posY + cellSize /2, cellSize/10);
		textAlign(LEFT, CENTER);
		text(player1Name, 0, 0,cellSize*2,cellSize);
		popMatrix();


		//Draw Player 2 Stuff
		rectMode(CORNER);
		rect(posX + cellSize/2, posY + cellSize + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6f);
		player2.draw();

		textSize(cellSize/2);
		fill(40);
		rectMode(CENTER);
		pushMatrix();
		translate(posX + cellSize/2, posY + cellSize /2 + cellSize, cellSize*0.3f);
		textAlign(CENTER, CENTER);
		text(Integer.toString(player2Score), 0, 0,cellSize,cellSize);
		popMatrix();

		textSize(cellSize/4);
		fill(40);
		rectMode(CENTER);
		pushMatrix();
		translate(posX + cellSize*2, posY + cellSize + cellSize /2, cellSize/10);
		textAlign(LEFT, CENTER);
		text(player2Name, 0, 0,cellSize*2,cellSize);
		popMatrix();

		pushMatrix();
		rotY += 0.03f;
		rotX += 0.03f;
		if(rotY > 2*PI) {
			rotY = 0;
			rotX = 0;
		}
		translate(posX + cellSize*2.5f, posY + (isPlayer1Turn ? 0 : cellSize) + cellSize /2, cellSize/10);
		rotateX(rotX);
		rotateY(rotY);
		box(25, 25, 25);
		popMatrix();

	}
}
class WaitTimer {
	int endTime, millsLeft;
	boolean go = false;

	WaitTimer() {
	}

	public void start(int endTime) {
		this.endTime = millis() + endTime;
		go = true;
	}

	public boolean checkFinished() {
		if(millis() >= endTime) {
			//println("Counting");
			return true;
		}
		return false;
	}

	public void draw() {
		if(checkFinished() && go)
			finished();
	}

	public void finished() {
		go=false;
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ReversiPC" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
