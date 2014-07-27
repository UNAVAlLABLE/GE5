package game;

import ge5.engine.Game;

public class TestGame extends Game {

	protected void init() {
		
		tickRate = 60;
								
	}

	protected void start() {
		
		loadScene("game.Scene1");
						
	}

	protected void tick(int skips) {
				
	}

}
