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
	static volatile int xOffset = 0;
	static volatile int yOffset = 0;

	private volatile static int imageWidth;
	private volatile static int imageHeight;

	static float scale = 1;

	// Temporary
	int rowsToDraw;
	public volatile int[] test = new int[25];
	final private int baseImageWidth;
	final private int baseImageHeight;
	private static float lastScale = 1;

	GameRender(final int width, final int height) {

		imageWidth = width;
		imageHeight = height;
		baseImageWidth = imageWidth;
		baseImageHeight = imageHeight;

		this.setSize(imageWidth, imageHeight);
		resizeImage(imageWidth, imageHeight);
		setFocusable(false);
		setIgnoreRepaint(true);

		// Temporary
		for(int i = 0; i < test.length; i++)
			test[i] = (int) (Math.random() * Integer.MAX_VALUE);
		
	}

	void resizeImage(final int width, final int height) {

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	}

	void renderGame() {

		if(scale != lastScale) {

			final int roundedDown = (int) scale;

			final float factor = (float) Math.pow(2, scale - roundedDown);

			tileSize = roundedDown;
			
			imageWidth = (int) (baseImageWidth / factor);
			imageHeight = (int) (baseImageHeight / factor);

			resizeImage(imageWidth, imageHeight);

			lastScale = scale;

		}

		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();

		drawBitmap(new Bitmap(test, 5, 5), 1000, 1000, 0, 0);

		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		bufferStrategy.show();
		graphics.dispose();

		Arrays.fill(pixels, 0);

	}

	void renderTilemap(final Bitmap tileMap) {

		// Finds the bounds (in world space) of the pixels that are both visible and are on the tile map
		final int startX = xOffset < 0 ? 0 : xOffset;
		final int endX = xOffset + imageWidth > tileMap.width<<tileSize ?  tileMap.width<<tileSize :imageWidth + xOffset;
		final int startY = yOffset < 0 ? 0 : yOffset;
		final int endY = yOffset + imageHeight > tileMap.height<<tileSize ?  tileMap.height<<tileSize : imageHeight + yOffset;

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
				pixels[worldX - xOffset + pixelsY] = tileMap.pixels[(worldX>>tileSize) + mapY];

			}

		}

	}

	void renderTilemap2(final Bitmap tileMap) {

		final int startX = xOffset < 0 ? 0 : xOffset;
		final int endX = xOffset + imageWidth > tileMap.width<<tileSize ?  tileMap.width<<tileSize :imageWidth + xOffset;
		final int startY = yOffset < 0 ? 0 : yOffset;
		final int endY = yOffset + imageHeight > tileMap.height<<tileSize ?  tileMap.height<<tileSize : imageHeight + yOffset;

		rowsToDraw = endY - startY;

		final AsyncTask t1 = new AsyncTask () {

			@Override
			public void run() {

				int pixelsY, mapY, worldX, worldY;

				for (worldY = startY; worldY < endY && rowsToDraw > 0; worldY++) {

					pixelsY = (worldY - yOffset) * imageWidth;
					mapY = (worldY >> tileSize) * tileMap.width;

					for (worldX = startX; worldX < endX; worldX++)

						pixels[worldX - xOffset + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

					rowsToDraw--;

				}

			}

		};

		final AsyncTask t2 = new AsyncTask () {

			@Override
			public void run(){

				int pixelsY, mapY, worldX, worldY;

				for (worldY = endY - 1; worldY >= startY && rowsToDraw > 0; worldY--) {

					pixelsY = (worldY - yOffset) * imageWidth;
					mapY = (worldY >> tileSize) * tileMap.width;

					for (worldX = startX; worldX < endX; worldX++)

						pixels[worldX - xOffset + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

					rowsToDraw--;

				}

			}

		};

		t1.await();
		t2.await();

	}

	void renderTilemap3(final Bitmap tileMap) {

		final int startX = xOffset < 0 ? 0 : xOffset;
		final int endX = xOffset + imageWidth > tileMap.width<<tileSize ?  tileMap.width<<tileSize :imageWidth + xOffset;
		final int startY = yOffset < 0 ? 0 : yOffset;
		final int endY = yOffset + imageHeight > tileMap.height<<tileSize ?  tileMap.height<<tileSize : imageHeight + yOffset;

		final AsyncTask a = new AsyncTask(startY, endY) {

			@Override
			public void run() {

				final int pixelsY = (getIteration() - yOffset) * imageWidth;
				final int mapY = (getIteration() >> tileSize) * tileMap.width;

				for (int worldX = startX; worldX < endX; worldX++) {

					pixels[worldX - xOffset + pixelsY] = tileMap.pixels[(worldX >> tileSize) + mapY];

				}

			}

		};

		a.await();

	}

	// Uses drawRaster to cull and render a buffered image with its own width and height
	public void drawBufferedImage(final BufferedImage i, final int x, final int y) {

		drawRaster(((DataBufferInt) i.getRaster().getDataBuffer()).getData(), i.getWidth(), i.getHeight(), x, y);

	}

	// Uses drawRaster to cull and render a buffered image scaled to a custom width and height
	public void drawBufferedImage(final BufferedImage i, final int w, final int h, final int x, final int y) {

		drawRaster(scaleRaster(((DataBufferInt) i.getRaster().getDataBuffer()).getData(), i.getWidth(),i.getHeight(), w, h), 2, h, x, y);

	}

	// Uses drawRaster to cull and render a bitmap with its own width and height
	public void drawBitmap(final Bitmap bitmap, final int x, final int y) {

		drawRaster(bitmap.pixels, bitmap.width, bitmap.height, x, y);

	}

	// Uses drawRaster to cull and render a bitmap scaled to a custom width and height
	public void drawBitmap(final Bitmap bitmap, final int w, final int h, final int x, final int y) {

		drawRaster(scaleRaster(bitmap.pixels, bitmap.width, bitmap.height, w, h), w, h, x, y);

	}

	// Culls and renders a raster onto the view sport
	public void drawRaster(final int[] p, final int w, final int h, final int x, final int y) {
		
		// Finds the bounds (in world space) of the pixels that are visible and are on the tile map
		final int startX = x < 0 ? 0 : x;
		final int endX = x + w > imageWidth ? imageWidth : w + x;
		final int startY = y < 0 ? 0 : y;
		final int endY = y + h > imageHeight ? imageHeight : h + y;

		final int deltaX = endX - startX;
		
		int offset, row, t;
		
		offset = startX + startY * imageWidth;
		t = startY * w;

		// For each visible row in world space defined by the bounds above
		for (row = startY; row < endY; row++) {
			
			// Copy the row from p to the corresponding row in pixels
			System.arraycopy(p, t, pixels, offset, deltaX);
			
			// Calculate the position of the first pixel of the row on the view port
			offset += imageWidth;
			
			t += w;

		}

	}

	// Optimized version of scale()
	public static int[] scaleRaster(final int[] pixels, final int w1, final int h1, final int w2, final int h2) {
				
		final int[] result = new int[w2 * h2];

		int x2, y2, t;
		
		float x1, y1;
		float rx = (float) w1 / w2;
		float ry = (float) h1 / h2;
				
		t = 0;
		
		y1 = 0;
		
		for (y2 = 0; y2 < h2; y2++) {
			
			x1 = 0;

			for (x2 = 0; x2 < w2; x2++) {
				
				result[t] = pixels[(int) x1 + (int) y1 * w1];

				x1 += rx;
				
				t++;
				
			}
			
			y1 += ry;
			
		}
				
		return result;

	}
	
	// Method incomplete and not commented
	public static int[] scaleRasterBilinear(final int[] pixels, final int w1, final int h1, final int w2, final int h2) {
		
		int[] result = new int[w2 * h2];
		
		int x, y;
		int x1, y1, x2, y2;
		int deltaX, deltaY, dx1, dy1, dx2, dy2;
		int dividend;
		
		int r11, r21, r12, r22;
		int g11, g21, g12, g22;
		int b11, b21, b12, b22;
		
		for (y = 0; y < h2; y++) {
			
			y1 = y * (h1 - 1) / h2;
			y2 = y1 + 1;
			
			deltaY = y2 - y1;
			
			for (x = 0; x < w2; x++) {
				
				x1 = x * (w1 - 1) / w2;
				x2 = x1 + 1;
				
				deltaX = x2 - x1;
				
				dividend = deltaX * deltaY;
				
				dx1 = x - x1;
				dy1 = y - y1;
				dx2 = x2 - x;
				dy2 = y2 - y;
				
			}
						
		}
		
		return result;
		
	}

}
