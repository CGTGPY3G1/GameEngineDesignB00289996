package b00289996.Components;

import b00289996.Blocks.Block;

public class Grid {
	Block[][] segments;
	
	private int x, y, segmentWidth, segmentHeight, columns, rows, extraRows, usedRows;
	public int getX() { return x; }
	public int getMaxX() { return x+columns; }
	public int getY() { return y; }
	public int getMaxY() { return y+rows; }
	public int getSegmentWidth() { return segmentWidth; }
	public int getSegmentHeight() {	return segmentHeight; }
	public int getColumns() { return columns; }
	public int getRows() { return rows; }
	public int getExtraRows() {	return extraRows; }
	public int getUsedRows() {	return usedRows; }
	public int getCentreRow() {	return rows/2; }
	public int getCentreColumn() {	return columns/2; }
	
	public Grid(int x, int y, int segmentWidth, int segmentHeight, int columns, int rows, int extraRows) {
		this.x = x; this.y = y; this.segmentWidth = segmentWidth; this.segmentHeight = segmentHeight;
		this.columns = columns; this.rows = rows; this.extraRows = extraRows;
		segments = new Block[rows+extraRows][columns];
	}
	
	public void removeRow(int row) {
		if (row >= 0 && row < usedRows) {
			for (int i = row; i < usedRows; i++) {
		    	for (int j = 0; j < columns; j++) {
		    		segments[i][j] = segments[i+1][j];
		    		segments[i+1][j] = null;
				}
		    }
			usedRows--;
		}
	}
	
	public boolean isRowFull(int row) {
		if (row < 0 || row >= rows) 
			return false;
		boolean rowFull = true;
		for (int i = 0; i < segments[row].length && rowFull; i++) {
			if (segments[row][i] == null)
				rowFull = false;
		}
		return rowFull;
	}
	
	public boolean isRowEmpty(int row) {
		if (row < 0 || row >= rows) 
			return false;
		boolean rowFull = true;
		for (int i = 0; i < segments[row].length && rowFull; i++) {
			if (segments[row][i] != null)
				rowFull = false;
		}
		return rowFull;
	}
	
	public boolean isSegmentEmpty(int row, int column) {
		if (row < 0 || row >= usedRows || column < 0 || column >= columns) 
			return true;
		if (segments[row][column] != null)
			return false;
		return true;
	}
	
	public Block get(int row, int column) {
		if (row < 0 || row >= rows || column < 0 || column >= columns) {
			return null;
		}
		return segments[row][column];
	}
	
	public Block[] getRow(int row) {
		if (row < 0 || row >= rows) {
			return null;
		}
		return segments[row];
	}
	
	public void set(int row, int column, Block value) {
		if (row >= 0 && row < rows && column >= 0  && column < columns) {
			segments[row][column] = value;
			if (row+1 > usedRows && value != null) {
				usedRows = row+1;
			}	
		}
	}
	
	public void remove(int row, int column) {
		if (row >= 0 && row < rows && column >= 0  && column < columns) {
			segments[row][column] = null;
		}
	}
	
	public int getWorldLocationX(int gridX) {
		return (x+gridX)*segmentWidth;
	}
	
	public int getWorldLocationY(int gridY) {
		return (y+gridY)*segmentHeight;
	}
	
	public Vector2I getSegmentWorldLocation(Block block) {
		Vector2I toReturn = getSegmentPosition(block);
		if (toReturn != null) {
			toReturn.setX(getWorldLocationX(toReturn.getX()));
			toReturn.setY(getWorldLocationY(toReturn.getY()));
		}
		return toReturn;
	}
	
	public boolean isEmpty() {
		for (int i = 0; i < rows; i++) {
	    	for (int j = 0; j < columns; j++) {
	    		if(segments[i][j] != null) 
	    			return false;
			}
	    }
		return true;
	}
	
	public void clear() {
		for (int i = 0; i < rows; i++) {
	    	for (int j = 0; j < columns; j++) {
	    		if(segments[i][j] != null) 
	    			segments[i][j] = null;
			}
	    }
		usedRows = 0;
	}
	
	public Vector2I getSegmentPosition(Block block) {
		for (int i = 0; i < segments.length; i++) {
			for (int j = 0; j < segments[i].length; j++) {
				Block toCheck = segments[i][j];
				while (toCheck != null) {		
					if (block.equals(toCheck)) 
						return new Vector2I(j, i);
					toCheck = toCheck.getSubBlock();
				}
			}
		}
		return null;
	}
	
	public void ActivateSegment(int row, int column) {
		if (row >= 0 && row < rows && column >= 0  && column < columns) {
			Block toActivate = segments[row][column];
			if (toActivate != null) 
				toActivate.Activate();
		}
	}
	
	public void ActivateRow(int row) {
		if (row >= 0 || row < rows) {
			for (int i = 0; i < segments[row].length; i++) {
				Block toActivate = segments[row][i];
				if (toActivate != null) 
					toActivate.Activate();
			}
		}
		
	}
}