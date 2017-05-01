package b00289996.Singletons;

import java.util.ArrayList;
import b00289996.Blocks.Block;
import b00289996.Components.Grid;
import b00289996.Components.RemovalTimer;
import b00289996.Components.Vector2I;
import b00289996.Interfaces.Behaviour;
import b00289996.Tetrominoes.Tetromino;

public class GridManager {
	final private float REMOVAL_DELAY = 0.4f;
	private boolean rowsChecked;
	private ArrayList<RemovalTimer> segmentsToRemove = new ArrayList<RemovalTimer>();
	Behaviour dropBehaviour, moveLeftBehaviour, moveRightBehaviour;
	private Grid grid;
	public Grid getGrid() { return grid; }
	public void setGrid(Grid grid) { this.grid = grid; }
	private Tetromino tetromino;
	public Tetromino geTetromino() { return tetromino; }
	
	public Tetromino removeTetromino() {
		Tetromino toReturn = tetromino;
		if (tetromino != null) 
			tetromino = null;
		return toReturn;
	}
	
	public void setTetromino(Tetromino tetromino) { 
		this.tetromino = tetromino; 
		this.tetromino.setPosition(getSpawnPointX(), getSpawnPointY());
	}
	
	public boolean hasTetromino() { return tetromino != null; }
	private static GridManager gridManager;
	public static GridManager getInstance() {
		if (gridManager == null) 
			gridManager = new GridManager();
		return gridManager;
	}
	
	private GridManager() {
		
	}
	
	public void activateInRange(Block block, int range) {
		Vector2I position = grid.getSegmentPosition(block);
		if (position != null) {
			int minY = position.getY()-range, maxY = position.getY()+range;
			int minX = position.getX()-range, maxX = position.getX()+range;
			if (minX < 0) minX = 0;
			if (minY < 0) minY = 0;
			for (int i = minY; i <= maxY; i++) {
				for (int j = minX; j <= maxX; j++) {
					if (!grid.isSegmentEmpty(i, j)) {
						grid.ActivateSegment(i, j);
						setSegmentToRemove(i, j, REMOVAL_DELAY);
					}	
				}
			}
		}
	}
	
	public boolean tryRotate(boolean clockwise) {
		boolean canRotate = canRotate(clockwise);
		if (canRotate) 
			tetromino.Rotate(clockwise);
		return canRotate;
	}
	
	public boolean canRotate(boolean clockwise) {
		boolean canRotate = true;
		if (tetromino != null) {
			int[][]positions = tetromino.getRotated(clockwise);
			for (int i = 0; i < positions.length && canRotate; i++) {
				for (int j = 0; j < positions[i].length && canRotate; j++) {
					int row = i + tetromino.getY(), column = tetromino.getX()+j;
						if(positions[i][j] != 0) 
							if(row < 0 || row >= grid.getRows()+grid.getExtraRows() || column < 0 || 
							column >= grid.getColumns() || grid.get(row, column) != null)
								canRotate = false;
				}
			}
		}
		else {
			canRotate = false;
		}
		return canRotate;
	}
	
	public boolean tryMove(int offsetX, int offsetY) {
		boolean canMove = canMove(offsetX, offsetY);
		if (canMove)
			tetromino.setPosition(tetromino.getX()+offsetX, tetromino.getY()+offsetY);
		return canMove;
	}
	
	public boolean canMove(int offsetX, int offsetY) {
		boolean canMove = true;
		if (tetromino != null) {
			int[][]positions = tetromino.getShape();
			for (int i = 0; i < positions.length && canMove; i++) {
				for (int j = 0; j < positions[i].length && canMove; j++) {
					int row = i + tetromino.getY() + offsetY, column = j + tetromino.getX() + offsetX;
						if(positions[i][j] != 0) 
							if(row < 0 || row >= grid.getRows()+grid.getExtraRows() || column < 0 || 
								column >= grid.getColumns() || grid.get(row, column) != null)
								canMove = false;
				}
			}
		}
		else {
			canMove = false;
		}
		return canMove;
	}

	public void update(float deltaTime) {
		if (!segmentsToRemove.isEmpty()) {
			for (int i = 0; i < segmentsToRemove.size(); i++) {
				RemovalTimer timer = segmentsToRemove.get(i);
				if (timer.countDown(deltaTime)) {
					grid.get(timer.getY(), timer.getX());
					grid.remove(timer.getY(), timer.getX());
					segmentsToRemove.remove(i);
					i--; 
				}	
			}
			for (int i = 0; i < grid.getUsedRows(); i++) {
				if (grid.isRowEmpty(i)) {
					grid.removeRow(i);
					i--;
				}
			}
		}
	}
	
	public int getSpawnPointX () {
		return  tetromino == null || grid == null ? 0 : (grid.getColumns()/2)-(tetromino.getWidth()/2);
	}
	
	public int getSpawnPointY () {
		return  tetromino == null || grid == null ? 0 : grid.getRows()-tetromino.yOffset();
	}
	
	public void addToGrid() {
		int[][]positions = tetromino.getShape();
		for (int i = 0; i < positions.length; i++) {
			for (int j = 0; j < positions[i].length; j++) {
				int row = i + tetromino.getY(), column = tetromino.getX()+j;
				if (row < grid.getRows() && column >= 0 && column < grid.getColumns()) 
					if(positions[i][j] != 0) {
						Block toAdd = tetromino.getBlock(positions[i][j]);
						grid.set(row, column, toAdd);
					}
			}
		}
		MediaManager.getInstance().LoadAudio("Audio/Land.wav", true);
		tetromino = null;
		rowsChecked = false;
	}
	
	public boolean isOnGrid() {
		return tetromino == null ? false : tetromino.getY() < getSpawnPointY() ? true : false;
	}
	
	public void checkRows() {
		if (!rowsChecked) {
			for (int i = 0; i < grid.getUsedRows(); i++) {
				if (grid.isRowFull(i)) {
					setRowToRemove(i, REMOVAL_DELAY);
				}
			}
			rowsChecked = true;
		}
	}
	
	public void setRowToRemove(int index, float delay) {
		for (int i = 0; i < grid.getColumns(); i++) {
			setSegmentToRemove(index, i, delay);
		}
		grid.ActivateRow(index); 
	}
	
	public void setSegmentToRemove(int row, int column, float delay) {
		segmentsToRemove.add(new RemovalTimer(new Vector2I(column, row), delay));
	}
}
