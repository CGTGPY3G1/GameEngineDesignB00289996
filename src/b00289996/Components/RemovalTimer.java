package b00289996.Components;

public class RemovalTimer extends CountDownTimer {
	private Vector2I position;
	public RemovalTimer(Vector2I position, float time) {
		super(time, false);
		this.position = position;
	}
	public Vector2I getPosition() { return position; }
	public int getX() { return position.getX(); }
	public int getY() { return position.getY(); }
}
