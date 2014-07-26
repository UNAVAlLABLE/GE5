// This is the superclass for all game classes

package ge5.engine;

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
	protected String loadedSceneName;
	
	protected long gameTicks = 0;
	protected long skippedTicks = 0;
	protected long sceneTicks = 0;

	protected Game() {
		
		init();

		input = new Input();

		window = new Window(this, input, title, width, height);

	}
	
	void startGame(){
		
		scenes = GameLoader.scenes;
		
		start();
		
		if(loadedScene == null)
			
			loadScene(scenes.keys().nextElement());

		gameLoop2();
		
	}

//	private void gameLoop() {
//
//		long fixedTickTime = (1000000000L / tickRate);
//		long accumulatedTime = fixedTickTime;
//		long now;
//		long lastTime = System.nanoTime();
//		int skips;
//
//		while (true) {
//
//			now = System.nanoTime();
//
//			accumulatedTime += now - lastTime;
//
//			lastTime = now;
//
//			if (accumulatedTime >= fixedTickTime) {
//				
//				skips = (int) (accumulatedTime / fixedTickTime);
//
//				tick(skips);
//				
//				if(skips > 1){
//					
//					System.out.println("Skipped " + (skips-1) + " ticks");
//					
//				}
//
//				if (isPaused == false) {
//
//					loadedScene.tick(skips);
//
//				}
//
//				now = System.nanoTime();
//				window.renderGame(loadedScene);
//				System.out.println((System.nanoTime() - now) / 1000000.0f);
//				
//				input.clear();
//
//				accumulatedTime -= fixedTickTime * skips;
//
//			}
//
//		}
//
//	}
	
	// Second game loop that allows 1 second to catch up before skipping ticks
	private void gameLoop2() {
		
		// Maximum amount of time each tick should last
		long fixedTickTime = (1000000000L / tickRate);
		
		// The Amount of time behind
		long owedTime = fixedTickTime;
		
		long now = System.nanoTime();
		long lastTime = System.nanoTime();
		
		long skips = 1;
		
		while(true){
			
			now = System.nanoTime();
			owedTime += now - lastTime;
			lastTime = now;
			
			while (owedTime >= fixedTickTime) {
				
				skips = 1 + owedTime / (fixedTickTime * tickRate);
				
				if (skips > 1) {
					skippedTicks += skips-1;
					System.out.printf("Simulated %2d tick(s). Overall game preformance: %.2f%s%n", (skips - 1), (1 - (((float) skippedTicks)/gameTicks)) * 100, "%.");
				}
				
				tick((int) skips);

				if (isPaused == false) {
					loadedScene.tick((int) skips);
					sceneTicks += skips;
				}
				
				gameTicks += skips;
				window.renderGame(loadedScene);
				input.clear();
				
				owedTime -= fixedTickTime * skips;		
												
			}
			
		}
		
	}

	protected void loadScene(String name) {

		if(scenes.get(name) != null) {
		
			if(loadedScene != null){
				loadedScene.unload();
				System.out.println("Succesfully unloaded \"" + loadedSceneName + "\"");
			}
			
			loadedScene = scenes.get(name);
			loadedScene.load();
			System.out.println("Succesfully loaded \"" + name + "\"");
			loadedScene.start();
			
			loadedSceneName = name;
			
			sceneTicks = 0;

		} else {
			
			System.out.println("Failed to load \"" + name + "\"");
			
		}
		
	}
	
	protected Scene getLoadedScene () {
		
		return loadedScene;
		
	}
	
	protected String getLoadedSceneName () {
		
		return loadedSceneName;
		
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
