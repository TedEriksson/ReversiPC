class WaitTimer {
	int endTime, millsLeft;
	boolean go = false;

	WaitTimer() {
	}

	void start(int endTime) {
		this.endTime = millis() + endTime;
	}

	boolean checkFinished() {
		if(millis() >= endTime)
			return true;
		return false;
	}

	void draw() {

	}
}