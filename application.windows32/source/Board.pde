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
				cell[n] = new Cell(posX + (cellSize/2) + (cellSize * k), posY + (cellSize/2) + (cellSize * i), cellSize);
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
			translate(cellSize*4.5 - cellSize/12, posY + (cellSize*i),0);
			box(cellSize*8 + cellSize/24, cellSize/24, cellSize/6);
			popMatrix();
		}
	}

	int mousePressed(boolean isPlayer1Turn) {
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

	int getPosX() {
		return posX;
	}

	int getPosY() {
		return posY;
	}

	void updateCells(int[] cells) {
			for(int k = 0; k < 64; k++) {
				cell[k].setState(cells[k]);
			}
	}
}
