//Scratchpad class to test out stuff

package ge5;

public class TestGame extends Game {

	@Override
	protected void init() {
		//For testing
		fixedTickRate = 1;
		isRunning = true;
	}

	@Override
	protected void start() {
		gameLoop();
	}

	@Override
	protected void tick(long delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void fixedTick() {
		// TODO Auto-generated method stub
		
	}

}
