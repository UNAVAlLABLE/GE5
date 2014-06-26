//This is the superclass for all game classes

package ge5;

public abstract class Game {
	
	// Default Game Title
	protected String title = "Untitled Game";
		
	// Default Game Fixed tick rate
	protected int fixedTickRate = 60;
	
	protected Screen screen;
	
	protected GUI gui;
	
	protected Input input;

	protected boolean isRunning;
	
	protected Game() {
		
		input = new Input(this);
				
		// Must be declared after input
		gui = new GUI();
		screen = new Screen(this, gui, title);
				
		init();
		
		start();
		
		gameLoop();
		
	}
	
	protected void gameLoop() {

		long fixedTickTime = 1000000000L / fixedTickRate;
		long accumulatedTime = 0;
		long tickStartTime = System.nanoTime();
		
		Time.fixedDeltaTime = fixedTickTime;
		
		long tickStopTime;
		long frameTime;
		
		float delta;
		
		while (isRunning) {

			tickStopTime = System.nanoTime();
			frameTime = tickStopTime - tickStartTime;
			accumulatedTime += frameTime;
			delta = frameTime / 1000000000.0f;
			
			Time.deltaTime = delta;
			Time.time += delta;
			Time.numFrames++;
			Time.updateFps();
			
			tickStartTime = tickStopTime;

			if (accumulatedTime >= fixedTickTime) {
								
				fixedTick((int) (accumulatedTime / fixedTickTime));
				
				accumulatedTime -= fixedTickTime * (accumulatedTime / fixedTickTime);
				
			}
			
			render();
			
			screen.update();
			
		}		

	}

//	protected void gameLoop3() {
//
//		long fixedTickTime = (500L / fixedTickRate);
//		long accumulatedTime = fixedTickTime;
//		long lastTime;
//		
//		start();
//
//		while (isRunning) {
//	
//			lastTime = System.currentTimeMillis();
//	
//			if (accumulatedTime >= fixedTickTime) {
//					
//				fixedTick((int) (accumulatedTime / fixedTickTime));
//				
//				accumulatedTime = accumulatedTime - fixedTickTime * (accumulatedTime / fixedTickTime);
//	
//			}
//	
//			accumulatedTime += (System.currentTimeMillis() - lastTime);
//			
//		}	
//
//	}
	
	protected abstract void init();

	protected abstract void start();
		
	protected abstract void fixedTick(int skips);
	
	protected abstract void render();
	
}
