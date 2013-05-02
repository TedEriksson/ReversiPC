import ddf.minim.*;
import ddf.minim.signals.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;


int W = 1280, H = 720;

Game game;
int gameState = 1;
Button gameButton, gameAIButton, quitButton;
CheckBox soundCheck;
Background bg = new Background();

Minim minim;
AudioPlayer player;
AudioInput input;

void setup() {
	//W = displayWidth;
	//H = displayHeight;
	size(W,H,P3D);

	minim = new Minim(this);
	player = minim.loadFile("data/dreams.mp3");
	input = minim.getLineIn();
	player.play();
	gameState = 1;
	ortho(0, width, 0, height); 
	game = new Game(false);
	gameButton = new Button("Start Game VS another Player", width/2, height/3);
	gameAIButton = new Button("Start Game VS CPU", width/2, (height/6)*3);
	quitButton = new Button("Quit Game",  width/2, (height/6)*5);
	soundCheck = new CheckBox("Sound on", "Sound off", width/2, (height/6)*4);
}

void draw() {
	lights();

	switch(gameState) {
		case 1: mainMenu(); break;
		case 2: if(!game.draw()) gameState = 1; break;
	}
}

void mainMenu() {
	//background(130);
	bg.draw();
	pushMatrix();
	translate(width/2, 0, 0);
		pushMatrix();
			translate(0, height/3, 0);
			gameButton.draw();
		popMatrix();
		pushMatrix();
			translate(0, (height/6)*3, 0);
			gameAIButton.draw();
		popMatrix();
		pushMatrix();
			translate(0, (height/6)*4, 0);
			soundCheck.draw();
		popMatrix();
		pushMatrix();
			translate(0, (height/6)*5, 0);
			quitButton.draw();
		popMatrix();
	popMatrix();
}

void mousePressed() {
	game.mousePressed();
	if(gameState == 1) {
		if(gameButton.clicked()) {
			game = new Game(false);
			gameState = 2;
		} else if(gameAIButton.clicked()) {
			game = new Game(true);
			gameState = 2;
		} else if(soundCheck.clicked()) {
			if(soundCheck.isChecked()) {
				player.mute();
			} else {
				player.unmute();
			}
			soundCheck.flipTicked();
		} else if(quitButton.clicked()) {
			exit();
		}
	}
}

void stop() {
	player.close();
	minim.stop();
	super.stop();
}

boolean sketchFullScreen() {
	return false;
}
