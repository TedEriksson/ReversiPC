class PopUp {
	String title, message, button1, button2;
	boolean oneButton = true, showPopUp = false, ready = false;
	Button b1, b2;

	PopUp() {
		
	}

	void setUp(String title, String message, String button1, String button2) {
		this.title = title;
		this.message = message;
		this.button1 = button1;
		this.button2 = button2;
		b1  = new Button(button1,width/2 + width/12,height/2 + height/9);
		if (!button2.equals("")) {
			b2  = new Button(button2,width/2 - width/12,height/2 + height/9);
			oneButton = false;
		}
		ready = true;
	}

	void setMessage(String message) {
		this.message = message;
	}

	void show() {
		showPopUp = true;
	}

	void hide() {
		showPopUp = false;
	}

	void button1Pressed() {
		println("clicked button 1");
	}

	void button2Pressed() {
		println("clicked button 2");
	}

	void mousePressed() {
		if(showPopUp) {
			if(b1.clicked())
				button1Pressed();
			if(!oneButton) {
				if(b2.clicked())
					button2Pressed();
			}
		}
	}

	void draw() {
		if(showPopUp && ready) {
			pushMatrix();
				translate(width/2, height/2, 100);
				pushMatrix();
					fill(40);
					box(width/3,height/3,30);
					pushMatrix();
						fill(255);
						textAlign(CENTER,CENTER);
						textSize(60);
						text(title,0,-(height/9),50);
					popMatrix();
					pushMatrix();
						fill(255);
						textAlign(CENTER,CENTER);
						textSize(30);
						text(message,0,0,50);
					popMatrix();
				popMatrix();

				pushMatrix();
					translate((width/12),height/9 , 20);
					fill(255);
					b1.draw();
				popMatrix();

				if(!oneButton) {
				pushMatrix();
					translate(-(width/12),height/9 , 20);
					fill(255);
					b2.draw();
				popMatrix();
				}
			popMatrix();
		}
	}
}