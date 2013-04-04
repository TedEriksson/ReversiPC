import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ReversiPC extends PApplet {

int W = 640, H = 480;

Game game;

public void setup() {
	W = displayWidth;
	H = displayHeight;
	size(W,H);
	game = new Game(W,H);
}

public void draw() {
	game.draw();
}

public void mousePressed() {
	game.mousePressed();
}

public boolean sketchFullScreen() {
	return true;
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
				cell[n] = new Cell(posX + (cellSize/2) + (cellSize * i), posY + (cellSize/2) + (cellSize * k), cellSize);
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
		stroke(0);
		for(int i = 0; i < 9; i++) {
			line(posX + (cellSize * i), posY, posX + (cellSize * i), posY + (cellSize * 8));
		}
		for(int i = 0; i < 9; i++) {
			line(posX, posY + (cellSize * i), posX + (cellSize * 8), posY + (cellSize * i));
		}
	}

	public int mousePressed(boolean isPlayer1Turn) {
		int n = 0;
		for(int i = 0; i<8; i++) {
			for(int k = 0; k < 8; k++) {
				if (mouseX > posX + (cellSize * i) && mouseX < posX + cellSize + (cellSize * i) && 
					mouseY > posY + (cellSize * k) && mouseY < posY + cellSize + (cellSize * k)) {
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

	public void updateCells(int[][] cells) {
		int n = 0;
		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {
				cell[n].setState(cells[i][k]);
				n++;
			}
		}
	}
}
class Cell {
	int posX, posY, cellSize, state = 0;

	Cell(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
	}

	public void draw() {
		ellipseMode(CENTER);
		switch(state) {
			case 1:
				stroke(255);
				fill(0xff000000);
				ellipse(posX, posY, (cellSize*0.8f), (cellSize*0.8f));
				break;
			case 2:
				stroke(0);
				fill(0xffFFFFFF);
				ellipse(posX, posY, (cellSize*0.8f), (cellSize*0.8f));
				break;
		}

		
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
}
class Game {

	private int W,H;
	private Board board;
	private Scores scores;
	private boolean isPlayer1Turn = true;
	private int[][] cells = new int[8][8];
	
	Game(int W, int H) {
		this.W = W;
		this.H = H;	
		setup();
	}

	public void setup() {
		board = new Board(H/20,H/20, H/9);
		scores = new Scores(9*H/9,H/20,W,H,H/9);
	}

	public void draw() {
		background(0xff008000);
		board.draw();
		scores.drawScores();
	}

	public void mousePressed() {
		playMove();
	}

	private void endTurn() {
		isPlayer1Turn = !isPlayer1Turn;
		board.updateCells(cells);
	}

	private void playMove() {
		boolean moveMade = false;
		int cell = board.mousePressed(isPlayer1Turn);
		if(cell != -1 && cells[cell/8][cell%8] == 0) {
			cells[cell/8][cell%8] = isPlayer1Turn ? 1 : 2;
			moveMade = true;
		}

		if(moveMade)
			endTurn();
	}
}
class Scores {
	private int posX, posY, width, height, cellSize;
	Cell player1, player2;
	
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

	public void drawScores() {
		rect(posX + cellSize/2, posY + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6f);
		player1.draw();

		rect(posX + cellSize/2, posY + cellSize + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6f);
		player2.draw();
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
