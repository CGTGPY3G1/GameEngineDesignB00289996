package b00289996.Blocks;

import b00289996.Components.MathOperation;
import b00289996.Components.Vector2I;
import b00289996.Singletons.GridManager;
import b00289996.Singletons.MediaManager;
import b00289996.Singletons.Screen;

public class BombBlock extends Block {
	private final int RANGE = 1;
	public BombBlock() {
		super("Bomb", null);
		mathOperation = new MathOperation('+', getPointValue());
	}
	@Override
	public int getPointValue() { return 0; }

	@Override
	public void Activate() {
		if (!isActive()) {
			notifyObservers();
			removeAllObservers();
			if (audioPlaybackBehaviour != null) 
				audioPlaybackBehaviour.enable();
			active = true;
			GridManager.getInstance().activateInRange(this, RANGE);
			Vector2I position = GridManager.getInstance().getGrid().getSegmentWorldLocation(this);
			Screen.getInstance().printImageForTime(MediaManager.getInstance().LoadImage("/Effects/BombBlast.png", true), position.getX()-20, position.getY()-20, 0.35f);
		}
	}

	@Override
	MathOperation getScoreModifier() { return mathOperation; }
}
