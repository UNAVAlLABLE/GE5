// This is the superclass for all game classes

package ge5.engine;

import java.util.Hashtable;

public abstract class Game implements Runnable, tickable {

	// Defaults
	protected static String title = "Untitled Game";
	protected static int tickRate = 30;
	protected static int width = 400;
	protected static int height = 300;

	protected static boolean isPaused = false;

	protected static Hashtable<String, Scene> scenes;
	
	protected static Scene loadedScene;
	protected static String loadedSceneName;
	
	protected static long gameTicks = 0;
	protected static long skippedTicks = 0;
	protected static long sceneTicks = 0;
	
	public void run(){
		
		init();

		new Window(title, width, height);
		
		scenes = GameLoader.scenes;
		
		start();
		
		if(loadedScene == null)
			
			loadScene(scenes.keys().nextElement());

		gameLoop2();
		
	}
	
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
			
			if (owedTime >= fixedTickTime) {
				
				skips = 1 + owedTime / (fixedTickTime * tickRate);
				
				if (skips > 1) {
					skippedTicks += skips-1;
					System.out.printf("Simulated %2d tick(s). Overall game preformance: %.2f%s%n", (skips - 1), (1 - (((float) skippedTicks)/gameTicks)) * 100, "%.");
				}
				
				tick((int) skips);
				
				if(Input.up)GameRender.yOffset -= 10 * skips;
				if(Input.down)GameRender.yOffset += 10 * skips;
				if(Input.left)GameRender.xOffset -= 10 * skips;
				if(Input.right)GameRender.xOffset += 10 * skips;
				if(Input.e && GameRender.tileSize >= 2)GameRender.tileSize -= 1 + 0.1 * GameRender.tileSize * skips;
				if(Input.q && GameRender.tileSize <= 512)GameRender.tileSize += 1 + 0.1 * GameRender.tileSize * skips;
				if(Input.space){GameRender.xOffset = 0; GameRender.yOffset = 0;}

				if (isPaused == false) {
					loadedScene.tick((int) skips);
					sceneTicks += skips;
				}
				
				gameTicks += skips;
				Window.gameRender.render();
				
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

}
