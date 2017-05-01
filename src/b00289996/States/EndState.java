package b00289996.States;

import b00289996.Engine.Engine;
import b00289996.Interfaces.State;
import b00289996.Singletons.Screen;

public class EndState implements State {
	private Engine engine;
	public EndState(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void Update() {
		engine.setState(null);
		Screen.getInstance().close();
	}
}
