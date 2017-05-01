package b00289996.Games;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import b00289996.Blocks.Block;
import b00289996.Components.CountDownTimer;
import b00289996.Components.Grid;
import b00289996.Components.ScoreBoard;
import b00289996.Components.Sprite;
import b00289996.Components.Vector2I;
import b00289996.Singletons.Factory;
import b00289996.Singletons.GridManager;
import b00289996.Singletons.InputManager;
import b00289996.Singletons.MediaManager;
import b00289996.Singletons.Screen;
import b00289996.Tetrominoes.Tetromino;

public class Tetris extends Game {
	final int NUMBER_TO_STORE = 4, INITIAL_TARGET_SCORE = 100000;
	final float INITIAL_DROP_TIME = 1f, MINIMUM_DROP_TIME = 0.2f;
	private Grid mainGrid, holdGrid, nextGrid;
	private Sprite background, panel;
	private Tetromino heldTetromino;
	
	private Vector2I moveDirection;
	private boolean shouldRotate = false;
	private CountDownTimer dropTimer, verticalInputTimer, horizontalInputTimer, rotationTimer, spawnTimer, holdTimer;
	
	ScoreBoard scoreBoard = new ScoreBoard(INITIAL_TARGET_SCORE);
	private int level = 0;
	private ArrayList<Tetromino> nextTetrominos = new ArrayList<Tetromino>();
	private float dropTime = INITIAL_DROP_TIME;
	private boolean playing = true;
	
	public Tetris() {
		background = new Sprite("/GameBackground.png", 800, 600);
		panel = new Sprite("/Panel.png", 440, 320);
		reset();
		incrementLevel();
		MediaManager.getInstance().LoopAudio("Audio/Theme.wav");
	}
	
	private void reset() {
		level = 0;
		dropTimer = new CountDownTimer(dropTime, true);
		spawnTimer = new CountDownTimer(dropTime, false);
		verticalInputTimer = new CountDownTimer(0.1f, false);
		horizontalInputTimer = new CountDownTimer(0.3f, false);
		rotationTimer = new CountDownTimer(0.4f, false);
		holdTimer = new CountDownTimer(0.8f, false);
		moveDirection = new Vector2I();
	}
	
	private void incrementLevel() {
		level++;
		scoreBoard.newScoreboard(INITIAL_TARGET_SCORE*level);
		mainGrid = new Grid(16, 5, 20, 20, 10, 20, 4);
		GridManager.getInstance().setGrid(mainGrid);
		holdGrid = new Grid(6, 19, 20, 20, 6, 4, 0);
		nextGrid = new Grid(29, 7, 20, 20, 6, 16, 0);
		setDropTime(INITIAL_DROP_TIME-((INITIAL_DROP_TIME/20)*(level-1)));
		verticalInputTimer.reset();
		horizontalInputTimer.reset();
		rotationTimer.reset();
		holdTimer.reset();
		moveDirection = new Vector2I();
		nextTetrominos.clear();
		for (int i = 0; i < NUMBER_TO_STORE; i++) {
			nextTetrominos.add(Factory.getInstance().buildRandomTetromino(scoreBoard));
		}
		playing = true;
		MediaManager.getInstance().LoadAudio("Audio/Reset.wav", true);
	}
	
	private void setDropTime(float dropTime) {
		if (dropTime >= MINIMUM_DROP_TIME) 
			this.dropTime = dropTime;
		else
			this.dropTime = MINIMUM_DROP_TIME;
		dropTimer.setTime(this.dropTime);
		spawnTimer.setTime(this.dropTime); 
	}

	@Override
	public void update(float deltaTime) {
		if (playing) {
			GridManager.getInstance().update(deltaTime);
			updateActiveTetromino(deltaTime);
			scoreBoard.update(deltaTime);
			if (scoreBoard.targetReached()) 
				playing = false;
		}
		else {
			if (InputManager.getInstance().getKey(KeyEvent.VK_ENTER)) {
				if (scoreBoard.targetReached()) {
					incrementLevel();
				}
				else if (!GridManager.getInstance().isOnGrid()) {
					reset();
					incrementLevel();
				}
			}
		}
		
		if (InputManager.getInstance().getKey(KeyEvent.VK_ESCAPE)) {
			gameOver = true;
		}
	}
	
