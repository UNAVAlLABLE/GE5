package game;

import ge5.*;

public class TestGame extends Game {
	
	public TestGame () {
		
		width = 800;
		height = 600;
		tickRate = 120;
		
	}

	public void start() {
		
		loadScene("game.Scene1");
		
		// Create a new AsyncTask with iterations set to 4
		new AsyncTask (4) {

			public void run() {

				for (int i = 0; i < 10; i++) {
					
					System.out.println("Iteration " + this.getIteration() + " - " + i);
					
				}
				
			}
			
		};
												
	}

	public void tick(int skips) {
				
	}

}
