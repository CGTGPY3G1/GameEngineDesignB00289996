package b00289996.Blocks;

import b00289996.Components.MathOperation;

public class ZeroPointBlock extends Block {
	MathOperation mathOperation = new MathOperation('*', 0);
	public ZeroPointBlock() {
		super("ZeroPoint", null);
	}

	@Override
	public int getPointValue() { return 0; }

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

	@Override
	MathOperation getScoreModifier() { return mathOperation; }
}
