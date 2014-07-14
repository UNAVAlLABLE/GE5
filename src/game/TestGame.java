package game;

import ge5.Scene;

public class TestGame extends ge5.Game {

	protected void init() {
		width = 800;
		height = 600;
		scenes = new Scene[1];
		scenes[0] = new Scene1(width / 32, height / 32);
		loadScene(0);
	}

	protected void start() {
		
	}

	protected void tick(int skips) {
				
	}

}
