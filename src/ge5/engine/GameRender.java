
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
	private int[] pixels;
	
	Bitmap tileMap;

	public int xOffset = -21;
	public int yOffset = 62;

	GameRender(int width, int height) {

		config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		resizeImage(width, height);
		setFocusable(false);
		setSize(width, height);
		setIgnoreRepaint(true);

	}
	
	void resizeImage(int width, int height) {

		image = config.createCompatibleImage(width, height, Transparency.OPAQUE);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}

	void render(Scene scene) {

		bufferStrategy = getBufferStrategy();
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		
		if(Input.up)yOffset++;
		if(Input.down)yOffset--;
		if(Input.left)xOffset++;
		if(Input.right)xOffset--;
						
		drawTiles(new Bitmap(new int[16], 4, 4));
		
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		bufferStrategy.show();
		graphics.dispose();

	}

	void drawTiles(Bitmap tileMap) {
		
		// Finds the bounds of the rectangle intersection between the viewport and the tilemap
		int startX = (xOffset < 0) ? 0 : xOffset;
		int endX = xOffset + image.getWidth() > tileMap.width * 32 ? tileMap.width * 32: xOffset + image.getWidth();		
		int startY = (yOffset < 0) ? 0 : yOffset;
		int endY = yOffset + image.getHeight() > tileMap.height * 32 ? tileMap.height * 32: yOffset + image.getHeight();
		
		// Finds the new rectangles offset on the view port
		int baseOffset = ((startX - xOffset) + (startY - yOffset) * image.getWidth()) - (xOffset + yOffset * image.getWidth());
				
		for (int row = startY; row < endY; row++) {
			
			for (int column = startX; column < endX; column++) {
				
				if(row % 32 == 0 || column % 32 == 0)
					
					pixels[baseOffset + column + (row * image.getWidth())] = 0xff000000;
				
				else
					
					pixels[baseOffset + column + (row * image.getWidth())] = 0xffcccccc;
				
			}

		}
			
	}

	void renderBitmap(Bitmap bitmap, int x, int y) {

		renderBitmap(bitmap.pixels, bitmap.width, bitmap.height, x, y);

	}
	
	void renderBitmap(int[] p, int w, int h, int x, int y) {

		int startX = (x < 0) ? 0 : x;
		int endX = (x + w > image.getWidth()) ? image.getWidth() : w + x;

		int startY = (y < 0) ? 0 : y;
		int endY = (y + h > image.getHeight()) ? image.getHeight() : h + y;
		
		int sp;
		int tp;

		for (int row = startY; row < endY; row++) {
			
			sp = (row - y) * w + (startX - x);
			
			tp = (row * image.getWidth() + startX) - sp;
			
			for (int column = sp; column < sp + (endX - startX); column++) {
				
				pixels[tp + column] = p[column];
				
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
