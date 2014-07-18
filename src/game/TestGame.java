package game;

import ge5.*;

public class TestGame extends Game {

	protected void init() {
						
	}

	protected void start() {
				
	}

	protected void tick(int skips) {
		
		if(gameTicks == 600)
			loadScene("game.Scene2");
				
	}

}
