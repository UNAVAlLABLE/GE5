// This is the superclass for all game classes

package ge5;

public abstract class Game {

	// Defaults
	protected String title = "Untitled Game";
	protected int tickRate = 30;
	protected int width = 400;
	protected int height = 300;

	protected final Window window;
	protected final Input input;

	protected boolean isPaused = false;

	Scene[] scenes;
	Scene loadedScene;
	int loadedSceneIndex = -1;

	protected Game() {
		
		init();

		input = new Input();

		window = new Window(this, input, title, width, height);
		
		start();

		gameLoop();

	}

	private void gameLoop() {

		long fixedTickTime = (1000000000L / tickRate);
		long accumulatedTime = fixedTickTime;
		long now;
		long lastTime = System.nanoTime();

		while (true) {

			now = System.nanoTime();

			accumulatedTime += now - lastTime;

			lastTime = now;

			if (accumulatedTime >= fixedTickTime) {

				tick((int) (accumulatedTime / fixedTickTime));

				if (isPaused = false) {

					loadedScene.tick((int) (accumulatedTime / fixedTickTime));

				}

				window.renderGame();

				input.clear();

				accumulatedTime = accumulatedTime - fixedTickTime * (accumulatedTime / fixedTickTime);

			}

		}

	}

	protected void loadScene(int index) {

		if (loadedSceneIndex == -1)
			
			return;

		else if (index == -1)

			loadedScene.unload();
			
			else {
			
				loadedScene = scenes[index];
				loadedScene.load();
				loadedSceneIndex = index;

			}

	}

	protected int getLoadedSceneindex() {

		return loadedSceneIndex;

	}

	protected void pause() {

		isPaused = true;

	}

	protected void unpause() {

		isPaused = false;

	}

	protected abstract void init();

	protected abstract void start();

	protected abstract void tick(int skips);

}
