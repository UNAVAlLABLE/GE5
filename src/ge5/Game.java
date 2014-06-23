//This is the superclass for all game classes

package ge5;

public abstract class Game {
	
	// Default Game Title
	protected String title = "Untitled Game";

	// Default Game width
	protected int width = 1280;
	
	// Default Game height
	protected int height = 720;
	
	// Default Game scale
	protected int scale = 1;
	
	// Default Game Fixed tick rate
	protected int fixedTickRate = 60;
	
	protected Engine engine;
	
	protected Screen screen;
	
	protected Input input;

	protected boolean isRunning;
	
	protected Game() {
		
		init();
		
		engine = new Engine(this, fixedTickRate);
		
		input = new Input(this);
		
		// Must be declared after input
		screen = new Screen(this, title, width, height, scale);
		
		start();
		
		gameLoop3();
		
	}
	
	protected void gameLoop() {
		
		long fixedTickTime = 1000000000L / fixedTickRate;
		
		while (isRunning) {
			//Record start of tick
			long startTime = System.nanoTime();

			fixedTick(1);
			
			//Calculate how long the tick took
			long currentTickTime = System.nanoTime() - startTime;
			
			long waitTime = fixedTickTime - currentTickTime;
			
			//If the tick did not take longer than it should have, wait
			if (waitTime > 0) {
				
				long stopTime = System.nanoTime() + waitTime;
				
				//Let's waste some CPU cycles
				while(System.nanoTime() < stopTime) {}
			
			}
			
			System.out.println(1000000000.0f / (System.nanoTime() - startTime));
		}
		
	}
	
	protected void gameLoop2() {

		long fixedTickTime = 1000000000L / fixedTickRate;
		long startTime = System.nanoTime();
		long accumulatedTime = 0;
		long overflow = 0;
		long lastTick = System.nanoTime();

		start();

		while (isRunning) {

			startTime = System.nanoTime();

			if (accumulatedTime >= fixedTickTime) {

				accumulatedTime = accumulatedTime - fixedTickTime * (accumulatedTime / fixedTickTime);

				overflow += accumulatedTime;

				if (overflow >= fixedTickTime) {
					System.out.println("Fixed Tick Skipped");
					overflow -= fixedTickTime;
				} else {
					System.out.println(1000000000.0f / (System.nanoTime() - lastTick));
					lastTick = System.nanoTime();
					fixedTick(1);
				}
			}

			accumulatedTime += System.nanoTime() - startTime;

		}		

	}
	
	protected void gameLoop3() {

		long fixedTickTime = (1000L / fixedTickRate);
		long accumulatedTime = fixedTickTime;
		long lastTime;
		
		start();

		while (isRunning) {
	
			lastTime = System.currentTimeMillis();
	
			if (accumulatedTime >= fixedTickTime) {
					
				fixedTick((int) (accumulatedTime / fixedTickTime));
				
				accumulatedTime = accumulatedTime - fixedTickTime * (accumulatedTime / fixedTickTime);
	
			}
	
			accumulatedTime += (System.currentTimeMillis() - lastTime);
			
	}	

}

	
	protected abstract void init();

	protected abstract void start();
		
	protected abstract void fixedTick(int skips);

}
