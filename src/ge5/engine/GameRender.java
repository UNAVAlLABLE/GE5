
package ge5.engine;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

class GameRender extends Canvas {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	private GraphicsConfiguration config;
	
	private int[] pixels;
	int tileSize = 32;
	
	int xOffset = 0;
	int yOffset = 0;

	GameRender(int width, int height) {
		
		resizeImage(width, height);
		setSize(width, height);
		
		setFocusable(false);
		setIgnoreRepaint(true);

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
		if(Input.up)yOffset++;
		if(Input.down)yOffset--;
		if(Input.left)xOffset++;
		if(Input.right)xOffset--;
								
		drawTiles(new Bitmap(new int[100], 10, 10));
		
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		bufferStrategy.show();
		graphics.dispose();
		
		Arrays.fill(pixels,0);

	}

	void drawTiles(Bitmap tileMap) {
		
		// Preallocate variables
		int startX, endX, startY, endY, camX, camY;
		
		// Finds the bounds (in world space) of the pixels that are visible and are on the tile map
		startX = (xOffset < 0) ? 0 : xOffset;
		endX = (xOffset + image.getWidth() > tileMap.width * tileSize) ?  tileMap.width * tileSize : image.getWidth() + xOffset;
		startY = (yOffset < 0) ? 0 : yOffset;
		endY = (yOffset + image.getHeight() > tileMap.height * tileSize) ?  tileMap.height * tileSize : image.getHeight() + yOffset;
		
		// For each visible row in world space defined by the bounds above
		for (int worldY = startY; worldY < endY; worldY++) {
			
			// Finds the corresponding row on the view port and multiplies it by image.getWidth() to get its value on pixels[]
			camX = (worldY - yOffset) * image.getWidth();
			
			// For each visible column in world space defined by the bounds above
			for (int worldX = startX; worldX < endX; worldX++) {
				
				// Finds the corresponding column on the view port
				camY = (worldX - xOffset);
				
				// pixels[camX + camY] = getTileImage(tileMap[worldX/tileSize,worldY/tileSize])[worldX%tileSize,worldY%tileSize];
				
			}
			
		}
			
	}

	void renderBitmap(Bitmap bitmap, int x, int y) {

		renderBitmap(bitmap.pixels, bitmap.width, bitmap.height, x, y);

	}
	
	void renderBitmap(int[] p, int w, int h, int x, int y) {
		
		int startX, endX, startY, endY, offset;

		startX = (x < 0) ? 0 : x;
		endX = (x + w > image.getWidth()) ? image.getWidth() : w + x;

		startY = (y < 0) ? 0 : y;
		endY = (y + h > image.getHeight()) ? image.getHeight() : h + y;
		
		for (int row = startY; row < endY; row++) {
			
			offset = (row * image.getWidth() + startX);
			
			for (int i = offset; i < offset + (endX - startX); i++) {
				
				pixels[i] = p[i];
				
			}
			
		}

	}

	public static int[] scale(int[] pixels, int w1, int h1, int w2, int h2) {

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
