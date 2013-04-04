class Game {

	private int W,H;
	private Board board;
	private Scores scores;
	private boolean isPlayer1Turn = true;
	private int[][] cells = new int[8][8];
	
	Game(int W, int H) {
		this.W = W;
		this.H = H;	
		setup();
	}

	void setup() {
		board = new Board(H/20,H/20, H/9);
		scores = new Scores(9*H/9,H/20,W,H,H/9);
	}

	void draw() {
		background(#008000);
		board.draw();
		scores.drawScores();
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
		if(cell != -1 && cells[cell/8][cell%8] == 0) {
			cells[cell/8][cell%8] = isPlayer1Turn ? 1 : 2;
			moveMade = true;
		}

		if(moveMade)
			endTurn();
	}
}