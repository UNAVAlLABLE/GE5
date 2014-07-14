package ge5;

public abstract class Scene {
	
	private int[] pixels;
	
	// This is in tiles
	protected int width;
	protected int height;
	
	// This is in pixels
	protected int pixelWidth;
	protected int pixelHeight;
	
	protected Scene(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		pixelWidth = width * 32;
		pixelHeight = height * 32;
		
	}
	
	protected void load() {
		// Loading allocates memory for the map
		pixels = new int[width * height * 32 * 32];
	}
	
	protected void unload() {
		// Unloading deallocates the memory (not sure if this is how it's done)
		pixels = null;
	}
	
	protected abstract void tick(int skips);
	
	protected int[] getPixels() {
		return pixels;
	}
	
	// Convenience method for filling a tile with a color
	protected void drawColor(int color, int i, int j) {
		
		int xOffset = i * 32;
		int yOffset = j * 32;
		
		for (int x = 0; x < 32; x++) {
						
			for (int y = 0; y < 32; y++) {
				
				pixels[xOffset + x + (yOffset + y) * width * 32] = color;
				
			}
			
		}
		
	}
	
}