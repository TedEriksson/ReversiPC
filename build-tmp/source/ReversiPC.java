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
	size(W,H,P3D);
	game = new Game();
	ortho(0, width, 0, height); 
	
}

public void draw() {
	lights();
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
		pushMatrix();
		translate(posX+cellSize*4, posY+cellSize*4, -cellSize/4);
		fill(0xff00D000);
		box(cellSize*8, cellSize*8, cellSize/2);
		popMatrix();
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
				cell[n].setState(cells[k][i]);
				n++;
			}
		}
	}
}
class Cell {
	int posX, posY, cellSize, state = 0, prevState = 0;
	float rot;
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
		translate(posX, posY, cellSize*0.15f);
		rotateY(rot);
		translate(0, 0, cellSize*0.05f);
		fill(40);
		box(cellSize*0.8f, cellSize*0.8f, cellSize*0.1f);
		popMatrix();
		pushMatrix();
		translate(posX, posY, cellSize*0.15f);
		rotateY(rot);
		translate(0, 0, -cellSize*0.05f);
		fill(255);
		box(cellSize*0.8f, cellSize*0.8f, cellSize*0.1f);
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
class Game {

	private Board board;
	private Scores scores;
	private boolean isPlayer1Turn = true, gameStart = true;
	private int[][] cells = new int[8][8];
	private int startHeight = height*7;
	
	Game() {
		setup();
	}

	public void setup() {
		board = new Board(height/20,height/20, height/9);
		scores = new Scores(9*height/9,height/20,width,height,height/9);
		//starting positions
		cells[3][3] = 2;
		cells[3][4] = 1;
		cells[4][3] = 1;
		cells[4][4] = 2;
		board.updateCells(cells);
	}

	public void draw() {
		background(0xff008000);
		board.draw();
		scores.drawScores();
		if(gameStart) {
			if(startHeight > height/2) {
				camera(width/2, startHeight, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
				startHeight -= 50;
			} else {
				gameStart = false;
				camera(width/2, height/2, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
			}
		}
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
		if(cell != -1 && cells[cell%8][cell/8] == 0) {
			cells[cell%8][cell/8] = isPlayer1Turn ? 1 : 2;
			moveMade = true;
		} else {
			cells[cell%8][cell/8] = isPlayer1Turn ? 1 : 2;
			moveMade = true;
		}

		if(moveMade)
			endTurn();
	}

	/*int[][] validMove(int cell) {
		int cellX = cell%8, cellY = cell/8;
		int[][] tempCells = cells;
		if(cellX - 1 == (isPlayer1Turn ? 2 : 1)) {
			int[] tempCell = {cellX-1, cellY};
			ArrayList queue = new ArrayList();
			while(tempCells[tempCell[0]][tempCell[1]] == (isPlayer1Turn ? 2 : 1)) {
				queue.add(tempCell);
				tempCell[0]--;
			}
			if(tempCells[tempCell[0]][tempCell[1]] == (isPlayer1Turn ? 1 : 2))
				queue.add(tempCell);

			//if(queue.get(0) == 0 && queue.get(queue.length-1) == (isPlayer1Turn ? 1 : 2)) {
				while(!queue.isEmpty()) {
					
				}
			//}
		}
	}*/
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
