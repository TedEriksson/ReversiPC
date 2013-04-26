class Queue {
	
	ArrayList<Integer> queue = new ArrayList();

	Queue() {

	}

	void add(int pos) {
		queue.add(pos);
	}

	int remove() {
		return queue.remove(0);
	}

	int getStart() {
		return queue.get(0);
	}

	int getLast() {
		return queue.get(queue.size()-1);
	}

	boolean isEmpty() {
		return queue.isEmpty();
	}

	int size() {
		return queue.size();
	}
}