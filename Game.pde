class Game {

	private Board board;
	private Scores scores;
	private boolean isPlayer1Turn = true, gameStart = true;
	private int[][] cells = new int[8][8];
	private int startHeight = height*7;
	
	Game() {
		setup();
	}

	void setup() {
		board = new Board(height/20,height/20, height/9);
		scores = new Scores(9*height/9,height/20,width,height,height/9);
		//starting positions
		cells[3][3] = 2;
		cells[3][4] = 1;
		cells[4][3] = 1;
		cells[4][4] = 2;
		board.updateCells(cells);
	}

	void draw() {
		background(#008000);
		board.draw();
		scores.drawScores();
		if(gameStart) {
			if(startHeight > height/2) {
				camera(width/2, startHeight, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
				startHeight -= 50;
			} else {
				gameStart = false;
				camera(width/2, height/2, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
			}
		}
	}

	void mousePressed() {
		playMove();
	}

	private void endTurn() {
		isPlayer1Turn = !isPlayer1Turn;
		board.updateCells(cells);
	}

	private void playMove() {
		boolean moveMade = false;
		int cell = board.mousePressed(isPlayer1Turn);
		if(cell != -1 && cells[cell%8][cell/8] == 0) {
			cells[cell%8][cell/8] = isPlayer1Turn ? 1 : 2;
			moveMade = true;
		} else {
			cells[cell%8][cell/8] = isPlayer1Turn ? 1 : 2;
			moveMade = true;
		}

		if(moveMade)
			endTurn();
	}

	/*int[][] validMove(int cell) {
		int cellX = cell%8, cellY = cell/8;
		int[][] tempCells = cells;
		if(cellX - 1 == (isPlayer1Turn ? 2 : 1)) {
			int[] tempCell = {cellX-1, cellY};
			ArrayList queue = new ArrayList();
			while(tempCells[tempCell[0]][tempCell[1]] == (isPlayer1Turn ? 2 : 1)) {
				queue.add(tempCell);
				tempCell[0]--;
			}
			if(tempCells[tempCell[0]][tempCell[1]] == (isPlayer1Turn ? 1 : 2))
				queue.add(tempCell);

			//if(queue.get(0) == 0 && queue.get(queue.length-1) == (isPlayer1Turn ? 1 : 2)) {
				while(!queue.isEmpty()) {
					
				}
			//}
		}
	}*/
}