	private void updateActiveTetromino(float deltaTime) {
		holdTimer.countDown(deltaTime);
		if (GridManager.getInstance().hasTetromino())  {
			if (holdTimer.isFinished() && heldTetromino == null && InputManager.getInstance().getKey(KeyEvent.VK_SPACE)) {
				heldTetromino = GridManager.getInstance().removeTetromino();
				heldTetromino.setOrientation(0);
				heldTetromino.setPosition(0, 0);
				MediaManager.getInstance().LoadAudio("Audio/Hold.wav", true);
				dropTimer.reset();
				holdTimer.reset();	
			}
			else {
				if (GridManager.getInstance().isOnGrid()) 
					updateUserInput(deltaTime);
				if (dropTimer.countDown(deltaTime)) {
					if(GridManager.getInstance().tryMove(0, -1)) {
						MediaManager.getInstance().LoadAudio("Audio/Move.wav", true);
					}
					else {
						if (GridManager.getInstance().isOnGrid()) {
							GridManager.getInstance().addToGrid();
						}
						else {
							playing = false;
						}
						
					}
				}
			}
			GridManager.getInstance().checkRows();
		}
		else {
			if (spawnTimer.countDown(deltaTime)) {
				GridManager.getInstance().setTetromino(nextTetrominos.remove(0));
				while (nextTetrominos.size() < NUMBER_TO_STORE) {
					nextTetrominos.add(Factory.getInstance().buildRandomTetromino(scoreBoard));
				}
				setDropTime(dropTime*0.99f);
			}
			else if (holdTimer.isFinished() && heldTetromino != null && InputManager.getInstance().getKey(KeyEvent.VK_SPACE)) {
				GridManager.getInstance().setTetromino(heldTetromino);
				heldTetromino = null;
				MediaManager.getInstance().LoadAudio("Audio/Hold.wav", true);
				holdTimer.reset();
				setDropTime(dropTime*0.99f);
			}
		}
	}
	
	private void updateUserInput(float deltaTime) {
		InputManager inputManager = InputManager.getInstance();
		if (rotationTimer.countDown(deltaTime) && inputManager.getKey(KeyEvent.VK_UP)) {
			shouldRotate = true;
			processRotation();
		}
		else if (horizontalInputTimer.countDown(deltaTime)){
			if(inputManager.getKey(KeyEvent.VK_LEFT)) {
				if (moveDirection.getX() != -1) 
					moveDirection.setX(-1);
				processHorizontalMovement();
			}
			else if (inputManager.getKey(KeyEvent.VK_RIGHT)) {
				if (moveDirection.getX() != 1) 
					moveDirection.setX(1);
				processHorizontalMovement();
			}
		}
		
		if (verticalInputTimer.countDown(deltaTime) && inputManager.getKey(KeyEvent.VK_DOWN)) {
			if (moveDirection.getY() != -1) 
				moveDirection.setY(-1);
			processVerticalMovement();
		}
		
	}
	
	private void processRotation() {
		if (GridManager.getInstance().hasTetromino()) {
			if (shouldRotate) {
				if(GridManager.getInstance().tryRotate(true)) 
					MediaManager.getInstance().LoadAudio("Audio/Rotate.wav", true);
				else 
					MediaManager.getInstance().LoadAudio("Audio/InvalidRotate.wav", true);
				rotationTimer.reset();
				shouldRotate = false;
			}
		}
	}
	
	private void processVerticalMovement() {
		if (GridManager.getInstance().hasTetromino()) {
			if (moveDirection.getY() != 0) {
				if(GridManager.getInstance().tryMove(0, moveDirection.getY())) {
					MediaManager.getInstance().LoadAudio("Audio/Move.wav", true);
					verticalInputTimer.reset();
					dropTimer.reset();
				}
				moveDirection.setY(0);
			}
		}
	}
	
	private void processHorizontalMovement() {
		if (GridManager.getInstance().hasTetromino()) {
			if (moveDirection.getX() != 0) {		
				if(GridManager.getInstance().tryMove(moveDirection.getX(), 0)) 
					MediaManager.getInstance().LoadAudio("Audio/Move.wav", true);
				else 
					MediaManager.getInstance().LoadAudio("Audio/InvalidMove.wav", true);
				horizontalInputTimer.reset();
				moveDirection.setX(0);
			}
		}
	}
	
