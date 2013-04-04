int W = 640, H = 480;

Game game;

void setup() {
	W = displayWidth;
	H = displayHeight;
	size(W,H);
	game = new Game(W,H);
}

void draw() {
	game.draw();
}

void mousePressed() {
	game.mousePressed();
}

boolean sketchFullScreen() {
	return true;
}