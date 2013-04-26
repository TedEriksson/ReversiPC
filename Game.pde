class Game {

	private Board board;
	private Scores scores;
	private boolean isPlayer1Turn = true, gameStart = true, prevGoDidFail = false, banPlay = true;
	private int[] cells = new int[64];
	private int startHeight = height*7;
	private boolean singlePlayer = false, endGame = false;
	Button mainMenu;
	WaitTimer timer = new WaitTimer() {
		void draw() {

		}
	};
	
	PopUp endGamePop = new PopUp() {
		void show() {
			super.show();
			banPlay = true;
		}

		void hide() {
			super.hide();
			banPlay = false;
		}
		void button1Pressed() {
			super.button1Pressed();
			endGame();
		}

		void button2Pressed() {
			super.button2Pressed();
			hide();
		}
	};
	
	Game(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
		setup();
	}

	void setup() {
		

		board = new Board(height/20,height/20, height/9);
		scores = new Scores(height,height/20,width,height,height/9);
		//starting positions
		cells[27] = 2;
		cells[28] = 1;
		cells[35] = 1;
		cells[36] = 2;
		endGamePop.setUp("Game Over","Player Won","Main Menu", "Cancel");
		mainMenu = new Button("Main Menu",(height/9)*10,(height/8)*7);
		updateStuff();
		banPlay =false;
	}

	boolean draw() {
		background(#008000);
		board.draw();
		scores.drawScores();
		endGamePop.draw();
		if(gameStart) {
			if(startHeight > height/2) {
				camera(width/2, startHeight, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
				startHeight -= 50;
			} else {
				gameStart = false;
				camera(width/2, height/2, (height/2) / tan(PI/6), width/2, height/2, 0, 0, 1, 0);
			}
		}

		//draw buttons
		pushMatrix();
			translate((height/9)*10 , (height/8)*7, 0);
			mainMenu.draw();
		popMatrix();

		if(endGame) {
			return false;
		}
		return true;
	}

	void endGame() {
		endGame = true;
	}

	void cpuPlay() {
		Queue queue = new Queue();
		for(int i = 0; i < 64; i++) {
			if(validMove(i,2) != null && cells[i] == 0) {
				queue.add(i);
			}
		}
		int bestMove = -1;
		int bestMoveValue = 0;
		while(!queue.isEmpty()) {
			int move = queue.remove();
			int[] tempBoard = validMove(move,2);
			int moveValue = 0;
			for(int i = 0; i<64; i++) {
				if(cells[i] != 2 && tempBoard[i] == 2) {
					moveValue++;
				}
			}
			if(moveValue > bestMoveValue) {
				bestMove = move;
				bestMoveValue = moveValue;
			}
		}
		if(bestMove != -1) {
			int[] tempCells = validMove(bestMove,2);
			if (tempCells != null) {
				cells = tempCells;
			}
		}
		endTurn();
	}

	void mousePressed() {
		if(mainMenu.clicked()) {
			endGame();
		}
		endGamePop.mousePressed();
		playMove();
	}

	void updateStuff() {
		board.updateCells(cells);
		int scoreP1 = 0, scoreP2 = 0;
		for(int i = 0; i <64; i++) {
			if(cells[i] == 1) {
				scoreP1++;
			} else if (cells[i] == 2) {
				scoreP2++;
			}
		}
		scores.setScores(scoreP1,scoreP2);
	}

	private void endTurn() {
		isPlayer1Turn = !isPlayer1Turn;
		updateStuff();
		scores.setTurn(isPlayer1Turn);
		if(checkWinState()) {
			if(prevGoDidFail) {
				println("WIN");
				endGamePop.setMessage((scores.getPlayer1Score() > scores.getPlayer2Score() ? "Player 1 Wins!" : (scores.getPlayer1Score() == scores.getPlayer2Score() ? "It's a Draw!" : "Player 2 Wins!")));
				endGamePop.show();
			} else {
				prevGoDidFail = true;
				endTurn();
			}
		} else {
			prevGoDidFail = false;
		}
		if(!banPlay && !isPlayer1Turn && singlePlayer) {
			cpuPlay();
		}
	}

	private boolean checkWinState() {
		if(anyValidMoves(isPlayer1Turn?1:2))
			return false;
		return true;
	}

	private boolean emptyCells() {
		boolean emptyCells = false;

		for (int i = 0; i <64; i++) {
			if(cells[i] == 0)
				emptyCells = true;
		}

		return emptyCells;
	}

	private boolean anyValidMoves(int playerNum) {
		boolean movesLeft = false;

		for (int i = 0; i <64; i++) {
			int[] temp = validMove(i,playerNum);
			if(temp != null)
				movesLeft = true;	
		}
		return movesLeft;
	}

	private void playMove() {
		boolean moveMade = false;
		if(!banPlay && !gameStart) {
			int cell = board.mousePressed(isPlayer1Turn); //Pressed Postition is passed here
			if(cell != -1 ) {
				int[] tempCells = validMove(cell, isPlayer1Turn ? 1 : 2);
				if (tempCells != null) {
					moveMade = true;
					cells = tempCells;
				} else {
					println("Not Valid Move");
				}
			}
		}

		if(moveMade)
			endTurn();
	}

	private int[] validMove(int cell, int playerNum) {
		boolean moveMade = false;
		int inverse = (playerNum == 1 ? 2 : 1); 
		int[] tempCells = new int[64];
		for(int i = 0; i <64; i++) {
			tempCells[i] = cells[i];
		}
		if(tempCells[cell] == 0) {
			for (int direction = 1; direction < 9; direction++) {
				int tempCell = cell;
				Queue queue = new Queue();
				while(tempCell != -1) {
					queue.add(tempCell);
					tempCell = checkValidMoveDirection(direction,tempCell);
					if(tempCell != -1 && tempCells[tempCell] == playerNum) {
						queue.add(tempCell);
						tempCell = -1;
					} else if (tempCell != -1 && tempCells[tempCell] == 0) {
						tempCell = -1;
					}
				}

				if(queue.size() > 2 && tempCells[queue.getLast()] == playerNum && tempCells[queue.remove()] == 0) {
					while (!queue.isEmpty()){
						tempCells[queue.remove()] = playerNum;
					}
					moveMade = true;
				}
			}
		}
		if(!moveMade) {
			return null;
		}
		tempCells[cell] = playerNum;
		return tempCells; 
	}

	private int checkValidMoveDirection(int direction, int position) {
		switch (direction) {
			case 1:
				if (Math.floor((float) position / 8) != 0) {
					return position - 8;
				}
				break;
			case 2:
				if (Math.floor((float) position / 8) != 0
						&& position % 8 != 8 - 1) {
					return position - 8 + 1;
				}
				break;
			case 3:
				if (position % 8 != 8 - 1

				) {
					return position + 1;
				}
				break;
			case 4:
				if (position % 8 != 8 - 1
						&& Math.floor((float) position / 8) != 8 - 1) {
					return position + 8 + 1;
				}
				break;
			case 5:
				if (Math.floor((float) position / 8) != 8 - 1) {
					return position + 8;
				}
				break;
			case 6:
				if (position % 8 != 0
						&& Math.floor((float) position / 8) != 8 - 1) {
					return position + 8 - 1;
				}
				break;
			case 7:
				if (position % 8 != 0) {
					return position - 1;
				}
				break;
			case 8:
				if (Math.floor((float) position / 8) != 0
						&& position % 8 != 0) {
					return position - 8 - 1;
				}
				break;
		}

		return -1;
	}
}