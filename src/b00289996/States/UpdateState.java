package b00289996.States;

import b00289996.Engine.Engine;
import b00289996.Interfaces.State;

public class UpdateState implements State {
	private Engine engine;

	public UpdateState(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void Update() {
		engine.getGame().update(engine.getDeltaTime());
		if (engine.getGame().isGameOver()) 
			engine.setState(engine.getEndState());
		else 
			engine.setState(engine.getRenderState());
	}
}
