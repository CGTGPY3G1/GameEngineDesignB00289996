package b00289996.Engine;

import b00289996.Games.Game;
import b00289996.Games.Tetris;
import b00289996.Interfaces.State;
import b00289996.States.EndState;
import b00289996.States.LateUpdateState;
import b00289996.States.RenderState;
import b00289996.States.UpdateState;
import b00289996.States.WaitState;

public class Engine {
	private State currentState;
	public State getCurrentState() { return currentState; }
	public void setState(State newState) { currentState = newState; }
	
	public State getWaitState() { return new WaitState(this); }
	public State getUpdateState() { return new UpdateState(this); }
	public State getRenderState() { return new RenderState(this); }
	public State getLateUpdateState() { return new LateUpdateState(this); }
	public State getEndState() { return new EndState(this); }

	private Game game;
	public Game getGame() { return game; }
	public void setGame(Game game) { this.game = game; }
	
	private float deltaTime;
	public float getDeltaTime() { return deltaTime; }
	public void setDeltaTime(float deltaTime) { this.deltaTime = deltaTime; }
	public Engine() {
		game = new Tetris();
		currentState = getWaitState();
	}
	
	public void Run() {
		while (currentState != null) {	
			currentState.Update();
		}
	}
	
	

}
