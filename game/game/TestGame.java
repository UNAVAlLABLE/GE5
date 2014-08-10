package game;

import ge5.Game;
import ge5.Scene;

public class TestGame extends Game {

	public TestGame () {

		width = 800;
		height = 600;
		tickRate = 120;

	}

	@Override
	public void start() {

		Scene.loadScene("game.Scene1");

	}

	@Override
	public void tick(final int skips) {

	}

}
