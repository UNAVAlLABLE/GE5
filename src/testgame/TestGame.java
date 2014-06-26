//Scratch-pad class to test out stuff

package testgame;

import ge5.Game;
import ge5.Time;

public class TestGame extends Game {
	
	float x = 0;

	protected void init() {
		screen.setDimensions(800, 600);
		screen.center();
		isRunning = true;
	}

	protected void start() {}

	protected void fixedTick(int skips) {
		x += 0.01f;
	}

	protected void render() {
		gui.drawString("FPS: " + Time.avgFps, (int) (screen.getWidth() / 2 * Math.sin(x) + screen.getWidth() / 2), 10);
	}

}
