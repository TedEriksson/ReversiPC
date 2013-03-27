import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ReversiPC extends PApplet {

int W = 640, H = 480;

Game game = new Game();

public void setup() {
  size(W,H);
}

public void draw() {
  game.draw();
}
class Board {

	int posX, posY, cellSize;

	Board(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
	}

	public void draw() {
		drawBoard();
	}

	public void drawBoard() {
		for(int i = 0; i < 9; i++) {
			line(posX + (cellSize * i), posY, posX + (cellSize * i), posY + (cellSize * 8));
		}
		for(int i = 0; i < 9; i++) {
			line(posX, posY + (cellSize * i), posX + (cellSize * 8), posY + (cellSize * i));
		}
	}
}
class Game {

	private Board board = new Board(50,50,50);
	public void Game() {

	}

	public void draw() {
		background(0xff008000);
		board.draw();
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
