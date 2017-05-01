package b00289996.Factories;

import b00289996.Blocks.Block;
import b00289996.Blocks.BombBlock;
import b00289996.Blocks.DoublePointBlock;
import b00289996.Blocks.NormalBlock;
import b00289996.Blocks.ZeroPointBlock;

public class BlockFactory extends AbstractFactory <Block> {
	
	@Override
	public Block build(String type) {
		if (type == null) 
			return null;
		Block newBlock = null;
		if (type.compareTo("DarkBlue") == 0 || type.compareTo("Green") == 0|| type.compareTo("LightBlue") == 0 ||
				type.compareTo("Orange") == 0 || type.compareTo("Purple") == 0|| type.compareTo("Red") == 0|| type.compareTo("Yellow") == 0) {
			newBlock = new NormalBlock(type);
		}
		else if (type.compareTo("ZeroPointer") == 0) {
			newBlock = new ZeroPointBlock();
		}
		else if (type.compareTo("PointDoubler") == 0) {
			newBlock = new DoublePointBlock();
		}
		else if (type.compareTo("Bomb") == 0) {
			newBlock = new BombBlock();
		}
		return newBlock;
	}
}
