package game;

import ge5.*;

public class TestGame extends Game {
	
	public TestGame () {
		
		width = 800;
		height = 600;
		tickRate = 120;
		
	}

	public void start() {
		
		loadScene("game.Scene1");
												
	}

	public void tick(int skips) {
				
	}

}
