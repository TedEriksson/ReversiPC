class Board {

	int posX, posY, cellSize;

	Board(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
	}

	void draw() {
		drawBoard();
	}

	void drawBoard() {
		for(int i = 0; i < 9; i++) {
			line(posX + (cellSize * i), posY, posX + (cellSize * i), posY + (cellSize * 8));
		}
		for(int i = 0; i < 9; i++) {
			line(posX, posY + (cellSize * i), posX + (cellSize * 8), posY + (cellSize * i));
		}
	}
}