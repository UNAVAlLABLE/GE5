// This is the superclass for all game classes

package ge5;

import java.util.Hashtable;

public abstract class Game{

	// Defaults
	protected String title = "Untitled Game";
	protected int tickRate = 30;
	protected int width = 400;
	protected int height = 300;

	protected final Window window;
	protected final Input input;

	protected boolean isPaused = false;

	protected Hashtable<String, Scene> scenes;
	
	protected Scene loadedScene;
	protected int loadedSceneIndex = -1;

	protected Game() {
		
		init();

		input = new Input();

		window = new Window(this, input, title, width, height);

	}
	
	void startGame(){
		
		scenes = GameLoader.scenes;
		loadScene(scenes.keys().nextElement());
		
		start();

		gameLoop2();
		
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

				tick(skips);
				
				if(skips > 1){
					
					System.out.println("Skipped " + (skips-1) + " ticks");
					
				}

				if (isPaused == false && loadedSceneIndex != -1) {

					loadedScene.tick(skips);

				}

				now = System.nanoTime();
				window.renderGame(loadedScene);
				System.out.println((System.nanoTime() - now) / 1000000.0f);
				
				input.clear();

				accumulatedTime -= fixedTickTime * skips;

			}

		}

	}
	
	// Second game loop that allows 1 second to catch up before skipping ticks
	private void gameLoop2() {
		
		long fixedTickTime = (1000000000L / tickRate);
		long accumulatedTime = fixedTickTime;
		long now = System.nanoTime();
		long lastTime = System.nanoTime();
		int skips;
		
		while(true){
			
			now = System.nanoTime();

			accumulatedTime += now - lastTime;

			lastTime = now;
			
			skips = (int) (1 + accumulatedTime / 1000000000);
			
			if(skips > 1){
				
				System.out.println("Skipping " + (skips-1) + " ticks");
				
			}
			
			while (accumulatedTime >= fixedTickTime) {
				
				tick(skips);

				if (isPaused == false && loadedSceneIndex != -1) {

					loadedScene.tick(skips);

				}
				
				now = System.nanoTime();
				window.renderGame(loadedScene);
				System.out.println((System.nanoTime() - now) / 1000000.0f);
				
				input.clear();
				
				accumulatedTime -= fixedTickTime;
								
			}
			
		}
		
		
	}

	protected void loadScene(String name) {

		if(scenes.get(name) != null) {
		
			if(loadedScene != null)
				loadedScene.unload();
			
			loadedScene = scenes.get(name);
			loadedScene.load();
			loadedScene.start();

		} else {
			
			System.out.println("Failed to load \"" + name + "\".");
			
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
