class Button {
	String message;
	int fontSize = 32, posX, posY;
	Button(String message, int posX, int posY) {
		this.message = message;
		this.posX = posX;
		this.posY = posY;
	}

	void draw() {
		pushMatrix();
		noStroke();
		textSize(fontSize);
		fill(230);
		if(mouseOver())
			selected();
		box((fontSize*message.length())/2 + fontSize,fontSize * 1.5,20);
			pushMatrix();
				fill(40);
				textAlign(CENTER, CENTER);
				translate(0, 0, 10);
				text(message, 0,0,1);
			popMatrix();
		popMatrix();
	}

	boolean clicked() {
		if(mouseOver()) {
			return true;
		}
		return false;
	}

	void selected() {
			fill(180);
	}

	boolean mouseOver() {
		if(mouseX > posX - ((fontSize*message.length())/4  + fontSize/2) && mouseX < posX + ((fontSize*message.length())/4  + fontSize/2) && mouseY > posY - fontSize * 1 && mouseY < posY + fontSize * 1) {
			return true;
		} else {
			return false;
		}
	}

	void setMessage(String message) {
		this.message = message;
	} 
}
