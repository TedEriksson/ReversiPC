class CheckBox extends Button {
	boolean ticked = true;
	String onMessage;
	String offMessage;

	CheckBox(String message, String offMessage, int posX, int posY) {
		super(message,posX,posY);
		this.onMessage = message;
		this.offMessage = offMessage;
	}

	boolean isChecked() {
		if(ticked)
			return true;
		return false;
	}

	void flipTicked() {
		if(ticked) {
			ticked = false;
			setMessage(offMessage);
		} else {
			ticked = true;
			setMessage(onMessage);
		}
	}

	boolean clicked() {
		if(mouseOver()) {
			return true;
		}
		return false;
	}
}