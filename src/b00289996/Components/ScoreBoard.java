package b00289996.Components;

import java.util.ArrayList;
import java.util.Collections;

import b00289996.Interfaces.Observer;

public class ScoreBoard implements Observer {
	private int score, target;
	public int getScore() { return score; }
	public int getTargetScore() { return target; }
	public boolean targetReached() { return score >= target;} 
	private ArrayList<MathOperation> mathOperations = new ArrayList<MathOperation>();
	private CountDownTimer timer = new CountDownTimer(0.05f, false);
	boolean recalculate = false;
	public void setRefreshTime(float time) { timer.setTime(time); }
	
	public ScoreBoard(int targetScore) {
		newScoreboard(targetScore);
	}
	
	public void newScoreboard(int target) {
		this.target = target;
		reset();
	}
	
	public void update(float deltaTime) {
		if (recalculate)
			if (timer.countDown(deltaTime))
				finalize();
	}
	
	public void reset() {
		mathOperations.clear();
		timer.reset();
		score = 0;
		if (recalculate)
			recalculate = false;
	}
	
	public void addNewMathOp(MathOperation newOp) {
		mathOperations.add(newOp);
		if (!recalculate)
			recalculate = true;
		timer.reset();
	}
	
	public int calculate() {
		int extra = 0;
		if (!mathOperations.isEmpty()) {
			Collections.sort(mathOperations);
	
			for (int i = 0; i < mathOperations.size(); i++) {
				extra = mathOperations.get(i).applyOperation(extra);
			}
		}
		
		recalculate = false;
		return score + extra;
	}
	
	public void finalize() {
		score = calculate();
		mathOperations.clear();
	}

	@Override
	public void update(MathOperation mathOp) {
		addNewMathOp(mathOp);
	}
}
