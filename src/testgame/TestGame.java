package testgame;

import ge5.Game;

public class TestGame extends Game {

	protected void init() {
		
		width = 800;
		height = 600;
		tickRate = 60;
				
	}

	protected void start() {}

	protected void tick(int skips) {
		
		camera.subHeight(input.getMouseWheelAxis() * 0.5f);
		
	}

}
