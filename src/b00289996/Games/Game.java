package b00289996.Games;

public abstract class Game {
	protected boolean gameOver = false;
	public boolean isGameOver() { return gameOver; }
	public abstract void update(float deltaTime);
	public abstract void Render();
}
