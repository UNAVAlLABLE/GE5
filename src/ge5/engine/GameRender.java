// Terminology
//
// Tile Map: Map of all tiles, where each unit represents a tile type
// World Space: Virtual world space in which every pixel of every tile represents a point. Top left corner is (0,0)
// View port: Pixels visible on the screen. The top right corner (0,0) on viewport corresponds with (xOffset, yOffset) in world space

package ge5.engine;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

class GameRender extends Canvas {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	private GraphicsConfiguration config;
	
	// The main raster of the view port
	private int[] pixels;
	
	// The world position of the top left corner of the view port
	int tileSize = 32;
	int xOffset = 0;
	int yOffset = 0;
	
	int[] test = new int[1000000];

	GameRender(int width, int height) {
		
		resizeImage(width, height);
		setSize(width, height);
		
		setFocusable(false);
		setIgnoreRepaint(true);
		
		// Temporary
		
		for(int i = 0; i < test.length; i++){
			
			test[i] = (int) (Math.random() * Integer.MAX_VALUE);
			
		}

	}
	
	void resizeImage(int width, int height) {

		config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		image = config.createCompatibleImage(width, height, Transparency.OPAQUE);
		
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}
	

	void render(Scene scene) {
		
		bufferStrategy = getBufferStrategy();
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
				
		// Temporary
		if(Input.up)yOffset -= 10;
		if(Input.down)yOffset += 10;
		if(Input.left)xOffset -= 10;
		if(Input.right)xOffset += 10;
		if(Input.e && tileSize >= 2)tileSize--;
		if(Input.q && tileSize <= 256)tileSize++;
			
		
		
								
		drawTiles(new Bitmap(test, 1000, 1000));
		
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		bufferStrategy.show();
		graphics.dispose();
		
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = 0;

	}

	void drawTiles(Bitmap tileMap) {
		
		// Preallocate variables
		int startX, endX, startY, endY, camX, camY, worldX, worldY;
		
		// Finds the bounds (in world space) of the pixels that are both visible and are on the tile map
		startX = (xOffset < 0) ? 0 : xOffset;
		endX = (xOffset + image.getWidth() > tileMap.width * tileSize) ?  tileMap.width * tileSize : image.getWidth() + xOffset;
		startY = (yOffset < 0) ? 0 : yOffset;
		endY = (yOffset + image.getHeight() > tileMap.height * tileSize) ?  tileMap.height * tileSize : image.getHeight() + yOffset;
		
		// For each visible row in world space defined by the bounds above
		for (worldY = startY; worldY < endY; worldY++) {
			
			// Find the corresponding row on the view port and multiply it by image.getWidth() to get its absolute position on pixels[]
			camX = (worldY - yOffset) * image.getWidth();
			
			// For each visible column in world space defined by the bounds above
			for (worldX = startX; worldX < endX; worldX++) {
				
				// Finds the corresponding column on the view port
				camY = (worldX - xOffset);
				
				// Mock-up of what the code to render tiles should be like once we implement tile classes and a tile map
				// pixels[camX + camY] = getTileImage(tileMap[worldX/tileSize,worldY/tileSize])[worldX%tileSize,worldY%tileSize];
				
				pixels[camX + camY] = tileMap.pixels[worldX/tileSize + ((worldY/tileSize) * tileMap.width)];
				
			}
			
		}
			
	}
	
	// Uses renderRaster to cull and render a buffered image with its own width and height
	void renderBufferedImage(BufferedImage i, int x, int y) {

		renderRaster(((DataBufferInt) i.getRaster().getDataBuffer()).getData(), i.getWidth(), i.getHeight(), x, y);

	}
	
	// Uses renderRaster to cull and render a buffered image scaled to a custom width and height
	void renderBufferedImage(BufferedImage i, int w, int h, int x, int y) {

		renderRaster(scaleRaster(((DataBufferInt) i.getRaster().getDataBuffer()).getData(), i.getWidth(),i.getHeight(), w, h), 2, h, x, y);

	}

	// Uses renderRaster to cull and render a bitmap with its own width and height
	void renderBitmap(Bitmap bitmap, int x, int y) {

		renderRaster(bitmap.pixels, bitmap.width, bitmap.height, x, y);

	}
	
	// Uses renderRaster to cull and render a bitmap scaled to a custom width and height
	void renderBitmap(Bitmap bitmap, int w, int h, int x, int y) {

		renderRaster(scaleRaster(bitmap.pixels, bitmap.width, bitmap.height, w, h), w, h, x, y);

	}
	
	// Culls and renders a raster onto the viewport
	void renderRaster(int[] p, int w, int h, int x, int y) {
		
		// Preallocate variables
		int startX, endX, startY, endY, offset;

		// Finds the bounds (in world space) of the pixels that are visible and are on the tile map
		startX = (x < 0) ? 0 : x;
		endX = (x + w > image.getWidth()) ? image.getWidth() : w + x;
		startY = (y < 0) ? 0 : y;
		endY = (y + h > image.getHeight()) ? image.getHeight() : h + y;
		
		// For each visible row in world space defined by the bounds above
		for (int row = startY; row < endY; row++) {
			
			// Calculate the position of the first pixel of the row on the view port
			offset = (row * image.getWidth() + startX);
			
			// For each pixel in the row
			for (int i = 0; i < endX - startX; i++) {
				
				pixels[i + offset] = p[i];
				
			}
			
		}

	}

	public static int[] scaleRaster(int[] pixels, int w1, int h1, int w2, int h2) {

		int[] result = new int[w2 * h2];

		int x1, y1, x2, y2;

		for (x2 = 0; x2 < w2; x2++) {

			for (y2 = 0; y2 < h2; y2++) {

				x1 = x2 * w1 / w2;
				y1 = y2 * h1 / h2;

				result[x2 + y2 * w2] = pixels[x1 + y1 * w1];

			}

		}

		return result;

	}

}
