package game;

import ge5.engine.Game;

public class TestGame extends Game {

	protected void init() {
		
		width = 800;
		height = 600;
		tickRate = 30;
								
	}

	protected void start() {
		
		loadScene("game.Scene1");
						
	}

	protected void tick(int skips) {
				
	}

}
