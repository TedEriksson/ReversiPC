int W = 640, H = 480;

Game game;

void setup() {
	W = displayWidth;
	H = displayHeight;
	size(W,H,P3D);
	game = new Game();
	ortho(0, width, 0, height); 
	
}

void draw() {
	lights();
	game.draw();
}

void mousePressed() {
	game.mousePressed();
}

boolean sketchFullScreen() {
	return true;
}