// Tile Map: Map of all tiles, where each unit represents a tile type
// World Space: Virtual world space in which every pixel of every tile represents a point. Top left corner is (0,0)
// View port: Pixels visible on the screen. The top right corner (0,0) on view port corresponds with (xOffset, yOffset) in world space

package ge5;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.util.Arrays;

class GameRender extends Canvas{

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	private GraphicsConfiguration config;
	
	// The main raster of the view port
	public volatile static int[] pixels;
	
	// 2 to the power of this represents the tileSize
	static int tileSize = 5;
	
	// The world position of the top left corner of the view port
	static int xOffset = 0;
	static int yOffset = 0;
	int x;
	
	// Temporary
	public int[] test = new int[4000000];

	GameRender(int width, int height) {
		
		this.resizeImage(width, height);
		this.setSize(width, height);
		
		this.setFocusable(false);
		this.setIgnoreRepaint(true);
		
		for(int i = 0; i < test.length; i++)
			test[i] = (int) (Math.random() * Integer.MAX_VALUE);
				
	}
		
	void resizeImage(int width, int height) {

		config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		image = config.createCompatibleImage(width, height, Transparency.OPAQUE);
		
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}
	
	void renderGame() {
		
		bufferStrategy = getBufferStrategy();
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
				
		// Temporary
		renderTilemap2(new Bitmap(test, 2000, 2000));
								
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		bufferStrategy.show();
		graphics.dispose();
		
		Arrays.fill(pixels, 0);
	
	}

	void renderTilemap(final Bitmap tileMap) {
		
		final int viewWidth = image.getWidth();
		final int viewHeight = image.getHeight();
		
		// Finds the bounds (in world space) of the pixels that are both visible and are on the tile map
		final int startX = (xOffset < 0) ? 0 : xOffset;
		final int endX = (xOffset + viewWidth > tileMap.width<<tileSize) ?  tileMap.width<<tileSize :viewWidth + xOffset;
		final int startY = (yOffset < 0) ? 0 : yOffset;
		final int endY = (yOffset + viewHeight > tileMap.height<<tileSize) ?  tileMap.height<<tileSize : viewHeight + yOffset;
		
		int pixelsY, mapY, worldX;
		
		// For each visible row in world space defined by the bounds above
		for (int worldY = startY; worldY < endY; worldY++) {
			
			// Find the corresponding row on the view port and multiply it by image.getWidth() to get its absolute position on pixels[]
			pixelsY = (worldY - yOffset) * viewWidth;
			
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
		
		final int viewWidth = image.getWidth();
		final int viewHeight = image.getHeight();
		
		// Finds the bounds (in world space) of the pixels that are both visible and are on the tile map
		final int startX = (xOffset < 0) ? 0 : xOffset;
		final int endX = (xOffset + viewWidth > tileMap.width<<tileSize) ?  tileMap.width<<tileSize :viewWidth + xOffset;
		final int startY = (yOffset < 0) ? 0 : yOffset;
		final int endY = (yOffset + viewHeight > tileMap.height<<tileSize) ?  tileMap.height<<tileSize : viewHeight + yOffset;
		
		x = endY - startY;
		
		Thread t1 = new Thread() {

			public void run() {
				
				int pixelsY, mapY, worldX;

				// For each visible row in world space defined by the bounds above
				for (int worldY = startY; worldY < endY && x > 0; worldY++) {

					// Find the corresponding row on the view port and multiply it by image.getWidth() to get its absolute position on pixels[]
					pixelsY = (worldY - yOffset) * viewWidth;

					// Finds the y of the map tile the pixel occupies and multiply it by tileMap.width
					mapY = (worldY >> tileSize) * tileMap.width;

					// For each visible column in world space defined by the bounds above
					for (worldX = startX; worldX < endX; worldX++) {

						// Temporary
						pixels[(worldX - xOffset) + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

					}
					
					x--;

				}
			}
		};
		
		Thread t2 = new Thread() {

			public void run() {
				
				int pixelsY, mapY, worldX;

				// For each visible row in world space defined by the bounds above
				for (int worldY = endY - 1; worldY >= startY && x > 0; worldY--) {

					// Find the corresponding row on the view port and multiply it by image.getWidth() to get its absolute position on pixels[]
					pixelsY = (worldY - yOffset) * viewWidth;

					// Finds the y of the map tile the pixel occupies and multiply it by tileMap.width
					mapY = (worldY >> tileSize) * tileMap.width;

					// For each visible column in world space defined by the bounds above
					for (worldX = startX; worldX < endX; worldX++) {

						// Temporary
						pixels[(worldX - xOffset) + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

					}
					
					x--;

				}
			}
		};
		
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
		
		final int viewWidth = image.getWidth();
		final int viewHeight = image.getHeight();

		// Finds the bounds (in world space) of the pixels that are visible and are on the tile map
		final int startX = (x < 0) ? 0 : x;
		final int endX = (x + w > viewWidth) ? viewWidth : w + x;
		final int startY = (y < 0) ? 0 : y;
		final int endY = (y + h > viewHeight) ? viewHeight : h + y;
		
		int offset, i;
		
		// For each visible row in world space defined by the bounds above
		for (int row = startY; row < endY; row++) {
			
			// Calculate the position of the first pixel of the row on the view port
			offset = (row * viewWidth + startX);
			
			// For each pixel in the row
			for (i = 0; i < endX - startX; i++) {
				
				pixels[i + offset] = p[i];
				
			}
			
		}

	}

	public static int[] scaleRaster(int[] pixels, int w1, int h1, int w2, int h2) {

		int[] result = new int[w2 * h2];

		int x1, y1, x2, y2;

		for (y2 = 0; y2 < y2; y2++) {
			
			y1 = y2 * h1 / h2 * w1;

			for (x2 = 0; x2 < w2; x2++) {

				x1 = x2 * w1 / w2;

				result[x2 + y2 * w2] = pixels[x1 + y1];

			}

		}

		return result;

	}

}
