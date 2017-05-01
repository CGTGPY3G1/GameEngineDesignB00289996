package b00289996.Singletons;

import java.util.Random;

import b00289996.Behaviours.NormalBehaviour;
import b00289996.Blocks.Block;
import b00289996.Factories.FactoryBuilder;
import b00289996.Interfaces.Behaviour;
import b00289996.Interfaces.Observer;
import b00289996.Tetrominoes.Tetromino;

public class Factory {
	private FactoryBuilder factoryBuilder;
	private static Factory factory;
	private Random random = new Random();
	String[] types = { "I", "J", "L", "O", "S", "T", "Z" };
	String[] colours = { "DarkBlue", "Green", "LightBlue", "Orange", "Purple", "Red", "Yellow" };
	
	public static Factory getInstance() {
		if (factory == null) 
			factory = new Factory();
		return factory;
	}
	
	private Factory() {
		factoryBuilder = new FactoryBuilder();
	}
	
	public Tetromino buildTetromino(String type, String blockType, Observer blockObserver) {
		Tetromino toReturn = buildTetromino(type);
		for (int i = 0; i < toReturn.noOfBlocks(); i++) {
			toReturn.setBlock(i+1, buildBlock(blockType, blockObserver));
		}
		return toReturn;
	}
	
	public Tetromino buildTetromino(String type) {
		return (Tetromino) (factoryBuilder.buildFactory("Tetromino")).build(type);
	}
	
	public Tetromino buildRandomTetromino(Observer blockObserver) {
		Tetromino newTetromino = Factory.getInstance().buildTetromino(types[random.nextInt(types.length)]);
		if (newTetromino != null) {
			String colour = colours[random.nextInt(colours.length)];
			for (int i = 0; i < newTetromino.noOfBlocks(); i++) {
				Block newBlock = Factory.getInstance().buildBlock(colour, blockObserver);
				int chance = random.nextInt(1000);
				if (chance < 160) {
					Block newSubBlock;
					if (chance < 30) {
						newSubBlock = Factory.getInstance().buildBlock("ZeroPointer", blockObserver);
						newSubBlock.setAudioPlaybackBehaviour(Factory.getInstance().buildBehaviour("ZeroPointer"));
					}
					else if (chance < 80) {
						newSubBlock = Factory.getInstance().buildBlock("Bomb", blockObserver);
						newSubBlock.setAudioPlaybackBehaviour(Factory.getInstance().buildBehaviour("Bomb"));
					}
					else {
						newSubBlock = Factory.getInstance().buildBlock("PointDoubler", blockObserver);
						newSubBlock.setAudioPlaybackBehaviour(Factory.getInstance().buildBehaviour("PointDoubler"));
					}
					newBlock.addSubBlock(newSubBlock);
				}
				newBlock.setAudioPlaybackBehaviour(new NormalBehaviour());
				newTetromino.setBlock(i+1, newBlock);
			}
		}
		return newTetromino;
	}
	
	public Block buildBlock(String type, Observer blockObserver) {
		Block toReturn = (Block)factoryBuilder.buildFactory("Block").build(type);
		if (blockObserver != null) {
			toReturn.registerObserver(blockObserver);
		}
		return toReturn;
	}
	
	public Behaviour buildBehaviour(String type) {
		return (Behaviour)factoryBuilder.buildFactory("Behaviour").build(type);
	}
}
