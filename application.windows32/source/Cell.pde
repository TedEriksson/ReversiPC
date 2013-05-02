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

	void setup() {
		
		
	}
	//Black rot = 0
	//White rot = PI
	void drawCell() {
		noStroke();
		pushMatrix();
		translate(0, 0, posZ);
		pushMatrix();
			translate(posX, posY, cellSize*0.15);
			rotateY(rot);
			rotateX(rotX);
			translate(0, 0, cellSize*0.05);
			fill(40);
			box(cellSize*0.8, cellSize*0.8, cellSize*0.1);
		popMatrix();
		pushMatrix();
			translate(posX, posY, cellSize*0.15);
			rotateY(rot);
			rotateX(rotX);
			translate(0, 0, -cellSize*0.05);
			fill(255);
			box(cellSize*0.8, cellSize*0.8, cellSize*0.1);
		popMatrix();
		popMatrix();
	}

	void draw() {
		if(turning) {
			if(state ==2) {
				if(rot < PI) {
					rot += 0.15;
				} else {
					rot = PI;
					turning = false;
				}
			}
			if(state ==1) {
				if(rot > 0) {
					rot -= 0.15;
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

void didTurn() {
	if(prevState != 0 && prevState != state)
		turning = true;
		prevState = state;
}

void setState(int state) {
	prevState = this.state;
	this.state = state;
	didTurn();
}

void swapState() {
	if(state==1) {
		state = 2;
	} else if (state == 2) {
		state = 1;
	}
}

int getState() {
	return state;
}
}
