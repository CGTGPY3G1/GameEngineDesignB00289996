package b00289996.Tetrominoes;

public class JShape extends Tetromino {
	private final int [][][] orientations = {
			{{0,0,4},
			{1,2,3},
			{0,0,0}},

			{{4,3,0},
			{0,2,0},
			{0,1,0}},

			{{0,0,0},
			{3,2,1},
			{4,0,0}},

			{{0,1,0},
			{0,2,0},
			{0,3,4}}};

	@Override
	public int[][] getShape() {
		return orientations[getOrientation()];
	}

	
	@Override
	public int yOffset() {
		int offset = 0, orientation = getOrientation();
		if (orientation == 2) {
			offset = 1;
		}
		return offset;
	}
	
	@Override
	public int xOffset() {
		int offset = 0, orientation = getOrientation();
		if (orientation == 3 ) 
			offset = 1;
		return offset;
	}
	
	@Override
	public int[][] getRotation(int rotation) {
		if (rotation >= 0 && rotation < noOfBlocks()) {
			return orientations[rotation];
		}
		return null;
	}
}
