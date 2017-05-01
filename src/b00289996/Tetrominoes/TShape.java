package b00289996.Tetrominoes;

public class TShape extends Tetromino {
	private final int [][][] orientations = {
			{{0,0,0},
			{2,3,4},
			{0,1,0}},

			{{0,4,0},
			{0,3,1},
			{0,2,0}},

			{{0,1,0},
			{4,3,2},
			{0,0,0}},

			{{0,2,0},
			{1,3,0},
			{0,4,0}}};

	@Override
	public int[][] getShape() {
		return orientations[getOrientation()];
	}

	@Override
	public int yOffset() {
		int offset = 0, orientation = getOrientation();
		if (orientation == 0 ) 
			offset = 1;
		return offset;
	}
	
	@Override
	public int xOffset() {
		int offset = 0, orientation = getOrientation();
		if (orientation == 1 ) 
			offset = 1;
		return offset;
	}
}
