package b00289996.Components;

public class CountDownTimer {
	private float timer, time;
	private boolean looping;
	public CountDownTimer(float time, boolean looping) {
		this.time = time;
		timer = time;
		this.looping = looping;
	}
	public boolean countDown(float deltaTime) {
		boolean timeUp = true;
		if (timer > 0) 
			timer -= deltaTime;
		timeUp = isFinished();
		if (looping && timeUp) { timer = time; }
		return timeUp;
	}
	
	public void reset() { timer = time; }
	public void setTime(float time) { this.time = time; timer = time; }
	public boolean isFinished() { return timer <= 0; }
	public void setFinished() { timer = 0; }
}
