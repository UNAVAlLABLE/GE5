// Tile Map: Map of all tiles, where each unit represents a tile type
// World Space: Virtual world space in which every pixel of every tile represents a point. Top left corner is (0,0)
// View port: Pixels visible on the screen. The top right corner (0,0) on view port corresponds with (xOffset, yOffset) in world space

package ge5;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

class GameRender extends Canvas{

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
		
	// The main raster of the view port
	public volatile static int[] pixels;
	
	// 2 to the power of this represents the tileSize
	static int tileSize = 5;
	
	// The world position of the top left corner of the view port
	static int xOffset = 0;
	static int yOffset = 0;
	
	final private int baseImageWidth;
	final private int baseImageHeight;
	private static int imageWidth;
	private static int imageHeight;
	
	private static float lastScale = 1;
	static float scale = 1;
	
	// Temporary
	int rowsToDraw;
	public int[] test = new int[4000000];

	GameRender(int width, int height) {
		
		imageWidth = width;
		imageHeight = height;
		baseImageWidth = imageWidth;
		baseImageHeight = imageHeight;
		
		this.setSize(imageWidth, imageHeight);
		this.resizeImage(imageWidth, imageHeight);
		this.setFocusable(false);
		this.setIgnoreRepaint(true);
		
		// Temporary
		for(int i = 0; i < test.length; i++)
			test[i] = (int) (Math.random() * Integer.MAX_VALUE);
		
	}
		
	void resizeImage(int width, int height) {
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}
	
	void renderGame() {
		
		if(scale != lastScale){
			
			imageWidth = (int) (baseImageWidth * scale);
			imageHeight = (int) (baseImageHeight * scale);
		
			resizeImage(imageWidth, imageHeight);
			
			lastScale = scale;
			
		}
		
		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
		
		renderTilemap(new Bitmap(test,4000,4000));
								
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				
		bufferStrategy.show();
		graphics.dispose();
		
		Arrays.fill(pixels, 0);
	
	}

	void renderTilemap(final Bitmap tileMap) {
		
		// Finds the bounds (in world space) of the pixels that are both visible and are on the tile map
		final int startX = (xOffset < 0) ? 0 : xOffset;
		final int endX = (xOffset + imageWidth > tileMap.width<<tileSize) ?  tileMap.width<<tileSize :imageWidth + xOffset;
		final int startY = (yOffset < 0) ? 0 : yOffset;
		final int endY = (yOffset + imageHeight > tileMap.height<<tileSize) ?  tileMap.height<<tileSize : imageHeight + yOffset;
		
		int pixelsY, mapY, worldX;
		
		// For each visible row in world space defined by the bounds above
		for (int worldY = startY; worldY < endY; worldY++) {
			
			// Find the corresponding row on the view port and multiply it by image.getWidth() to get its absolute position on pixels[]
			pixelsY = (worldY - yOffset) * imageWidth;
			
			// Finds the y of the map tile the pixel occupies and multiply it by tileMap.width
			mapY = (worldY>>tileSize) * tileMap.width;
			
			// For each visible column in world space defined by the bounds above
			for (worldX = startX; worldX < endX; worldX++) {
				
				// Temporary
				pixels[(worldX - xOffset) + pixelsY] = tileMap.pixels[(worldX>>tileSize) + mapY];
				
			}
			
		}
		
	}
	
	void renderTilemap2(final Bitmap tileMap) {
		
		final int startX = (xOffset < 0) ? 0 : xOffset;
		final int endX = (xOffset + imageWidth > tileMap.width<<tileSize) ?  tileMap.width<<tileSize :imageWidth + xOffset;
		final int startY = (yOffset < 0) ? 0 : yOffset;
		final int endY = (yOffset + imageHeight > tileMap.height<<tileSize) ?  tileMap.height<<tileSize : imageHeight + yOffset;
		
		rowsToDraw = endY - startY;
				
		Thread t1 = new Thread (() -> {
				
				int pixelsY, mapY, worldX, worldY;

				for (worldY = startY; worldY < endY && rowsToDraw > 0; worldY++) {

					pixelsY = (worldY - yOffset) * imageWidth;
					mapY = (worldY >> tileSize) * tileMap.width;

					for (worldX = startX; worldX < endX; worldX++)
						
						pixels[(worldX - xOffset) + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

					rowsToDraw--;
					
				}
												
		});
		
		Thread t2 = new Thread (() -> {

				int pixelsY, mapY, worldX, worldY;

				for (worldY = endY - 1; worldY >= startY && rowsToDraw > 0; worldY--) {
					
					pixelsY = (worldY - yOffset) * imageWidth;
					mapY = (worldY >> tileSize) * tileMap.width;

					for (worldX = startX; worldX < endX; worldX++) 
						
						pixels[(worldX - xOffset) + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

					rowsToDraw--;

				}
								
		});
				
		t1.start();
		t2.start();
		
		while(t1.isAlive() || t2.isAlive());
		
	}
		
	// Uses drawRaster to cull and render a buffered image with its own width and height
	public void drawBufferedImage(BufferedImage i, int x, int y) {

		drawRaster(((DataBufferInt) i.getRaster().getDataBuffer()).getData(), i.getWidth(), i.getHeight(), x, y);

	}
	
	// Uses drawRaster to cull and render a buffered image scaled to a custom width and height
	public void drawBufferedImage(BufferedImage i, int w, int h, int x, int y) {

		drawRaster(scaleRaster(((DataBufferInt) i.getRaster().getDataBuffer()).getData(), i.getWidth(),i.getHeight(), w, h), 2, h, x, y);

	}

	// Uses drawRaster to cull and render a bitmap with its own width and height
	public void drawBitmap(Bitmap bitmap, int x, int y) {

		drawRaster(bitmap.pixels, bitmap.width, bitmap.height, x, y);

	}
	
	// Uses drawRaster to cull and render a bitmap scaled to a custom width and height
	public void drawBitmap(Bitmap bitmap, int w, int h, int x, int y) {

		drawRaster(scaleRaster(bitmap.pixels, bitmap.width, bitmap.height, w, h), w, h, x, y);

	}
	
	// Culls and renders a raster onto the view sport
	public void drawRaster(int[] p, int w, int h, int x, int y) {

		// Finds the bounds (in world space) of the pixels that are visible and are on the tile map
		final int startX = (x < 0) ? 0 : x;
		final int endX = (x + w > imageWidth) ? imageWidth : w + x;
		final int startY = (y < 0) ? 0 : y;
		final int endY = (y + h > imageHeight) ? imageHeight : h + y;
		
		int offset, i;
		
		// For each visible row in world space defined by the bounds above
		for (int row = startY; row < endY; row++) {
			
			// Calculate the position of the first pixel of the row on the view port
			offset = (row * imageWidth + startX);
			
			// For each pixel in the row
			for (i = 0; i < endX - startX; i++) {
				
				pixels[i + offset] = p[i];
				
			}
			
		}
		
	}
	
	// Untested optimized version of scale()
	public static int[] scaleRaster(int[] pixels, int w1, int h1, int w2, int h2) {

		int[] result = new int[w2 * h2];

		int x1, y1, x2, y2, t;

		for (y2 = 0; y2 < y2; y2++) {
			
			y1 = y2 * h1 / h2 * w1;
			
			t = y2 * w2;

			for (x2 = 0; x2 < w2; x2++) {

				x1 = x2 * w1 / w2;

				result[x2 + t] = pixels[x1 + y1];

			}

		}

		return result;

	}

}
