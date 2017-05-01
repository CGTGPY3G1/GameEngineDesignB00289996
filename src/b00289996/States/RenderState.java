package b00289996.States;

import b00289996.Engine.Engine;
import b00289996.Interfaces.State;
import b00289996.Singletons.Screen;

public class RenderState implements State {
	private Engine engine;
	
	public RenderState(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public void Update() {
		Screen.getInstance().clear();
		engine.getGame().Render();
		Screen.getInstance().update(engine.getDeltaTime());
		Screen.getInstance().render();
		engine.setState(engine.getLateUpdateState());
	}
}
