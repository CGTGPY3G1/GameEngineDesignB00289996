package b00289996.Factories;

import b00289996.Behaviours.DoublePointBehaviour;
import b00289996.Behaviours.LargeExplosionBehaviour;
import b00289996.Behaviours.NormalBehaviour;
import b00289996.Behaviours.ZeroPointBehavior;
import b00289996.Interfaces.Behaviour;

public class BehaviourFactory extends AbstractFactory<Behaviour> {

	@Override
	public Behaviour build(String type) {
		if (type == null) 
			return null;
		Behaviour toReturn = null;
		if (type.compareTo("Normal") == 0 || type.compareTo("DarkBlue") == 0 || type.compareTo("Green") == 0|| type.compareTo("LightBlue") == 0 ||
				type.compareTo("Orange") == 0 || type.compareTo("Purple") == 0|| type.compareTo("Red") == 0|| type.compareTo("Yellow") == 0) {
			toReturn = new NormalBehaviour();
		}
		else if (type.compareTo("ZeroPointer") == 0) {
			toReturn = new ZeroPointBehavior();
		}
		else if (type.compareTo("PointDoubler") == 0) {
			toReturn = new DoublePointBehaviour();
		}
		else if (type.compareTo("Bomb") == 0) {
			toReturn = new LargeExplosionBehaviour();
		}
		return toReturn;
	}
}
