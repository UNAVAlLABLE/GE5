// This is the superclass for all game classes

package ge5;

import java.util.Hashtable;

public abstract class Game implements tickable, Runnable {

	// Defaults
	protected static String title = "Untitled Game";
	protected static int tickRate = 30;
	protected static int width = 400;
	protected static int height = 300;

	protected static boolean isPaused = false;

	protected static Hashtable<String, Scene> scenes;
	protected static Scene loadedScene;
	protected static String loadedSceneKey;
	
	public void run(){

		new Window(title, width, height);
		
		scenes = GameLoader.scenes;
		
		start();
		
		if(loadedScene == null)
			
			loadScene(scenes.keys().nextElement());

		gameLoop();
		
	}
	
	private void gameLoop() {
		
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
				
				skips = 1 + owedTime / (fixedTickTime * 15);
				
				if (skips > 1)
					System.out.println("Simulated " + (skips - 1) + " tick(s)");
				
				tick((int) skips);
				
				// Temporary
				if(Input.up)GameRender.yOffset -= 10 * skips;
				if(Input.down)GameRender.yOffset += 10 * skips;
				if(Input.left)GameRender.xOffset -= 10 * skips;
				if(Input.right)GameRender.xOffset += 10 * skips;
				
				//if(Input.e && GameRender.tileSize > 0)GameRender.tileSize -= 1;
				//if(Input.q && GameRender.tileSize < 9)GameRender.tileSize += 1;
				
				if(Input.e && GameRender.scale >= 0.1)GameRender.scale -= 0.01;				
				if(Input.q && GameRender.scale < 1)GameRender.scale += 0.01;
				
				if(Input.space){GameRender.xOffset = 0; GameRender.yOffset = 0;}

				if (isPaused == false) 
					loadedScene.tick((int) skips);
				
				Window.gameRender.renderGame();
				
				owedTime -= fixedTickTime * skips;		
												
			}
			
		}
		
	}

	protected void loadScene(String sceneKey) {
		
		System.out.println();

		if(scenes.get(sceneKey) != null) {
		
			if(loadedScene != null){
				loadedScene.unload();
				System.out.println("Unloaded " + loadedSceneKey + "");
			}
			
			loadedScene = scenes.get(sceneKey);
			loadedScene.load();
			System.out.println("Loaded " + sceneKey + "");
			loadedScene.start();
			
			loadedSceneKey = sceneKey;
			
		} else {
			
			System.out.println("Failed to load " + sceneKey + "");
			
		}
		
		System.out.println();
		
	}
	
	protected Scene getLoadedScene () {
		return loadedScene;
	}
	
	protected String getLoadedSceneKey () {
		return loadedSceneKey;
	}

	protected void pause() {
		isPaused = true;
	}

	protected void unpause() {
		isPaused = false;
	}

}
