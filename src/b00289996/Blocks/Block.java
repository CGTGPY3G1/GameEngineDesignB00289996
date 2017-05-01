package b00289996.Blocks;

import java.util.ArrayList;

import b00289996.Components.MathOperation;
import b00289996.Interfaces.Behaviour;
import b00289996.Interfaces.Observer;
import b00289996.Interfaces.Subject;

public abstract class Block implements Subject {
	private static int blockCount = 1;
	private static int getNewBlockID() { return ++blockCount; }
	private int iD;
	public int getID() { return iD; }
	private final static String filePath = "/Blocks/", maskFilePath =  "/Masks/";
	protected MathOperation mathOperation;
	private String imageName = "Red";
	public String getImageName() { return imageName; }
	public String getImagePath() { return filePath+imageName+".png"; }
	private String maskName = "WhiteOverlay";
	public String getMaskName() { return maskName; }
	public String getMaskPath() { return maskFilePath+maskName+".png"; }
	protected boolean showMask = false;
	public boolean shouldShowMask() { return showMask; }
	protected Block subBlock;
	public Block getSubBlock() { return subBlock; }
	public void addSubBlock(Block subBlock) { this.subBlock = subBlock; }
	public void removeSubBlock() { subBlock = null; }
	public boolean hasSubBlock() { return subBlock != null; }
	protected boolean active = false;
	public boolean isActive() { return active; }
	MathOperation getScoreModifier() { return mathOperation; }
	public int getPointValue() { return mathOperation.getValue(); }
	public Block(String imageName, String maskName) {
		if (imageName != null) 
			this.imageName = imageName;
		if (maskName != null) 
			this.maskName = maskName;
		iD = getNewBlockID();
	}
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(getScoreModifier());
		}
	}
	
	@Override
	public void removeAllObservers() {
		observers.clear();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Block) {
			if (getID() == ((Block)other).getID()) 
				return true;
			else 
				return false;
		}
		return false;
	}
	
	Behaviour audioPlaybackBehaviour; 
	public void setAudioPlaybackBehaviour(Behaviour audioPlaybackBehaviour) {
		this.audioPlaybackBehaviour = audioPlaybackBehaviour;
	}
	
	public abstract void Activate();
	
}
