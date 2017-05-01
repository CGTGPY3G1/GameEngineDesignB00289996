package b00289996.Singletons;
import java.text.DecimalFormat;

public class Time {
	private float deltaTime = 0, totalTime = 0;
	private int fps = 0, fpsCounter = 0;
	private long startTime;
	private long lastTime;
	private final float syncTime = 1.0f/60f;
	public float getDeltaTime () { return deltaTime; }
	public int getFPS () { return fps; }
	
	private static Time time;
	public static Time getInstance() {
		if (time == null)
			time = new Time();
		return time;
	}
	private Time() {
		startTime = System.currentTimeMillis();
		lastTime = startTime;
	}
	
	
	public boolean update() {
		long currentTime = System.currentTimeMillis();
		float tempDelta = (currentTime - lastTime) * 0.001f;
		if (tempDelta >= syncTime) {
			if (currentTime - startTime >= 1000) {
				fps = fpsCounter;
				fpsCounter = 0;
				startTime = currentTime;
				System.out.println("FPS = " + getFPS());
			}
			deltaTime =  tempDelta;
			totalTime += tempDelta;
			lastTime = currentTime;
			fpsCounter++;
			System.out.println(toString());
			return true;
		}	
		return false;
	}
	
	@Override
	public String toString() {
		String toReturn = "Delta Time = " + new DecimalFormat("0.000").format(getDeltaTime()) + " | Run Time = ";
		float printTime = totalTime;
		if (printTime > 3600) {
			toReturn += ((int)(printTime / 3600)) + " hours, ";
			printTime = totalTime % 3600;
		}
		if (printTime > 60) {
			toReturn += ((int)(printTime / 60)) + " minutes, ";
			printTime = printTime % 60;
		}
		toReturn += ((int)(printTime % 60)) + " seconds";
		return toReturn;
	}
}
