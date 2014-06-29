//This is the superclass for all game classes

package ge5;

public abstract class Game {
	
	// Defaults
	protected String title = "Untitled Game";
	protected int tickRate = 30;
	protected int width = 400;
	protected int height = 300;
	
	protected final Window window;
	protected final Input input;

	protected boolean isRunning = true;
	
	protected Game() {
		
		init();
		
		input = new Input(this);
				
		window = new Window(this, input, title, width, height);
				
		start();
		
		gameLoop();
		
	}

	protected void gameLoop() {

		long fixedTickTime = (1000000000L / tickRate);
		long accumulatedTime = fixedTickTime;
		long now; 
		long lastTime = System.nanoTime();

		while (isRunning) {
			
			now = System.nanoTime();
			
			accumulatedTime += now - lastTime;
			
			lastTime = now;
	
			if (accumulatedTime >= fixedTickTime) {
				
				System.out.println("Tick");
					
				tick((int) (accumulatedTime / fixedTickTime));
				
				window.renderGame();
								
				accumulatedTime = accumulatedTime - fixedTickTime * (accumulatedTime / fixedTickTime);
	
			}
			
		}	

	}
	
	protected abstract void init();

	protected abstract void start();
		
	protected abstract void tick(int skips);
	
}
