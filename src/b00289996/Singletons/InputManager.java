package b00289996.Singletons;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class InputManager implements KeyListener {
	
	private boolean[] currentPressedKeys = new boolean[525];
	private boolean[] oldPressedKeys = new boolean[525];
	public void updateKeys() {
		oldPressedKeys = currentPressedKeys.clone();
	}
	
	private Queue<String> toProcess = new LinkedList<String>();
	
	private static InputManager instance;
	public static InputManager getInstance() {
		if (instance == null) {
			instance = new InputManager();
		}
		return instance;
	}
	
	public void RegisterInputListeners(Container windowPanel) {
		windowPanel.addKeyListener(this);
	}
	
	private InputManager() {
		for (int i = 0; i < currentPressedKeys.length; i++) {
			currentPressedKeys[i] = false;
		}
		for (int i = 0; i < oldPressedKeys.length; i++) {
			currentPressedKeys[i] = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int identifier = e.getKeyCode();
		if (identifier > -1 && identifier < currentPressedKeys.length) {
			currentPressedKeys[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int identifier = e.getKeyCode();
		if (identifier > -1 && identifier < currentPressedKeys.length) {
			currentPressedKeys[identifier] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		toProcess.add(""+e.getKeyChar());
	}
	
	public boolean getKey(int identifier) {	
		boolean toReturn = false;
		if (identifier > -1 && identifier < currentPressedKeys.length) {
			toReturn = currentPressedKeys[identifier];
		}
		return toReturn;
	}
	
	public boolean getKeyDown(int identifier) {	
		boolean toReturn = false;
		if (identifier > -1 && identifier < currentPressedKeys.length) {
			toReturn = !oldPressedKeys[identifier] && currentPressedKeys[identifier];
		}
		return toReturn;
	}
	
	public boolean getKeyUp(int identifier) {	
		boolean toReturn = false;
		if (identifier > -1 && identifier < oldPressedKeys.length) {
			toReturn = oldPressedKeys[identifier] && !currentPressedKeys[identifier];
		}
		return toReturn;
	}
	
	public String getStringInput() {
		String toReturn = "";
		if (!toProcess.isEmpty())
			toReturn = toProcess.remove();
		return toReturn;
	}
}
