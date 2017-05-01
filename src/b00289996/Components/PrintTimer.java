package b00289996.Components;

import java.awt.Image;

public class PrintTimer extends RemovalTimer {
	private Image image;
	public Image getImage() { return image; }
	
	public PrintTimer(Image image, Vector2I position, float time) {
		super(position, time);
		this.image = image;
	}

}
