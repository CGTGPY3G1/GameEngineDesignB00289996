package b00289996.Components;

public class Vector2I {
	private int x, y;
	
	public Vector2I(int x, int y) { 
		this.x = x; this.y = y; 
	}
	
	public Vector2I() { 
		reset(); 
	}
	
	public void reset() { x = 0; y = 0; }
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
}