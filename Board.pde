class Board {

	int posX, posY, cellSize;
	Cell[] cell = new Cell[64];

	Board(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
		setup();
	}

	void setup() {
		int n = 0;
		for(int i = 0; i<8; i++) {
			for(int k = 0; k < 8; k++) {
				cell[n] = new Cell(posX + (cellSize/2) + (cellSize * i), posY + (cellSize/2) + (cellSize * k), cellSize);
				n++;
			}
		}
	}

	void draw() {
		drawBoard();
		for(int i =0;i<64;i++) {
			cell[i].draw();
		}
	}

	void drawBoard() {
		stroke(0);
		for(int i = 0; i < 9; i++) {
			line(posX + (cellSize * i), posY, posX + (cellSize * i), posY + (cellSize * 8));
		}
		for(int i = 0; i < 9; i++) {
			line(posX, posY + (cellSize * i), posX + (cellSize * 8), posY + (cellSize * i));
		}
	}

	int mousePressed(boolean isPlayer1Turn) {
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

	int getPosX() {
		return posX;
	}

	int getPosY() {
		return posY;
	}

	void updateCells(int[][] cells) {
		int n = 0;
		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {
				cell[n].setState(cells[i][k]);
				n++;
			}
		}
	}
}