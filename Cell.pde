class Cell {
	int posX, posY, cellSize, state = 0;

	Cell(int posX, int posY, int cellSize) {
		this.posX = posX;
		this.posY = posY;
		this.cellSize = cellSize;
	}

	void draw() {
		ellipseMode(CENTER);
		switch(state) {
			case 1:
				stroke(255);
				fill(#000000);
				ellipse(posX, posY, (cellSize*0.8), (cellSize*0.8));
				break;
			case 2:
				stroke(0);
				fill(#FFFFFF);
				ellipse(posX, posY, (cellSize*0.8), (cellSize*0.8));
				break;
		}

		
	}

	void setState(int state) {
		this.state = state;
	}

	int getState() {
		return state;
	}
}