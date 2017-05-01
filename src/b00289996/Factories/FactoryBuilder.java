package b00289996.Factories;

public class FactoryBuilder {
	@SuppressWarnings("rawtypes")
	public AbstractFactory buildFactory(String type) {
		if (type == null) 
			return null;
		AbstractFactory toReturn = null;
		if(type.equalsIgnoreCase("Behaviour"))
			return new BehaviourFactory();     
		else if(type.equalsIgnoreCase("Block"))
			return	new BlockFactory();    
		else if(type.equalsIgnoreCase("Tetromino"))
			return new TetrominoFactory();    
		return toReturn;
	}
}
