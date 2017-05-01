package b00289996.Components;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import b00289996.Singletons.MediaManager;


public class Sprite {
	int SortLayer;
	private BufferedImage image;
	private int width = 0, height = 0;
	public int getWidth() { return width; }
	public int getHeight() { return  height; }
	public BufferedImage GetImage() { return image; }
	
	public Sprite(String imagePath) {
		LoadImage(imagePath);
		setDimensionsFromImage();
	}
	
	public Sprite(String imagePath, int sizeX, int sizeY) {
		setImage(LoadImage(imagePath), sizeX, sizeY);
	}
	
	private BufferedImage LoadImage(String imagePath) {
		return MediaManager.getInstance().LoadImage(imagePath, false);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		setDimensionsFromImage();
	}
	
	public void setImage(BufferedImage image, int sizeX, int sizeY) {
		if (image != null) {
			this.image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB );
			Graphics2D g2d = this.image.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.drawImage(image, 0, 0, sizeX, sizeY, null);
			g2d.dispose();
		}
		setDimensionsFromImage();
	}
	
	private void setDimensionsFromImage() {
		if (image != null) {
			width = image.getWidth();
			height = image.getHeight();	
		}
		else {
			width = 0;
			height = 0;
		}
	}
	
	@Override
	public String toString() {
		return "Width = " + width + " | Height = " + height;
	}
}
