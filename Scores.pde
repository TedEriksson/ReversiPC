class Scores {
	private int posX, posY, width, height, cellSize;
	Cell player1, player2;

	int player1Score = 2, player2Score = 2;

	String player1Name = "Ted", player2Name = "CPU";

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

	int getPlayer1Score() {
		return player1Score;
	}

	int getPlayer2Score() {
		return player2Score;
	}

	void setScores(int score1, int score2) {
		player1Score = score1;
		player2Score = score2;

	}

	void setTurn(boolean p1Turn) {
		isPlayer1Turn = p1Turn;
	}

	void drawScores() {
		//Draw Player 1 Stuff
		rectMode(CORNER);
		rect(posX + cellSize/2, posY + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6);
		player1.draw();

		textSize(cellSize/2);
		fill(255);
		rectMode(CENTER);
		pushMatrix();
		translate(posX + cellSize/2, posY + cellSize /2, cellSize*0.3);
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
		rect(posX + cellSize/2, posY + cellSize + cellSize/5 , (cellSize/2)*5 ,cellSize*0.6);
		player2.draw();

		textSize(cellSize/2);
		fill(40);
		rectMode(CENTER);
		pushMatrix();
		translate(posX + cellSize/2, posY + cellSize /2 + cellSize, cellSize*0.3);
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
		translate(posX + cellSize*3, posY + (isPlayer1Turn ? 0 : cellSize) + cellSize /2, cellSize/10);
		box(50, 50, 50);
		popMatrix();

	}
}