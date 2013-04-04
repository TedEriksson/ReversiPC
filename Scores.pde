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

	void drawScores() {
		rect(posX + cellSize/2, posY + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6);
		player1.draw();

		rect(posX + cellSize/2, posY + cellSize + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6);
		player2.draw();
	}
}