	@Override
	public void Render() {
		Screen.getInstance().printImage(background.GetImage(), 0, 0);
		if (heldTetromino != null)
			drawToGrid(heldTetromino, holdGrid, 0);
		if (!nextTetrominos.isEmpty()) {
			for (int i = 0; i < nextTetrominos.size(); i++) {
				drawToGrid(nextTetrominos.get(i), nextGrid, (nextTetrominos.size()-1-i)*4);
			}
			
		}
		printScoreBoard();
		drawGrid(mainGrid);
		drawActiveTetronimo();
		if (!playing) {
			showPanel();
		}
	}
	
	private void showPanel() {
		Screen screen = Screen.getInstance();
		screen.printImage(panel.GetImage(), 180, 140);
		if (scoreBoard.targetReached()) {
			screen.printText("Level "+level+" Complete", 400, 390, 2, 2);
			screen.printText("Congratulations", 400, 345, 2, 2);
			screen.printText("Score : "+ scoreBoard.getScore(), 400, 295, 2, 2);
			screen.printText("Press Enter to", 400, 240, 2, 2);
			screen.printText("Play Next Level", 400, 200, 2, 2);
		}
		else {
			screen.printText("Game Over!", 400, 390, 2, 2);
			screen.printText("Reached Level " + level, 400, 345, 2, 2);
			screen.printText("Score : "+ scoreBoard.getScore(), 400, 295, 2, 2);
			screen.printText("Press Enter to", 400, 240, 2, 2);
			screen.printText("Play Again", 400, 200, 2, 2);
		}
	}
	
	private void printScoreBoard() {
		Screen screen = Screen.getInstance();
		int y = 286, offset = 41;
		screen.printText("Level " + level, 180, y, 2, 2); y -= offset;
		screen.printText("Score", 115, y, 2, 1); y -= offset;
		screen.printText(""+scoreBoard.getScore(), 245, y, 3, 3); y -= offset;
		screen.printText("Target", 115, y, 2, 1); y -= offset;
		screen.printText(""+scoreBoard.getTargetScore(), 245, y, 3, 3);
	}
	
	private void drawToGrid(Tetromino toDraw, Grid grid, int yOffset) {
		toDraw.setOrientation(0);
		int[][]positions = toDraw.getShape();
		for (int i = 0; i < positions.length; i++) {
			for (int j = 0; j < positions[i].length; j++) {
				int row = i;
				if (row >= 0 && row < grid.getRows()) {
					if(positions[i][j] != 0) {
						Block toPrint = toDraw.getBlock(positions[i][j]);
						drawBlock(toPrint, grid.getWorldLocationX(j+(grid.getCentreColumn()-((toDraw.getWidth()/2))-(((toDraw.getWidth() & 1) == 1) ? 1 : 0 ))), 
								grid.getWorldLocationY(i+(((toDraw.getHeight()/2))-toDraw.yOffset())+yOffset));
					}
				}
			}
		}
	}
	
	private void drawGrid(Grid grid) {
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getColumns(); j++) {
				Block toPrint = grid.get(i, j);
				if (toPrint != null) {	
					drawBlock(toPrint, grid.getWorldLocationX(j), grid.getWorldLocationY(i));
				}
			}
		}
	}
	
	private void drawActiveTetronimo() {
		if (GridManager.getInstance().hasTetromino()) {
			int[][]positions = GridManager.getInstance().geTetromino().getShape();
			for (int i = 0; i < positions.length; i++) {
				for (int j = 0; j < positions[i].length; j++) {
					int row = i + GridManager.getInstance().geTetromino().getY();
					if (row >= 0 && row < mainGrid.getRows()) {
						if(positions[i][j] != 0) {
							Block toPrint = GridManager.getInstance().geTetromino().getBlock(positions[i][j]);
							drawBlock(toPrint, mainGrid.getWorldLocationX(GridManager.getInstance().geTetromino().getX()+j), mainGrid.getWorldLocationY(GridManager.getInstance().geTetromino().getY()+i));
						}
					}
				}
			}
		}
	}
	
	private void drawBlock(Block block, int x, int y) {
		Screen.getInstance().printImage(MediaManager.getInstance().LoadImage(block.getImagePath(), true), x, y);
		if (block.hasSubBlock()) 
			drawBlock(block.getSubBlock(), x, y);
		if (block.shouldShowMask()) 
			Screen.getInstance().printImage(MediaManager.getInstance().LoadImage(block.getMaskPath(), true), x, y);
	}
}
