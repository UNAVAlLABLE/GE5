package game;

import ge5.engine.Game;

public class TestGame extends Game {

	protected void init() {
		
		width = 400;
		height = 300;
		tickRate = 30;
								
	}

	protected void start() {
		
		loadScene("game.Scene1");
						
	}

	protected void tick(int skips) {
				
	}

}
