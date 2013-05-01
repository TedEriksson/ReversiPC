class BCell extends Cell {
	int cellSize = 10, state = 0;
	float rot = (random(0.001, 0.07)), rotX = (random(0.001, 0.07));
	int posX = int(random(1, 3));
	int posY = int(random(1, 3));
	boolean turning = false;

	BCell(int posX, int posY, int posZ, int cellSize) {
		super(posX,posY,cellSize);
		super.posZ = posZ;
		setup();
	}

	void setup() {
			
	}

	void draw() {
		if(super.posX > width + 200)
			super.posX = -int(random(200));
		if(super.posY > height + 200)
			super.posY = -int(random(200));
		drawCell();
		super.rot+=rot;
		super.rotX+=rotX;
		super.posX +=posX;
		super.posY +=posY;
	}
}