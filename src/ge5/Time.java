package ge5;

public class Time {

	public static int avgFps;
	public static int fps;
	
	public static float deltaTime;
	public static float fixedDeltaTime;
	public static float time;
	
	protected static int numFrames;
	
	protected static void updateFps() {
		avgFps = (int) (numFrames / time);
		fps = (int) (1.0f / deltaTime);
	}
	
}