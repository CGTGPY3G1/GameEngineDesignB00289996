package b00289996.Blocks;

import b00289996.Components.MathOperation;

public class NormalBlock extends Block {
	private final MathOperation mathOperation = new MathOperation('+', 200);
	public NormalBlock(String imageName) {
		super(imageName, "WhiteOverlay");
	}

	@Override
	public void Activate() {
		if (!isActive()) {
			notifyObservers();
			showMask = true;
			if (audioPlaybackBehaviour != null) 
				audioPlaybackBehaviour.enable();
			if (subBlock != null) {
				subBlock.Activate();
			}
			removeAllObservers();
			active = true;
		}
	}

	@Override
	MathOperation getScoreModifier() { return mathOperation; }

	@Override
	public int getPointValue() { return mathOperation.getValue(); }
	
}
