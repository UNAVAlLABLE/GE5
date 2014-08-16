package ge5;




public abstract class Game implements tickable{

	// Defaults
	protected static String title = "Untitled Game";
	protected static int tickRate = 30;
	protected static int width = 400;
	protected static int height = 300;

	protected static boolean isPaused = false;

	public void startGame(){

		new Window(title, width, height);
		start();
		gameLoop();

	}

	private void gameLoop() {

		// Maximum amount of time each tick should last
		final long fixedTickTime = 1000000000L / tickRate;

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

				//				if (skips > 1)
				//					System.out.println("Simulated " + (skips - 1) + " tick(s)");

				tick((int) skips);

				// Temporary
				if(Input.up)GameRender.yOffset -= 10 * skips;
				if(Input.down)GameRender.yOffset += 10 * skips;
				if(Input.left)GameRender.xOffset -= 10 * skips;
				if(Input.right)GameRender.xOffset += 10 * skips;
				if(Input.e) GameRender.scale -= 0.01f;
				if(Input.q) GameRender.scale += 0.01f;
				if(Input.space){GameRender.xOffset = 0; GameRender.yOffset = 0;}

				if (isPaused == false && Scene.loadedScene != null)
					Scene.loadedScene.tick((int) skips);

				Window.gameRender.renderGame();

				owedTime -= fixedTickTime * skips;

			}

		}

	}

	protected void setPaused(final Boolean b) {
		isPaused = b;
	}

	@Override
	public String toString () {
		return getClass().getName();
	}

}
