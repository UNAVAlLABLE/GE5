package game;

public class Scene1 extends ge5.Scene {
	
	protected Scene1(int width, int height) {
		super(width, height);
	}

	protected void tick(int skips) {
		
		// This is to really hurt your eyes (and it really drops the frame rate)
		
		for (int i = 0; i < width; i++) {
			
			for (int j = 0; j < height; j++) {
				
				double value = Math.random();
				int color;
				
				if (value > 0.5) {
					color = 0x00FF0000;
				} else {
					color = 0x0000FF00;
				}
								
				drawColor(color, i, j);
				
			}
			
		}
	}

	protected void load() {
		
		// Always call this first
		super.load();
		
		for (int i = 0; i < width; i++) {
			
			for (int j = 0; j < height; j++) {
				
				double value = Math.random();
				int color;
				
				if (value > 0.5) {
					color = 0x00FF0000;
				} else {
					color = 0x0000FF00;
				}
								
				drawColor(color, i, j);
				
			}
			
		}
		
	}
	
}
