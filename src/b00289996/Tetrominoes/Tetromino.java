package b00289996.Tetrominoes;

import b00289996.Blocks.Block;

public abstract class Tetromino {

	private int x, y;
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public void setPosition(int x, int y) { this.x = x; this.y = y; }
	public int noOfBlocks() { return blocks.length; }
	
	private Block[] blocks = new Block[4];
	
	public Block getBlock(int number) {
		if (number > blocks.length || number < 1) {
			System.out.println("Block index out of range!");
			return null;
		}
		return blocks[number-1];
	}
	
	public void setBlock(int number, Block toAdd) {
		if (number > blocks.length || number < 1) {
			System.out.println("Block index out of range!");
			return;
		}
		blocks[number-1] = toAdd;
	}
	
	private int orientation = 0;
	protected int getOrientation() { return orientation; }
	public void setOrientation(int orientation) { 
		if (orientation >= 0 && orientation < noOfBlocks()) 
			this.orientation = orientation;
	}
	
	protected int nextOrientation(boolean clockwise) {
		if (clockwise) {
			orientation++;
			if (orientation > 3) 
				orientation = 0;
		}
		else {
			orientation--;
			if (orientation < 0) 
				orientation = 3;
		}
		return orientation;
	}
	
	public int getWidth() { return getShape()[0].length; }
	
	public int getHeight() { return getShape().length; }
	
	public int [][] getRotated(boolean clockwise) {
		int rotatedIndex = nextOrientation(clockwise);
		nextOrientation(!clockwise);
		return getRotation(rotatedIndex);
	}
	
	public int[][] getRotation(int rotation) {
		if (rotation >= 0 && rotation < noOfBlocks()) {
			
			int oldOrientation = orientation;
			orientation = rotation;
			int[][] rotated = getShape();
			orientation = oldOrientation;
			return rotated;
		}
		return null;
	}
	
	public abstract int [][] getShape();
	public abstract int yOffset();
	public abstract int xOffset();
	
	public void Rotate(boolean clockWise) {
		nextOrientation(clockWise);
	}
}
