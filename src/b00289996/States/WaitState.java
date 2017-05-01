package b00289996.States;

import b00289996.Engine.Engine;
import b00289996.Interfaces.State;
import b00289996.Singletons.Time;

public class WaitState implements State{
	private Engine engine;

	public WaitState(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void Update() {
		if (Time.getInstance().update()) {
			engine.setDeltaTime(Time.getInstance().getDeltaTime());
			engine.setState(engine.getUpdateState());	
		}
	}
}
