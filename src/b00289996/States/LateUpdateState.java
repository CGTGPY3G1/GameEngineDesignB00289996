package b00289996.States;

import b00289996.Engine.Engine;
import b00289996.Interfaces.State;
import b00289996.Singletons.InputManager;

public class LateUpdateState implements State {
	private Engine engine;

	public LateUpdateState(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void Update() {
		InputManager.getInstance().updateKeys();
		engine.setState(engine.getWaitState());
	}
}
