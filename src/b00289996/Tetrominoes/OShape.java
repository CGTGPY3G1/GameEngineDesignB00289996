package b00289996.Tetrominoes;

public class OShape extends Tetromino {
	private final int [][][] orientations = {
			{{3,4},
			{1,2}},

			{{4,2},
			{3,1}},

			{{2,1},
			{4,3}},

			{{1,3},
			{2,4}}};
	
	@Override
	public int[][] getShape() {
		return orientations[getOrientation()];
	}

	@Override
	public int yOffset() {
		return 0;
	}
	
	@Override
	public int xOffset() {
		return 0;
	}
	
	@Override
	public int[][] getRotation(int rotation) {
		if (rotation >= 0 && rotation < noOfBlocks()) {
			return orientations[rotation];
		}
		return null;
	}
}
