class WaitTimer {
	int endTime, millsLeft;
	boolean go = false;

	WaitTimer() {
	}

	void start(int endTime) {
		this.endTime = millis() + endTime;
		go = true;
	}

	boolean checkFinished() {
		if(millis() >= endTime) {
			//println("Counting");
			return true;
		}
		return false;
	}

	void draw() {
		if(checkFinished() && go)
			finished();
	}

	void finished() {
		go=false;
	}
}