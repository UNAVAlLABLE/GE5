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

	protected Scene[] scenes;
	protected Scene loadedScene;
	protected int loadedSceneIndex = -1;

	protected Game() {
		
		init();

		input = new Input();

		window = new Window(this, input, title, width, height);

	}
	
	void startGame(){
		
		scenes = new Scene[GameLoader.scenes.size()];
		
		for (int i = 0; i < scenes.length; i++) {
			scenes[i] = GameLoader.scenes.get(i);
		}
		
		start();

		gameLoop();
		
	}

	private void gameLoop() {

		long fixedTickTime = (1000000000L / tickRate);
		long accumulatedTime = fixedTickTime;
		long now;
		long lastTime = System.nanoTime();
		int skips;

		while (true) {

			now = System.nanoTime();

			accumulatedTime += now - lastTime;

			lastTime = now;

			if (accumulatedTime >= fixedTickTime) {
				
				skips = (int) (accumulatedTime / fixedTickTime);
				
				if(skips > 1){
					
					System.out.println("Skipped " + (skips-1) + " ticks.");
					
				}

				tick(skips);

				if (isPaused == false && loadedSceneIndex != -1) {

					loadedScene.tick(skips);

				}

				now = System.nanoTime();
				window.renderGame(loadedScene);
				System.out.println((System.nanoTime() - now) / 1000000.0f);
				
				input.clear();

				accumulatedTime = accumulatedTime - fixedTickTime * (accumulatedTime / fixedTickTime);

			}

		}

	}

	protected void loadScene(int index) {

		if (index == -1){

			loadedScene.unload();
			loadedScene = null;
			
		} else {
			
				loadedScene = scenes[index];
				loadedScene.load();
				loadedSceneIndex = index;
				loadedScene.start();
				
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
