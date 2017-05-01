package b00289996.Interfaces;

public interface Subject {
	void registerObserver(Observer observer);
	void removeObserver(Observer observer);
	void removeAllObservers();
	void notifyObservers();
}
