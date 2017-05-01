package b00289996.Singletons;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import b00289996.Components.PrintTimer;
import b00289996.Components.Vector2I;

public class Screen {
	
	private final int DEFAULT_SCREEN_WIDTH = 800, DEFAULT_SCREEN_HEIGHT = 600, WINDOW_WIDTH_OFFSET = 3, WINDOW_HEIGHT_OFFSET = 25;
	public int GetScreenWidth() { return DEFAULT_SCREEN_WIDTH; }
	public int GetScreenHeight() { return DEFAULT_SCREEN_HEIGHT; }
	Dimension windowResolution, panelResolution;
	Font boldFont = new Font("Ariel", Font.BOLD, 26), regularFont = new Font("Ariel", Font.PLAIN, 26), italicFont = new Font("Ariel", Font.PLAIN, 26);
	JFrame window;
	JPanel panel;
	BufferedImage bufferedImage;
	Graphics frameBuffer, renderer;
	boolean fullScreen = false;
	private static Screen screen;
	public static Screen getInstance() {
		if (screen == null) {
			screen = new Screen();
		}
		return screen;
	}
	
	// will always be printed last
	ArrayList<PrintTimer> timedPrints = new ArrayList<PrintTimer>();
	
	private Screen() {
		window = new JFrame("UWS Game Engine Design B00289996");
		windowResolution = new Dimension(DEFAULT_SCREEN_WIDTH+WINDOW_WIDTH_OFFSET, DEFAULT_SCREEN_HEIGHT+WINDOW_HEIGHT_OFFSET);
		panelResolution = new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		setFullScreen();
		panel = new JPanel();
		panel.setPreferredSize(panelResolution);
		window.add(panel, BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);
		InputManager.getInstance().RegisterInputListeners(panel);
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		renderer = window.getGraphics();
		bufferedImage = new BufferedImage(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		frameBuffer = bufferedImage.getGraphics();
		frameBuffer.setFont(regularFont);
	}
	
	public boolean isFullScreen() {
		return fullScreen;
	}
	
	public void setFullScreen() {
		if (!fullScreen) {
			GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
			if (monitor.isFullScreenSupported()) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				windowResolution = toolkit.getScreenSize();
				panelResolution.width = windowResolution.width;
				panelResolution.height = windowResolution.height;
				window.setUndecorated(true);
				monitor.setFullScreenWindow(window);
			    fullScreen = true;			
			    window.setCursor(toolkit.createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "Hide_Cursor"));
				System.out.println("Window set to Full-Screen");
			}
			else {
				System.out.println("Can't set Fullscreen");
			}
		}
		else {
			System.out.println("Window is already Fullscreen");
		}
		
	}
	
	public void printImageForTime(BufferedImage image, int x, int y, float time) {
		timedPrints.add(new PrintTimer(image, new Vector2I(x, DEFAULT_SCREEN_HEIGHT-(y+image.getHeight())), time));
	}
	
	public void printImage(BufferedImage image, int x, int y) {
		frameBuffer.drawImage(image, x, DEFAULT_SCREEN_HEIGHT-(y+image.getHeight()), null);
	}
	
	public void printText(String text, int x, int y, int style, int alignment) {
		if (style == 2) 
			frameBuffer.setFont(boldFont);
		else if (style == 3) 
			frameBuffer.setFont(italicFont);
		else 
			frameBuffer.setFont(regularFont);
		if (alignment == 2) 
			x -= frameBuffer.getFontMetrics().stringWidth(text)/2;
		else if (alignment == 3)
			x -= frameBuffer.getFontMetrics().stringWidth(text);
		frameBuffer.setColor(Color.WHITE);
		frameBuffer.drawString(text, x, DEFAULT_SCREEN_HEIGHT-y);
		frameBuffer.setColor(Color.BLACK);
	}
	
	public void update(float deltaTime) {
		for (int i = 0; i < timedPrints.size(); i++) {
			PrintTimer timer = timedPrints.get(i);
			if (timer.countDown(deltaTime)) {
				timedPrints.remove(i);
				i--;
			}
			else {
				frameBuffer.drawImage(timer.getImage(), timer.getX(), timer.getY(), null);
			}
		}
	}
	
	public void render() {	
		if (fullScreen)
			renderer.drawImage(bufferedImage, 0, 0, window.getWidth(), window.getHeight(), panel);
		else
			renderer.drawImage(bufferedImage, WINDOW_WIDTH_OFFSET, WINDOW_HEIGHT_OFFSET, panel);
	}
	
	public void clear() {
		frameBuffer.setColor(Color.BLACK);
		frameBuffer.fillRect(0, 0, panel.getWidth(), panel.getWidth());
	}
	
	public void close() {
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
}
