package b00289996.Singletons;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MediaManager {

	final int CLIPS_TO_CACHE = 5;
	HashMap<String, Clip[]> cachedClips;
	HashMap<String, BufferedImage> cachedImages;
	HashMap<String, Clip> toLoop;
	private static MediaManager mediaManager;
	public static MediaManager getInstance() {
		if (mediaManager == null) {
			mediaManager = new MediaManager();
		}
		return mediaManager;
	}
	
	public MediaManager() {
		cachedClips = new HashMap<String, Clip[]>();
		toLoop = new HashMap<String, Clip>();
		cachedImages = new HashMap<String, BufferedImage>();
	}
	
	public void LoadAudio(String path, boolean cached) {
		if (cached) {
			if (!isClipCached(path)) 
				cachedClips.put(path, ReadAudioClips(path));
			PlayClip(cachedClips.get(path));	
		}
		else {
			PlayOnce(path);
		}
	}
	
	public void LoopAudio(String path) {
		Clip newClip = ReadAudioClip(path);
		newClip.loop(Clip.LOOP_CONTINUOUSLY);
		toLoop.put(path, newClip);
	}
	
	public void StopLoopedAudio(String path) {
		Clip newClip = ReadAudioClip(path);
		newClip.loop(Clip.LOOP_CONTINUOUSLY);
		if(toLoop.containsKey(path)) {
			Clip toStop = toLoop.remove(path);
			toStop.stop();
		}
	}
	
	private void PlayOnce(String path) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(path));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
        
	}
	
	private Clip[] ReadAudioClips(String path) {
		Clip[] clips = new Clip[CLIPS_TO_CACHE];
		try {
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(path));
	         for (int i = 0; i < CLIPS_TO_CACHE; i++) {
	        	 Clip clip = AudioSystem.getClip();
	        	 clip.open(audioInputStream);
	        	 clips[i] = clip;
	         }
	         System.out.println("Loaded Audio Clip from " + path);
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	         e.printStackTrace();
	         System.out.println("Couldn't Load Audio Clip from " + path);
	    }
		return clips;
	}
	
	private Clip ReadAudioClip(String path) {
		Clip clip = null;
		try {
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(path));
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	         System.out.println("Loaded Audio Clip from " + path);
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	         e.printStackTrace();
	         System.out.println("Couldn't Load Audio Clip from " + path);
	    }
		return clip;
	}
	
	private void PlayClip(Clip[] clips) {
		for (int i = 0; i < clips.length; i++) {
			Clip clip = clips[i];
			if (clip.getMicrosecondPosition() == 0 && !clip.isActive()) {
				clip.start();
				break;
			}
			else if (clip.getMicrosecondPosition() == clip.getMicrosecondLength()) {
				clip.setMicrosecondPosition(0);
				clip.start();
				break;
			}
		}
	}
	
	private boolean isClipCached(String key) {
		return cachedClips.containsKey(key);
		
	}
	
	public void Clear() {
		cachedClips.clear();
		cachedImages.clear();
	}
	
	public BufferedImage LoadImage(String imagePath, boolean cached) {
		BufferedImage toReturn = null;
		if (cached) {
			if (isImageCached(imagePath)) {
				toReturn = cachedImages.get(imagePath);
			}
			else {
				toReturn = ReadImage(imagePath);
				if (toReturn != null) {
					cachedImages.put(imagePath, toReturn);
				}
			}
		}
		else {
			toReturn = ReadImage(imagePath);
		}
		return toReturn;
	}
	
	private boolean isImageCached(String key) {
		return cachedImages.containsKey(key);
	}
	
	public BufferedImage ReadImage(String imagePath) {
		BufferedImage newImage = null;
		try {
			newImage = ImageIO.read(getClass().getResource(imagePath));
			System.out.println("Loaded Image from " + imagePath);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't Load Image from " + imagePath);
		}
		return newImage;
	}
}
