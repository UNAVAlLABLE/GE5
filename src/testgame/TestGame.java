package testgame;

import ge5.Game;

public class TestGame extends Game {
	
	private float wantedHeight;
	private float cameraHeight;

	protected void init() {
		
		width = 1024;
		height = 768;
		tickRate = 60;
				
	}

	protected void start() {
		
		cameraHeight = wantedHeight = camera.getHeight();

	}

	protected void tick(int skips) {
		
		wantedHeight += input.getMouseWheelAxis();
		cameraHeight += 0.15f * (wantedHeight - cameraHeight);
		camera.setHeight(cameraHeight);
		
	}

}
