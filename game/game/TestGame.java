package game;

import ge5.engine.Game;

public class TestGame extends Game {
	
	public TestGame () {
		
		width = 800;
		height = 600;
		tickRate = 60;
		
	}

	public void start() {
		
		loadScene("game.Scene1");
						
	}

	public void tick(int skips) {
				
	}

}
