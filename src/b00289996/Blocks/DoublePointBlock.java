package b00289996.Blocks;

import b00289996.Components.MathOperation;

public class DoublePointBlock extends Block {
	public DoublePointBlock() {
		super("DoublePoint", null);
		mathOperation = new MathOperation('*', getPointValue());
	}
	@Override
	public int getPointValue() { return 2; }

	@Override
	public void Activate() {
		if (!isActive()) {
			if (audioPlaybackBehaviour != null) 
				audioPlaybackBehaviour.enable();
			notifyObservers();
			removeAllObservers();	
			active = true;
		}
	}
}
