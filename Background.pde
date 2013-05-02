class Background {

	BCell[] bCell = new BCell[40];
	
	Background() {
		for(int i = 0; i <40;i++) {
			int posX = 1500-int(random(3000));
			
			int	posY =1500- int(random(3000));
			int posZ = -300 - int(random(1000));
			int cellSize = int(random(100) + 10);
			
			bCell[i] = new BCell(posX,posY,posZ,cellSize);
		}
	}

	void draw() {
		background(30);
		for(int i = 0; i <40;i++)
			bCell[i].draw();
	}
}