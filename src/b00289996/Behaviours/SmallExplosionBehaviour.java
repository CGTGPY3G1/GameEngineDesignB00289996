package b00289996.Behaviours;

import b00289996.Interfaces.Behaviour;
import b00289996.Singletons.MediaManager;

public class SmallExplosionBehaviour implements Behaviour {

	@Override
	public void enable() {
		MediaManager.getInstance().LoadAudio("Audio/Explosion1.wav", true);
	}

}
