package b00289996.Factories;

import b00289996.Tetrominoes.IShape;
import b00289996.Tetrominoes.JShape;
import b00289996.Tetrominoes.LShape;
import b00289996.Tetrominoes.OShape;
import b00289996.Tetrominoes.SShape;
import b00289996.Tetrominoes.TShape;
import b00289996.Tetrominoes.Tetromino;
import b00289996.Tetrominoes.ZShape;

public class TetrominoFactory extends AbstractFactory <Tetromino> {
	
	@Override
	public Tetromino build(String type) {
		if (type == null) 
			return null;
		Tetromino tetromino = null;
		if(type.equalsIgnoreCase("I"))
			tetromino = new IShape();     
		else if(type.equalsIgnoreCase("J"))
			tetromino = new JShape();
		else if(type.equalsIgnoreCase("L"))
			tetromino = new LShape();
		else if(type.equalsIgnoreCase("O"))
			tetromino = new OShape();
		else if(type.equalsIgnoreCase("S"))
			tetromino = new SShape();
		else if(type.equalsIgnoreCase("T"))
			tetromino = new TShape();
		else if(type.equalsIgnoreCase("Z"))
			tetromino = new ZShape();
		return tetromino;
	}
}
