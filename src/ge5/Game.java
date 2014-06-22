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
	protected int fixedTickRate = 1;
	
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
		
		gameLoop2();
		
	}
	
	protected void gameLoop() {
		int ticksPerFrame = 1;
		long fixedFrameTime = 1000000000L / fixedTickRate;
		long lastTime = System.nanoTime();
		
		while (isRunning) {
			try {
				long previousFrameTime = System.nanoTime() - lastTime;		
				long waitTime = fixedFrameTime - previousFrameTime;
					
				if (waitTime > 0) {
					//Game is running normally
					Thread.sleep(waitTime / 1000000L, (int) (waitTime % 1000000L));
					ticksPerFrame++;
				} else {
					//Game is lagging
					//Add number of skipped ticks
					ticksPerFrame += (int) (-waitTime / fixedFrameTime);
				}
				
				//Print the "instantaneous" frame rate
				System.out.println(1000000000.0f / (System.nanoTime() - lastTime));
				
				lastTime = System.nanoTime();
				
				while (ticksPerFrame > 0) {
					fixedTick();
					ticksPerFrame--;
				}
			} catch (Exception e) {
				//Probably will never happen
			}
		}		
		
	}
	
	protected void gameLoop2() {
		
		long fixedTickTime = 1000000000L / fixedTickRate;
		long lastTime = System.nanoTime();
		long waitTime = 0;
		long overflow = 0;
		
		start();
		
		while (isRunning) {
			
			lastTime = System.nanoTime();
					
			if (waitTime >= fixedTickTime) {
				
				waitTime = waitTime - fixedTickTime;
				
				overflow += waitTime;
				
				if (overflow >= fixedTickTime) {
					
					System.out.println("fixed Tick Skipped");
				
					overflow -= fixedTickTime;
					
				}else
				
					fixedTick();
				
			}
			
			waitTime += System.nanoTime() - lastTime;
				
		}		
		
	}
	
	protected abstract void init();

	protected abstract void start();
	
	protected abstract void tick(long delta);
	
	protected abstract void fixedTick();

}
