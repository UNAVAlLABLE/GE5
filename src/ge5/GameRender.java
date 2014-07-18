
package ge5;

import java.awt.Canvas;
import java.awt.Graphics;
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
	private Graphics graphics;
	private GraphicsConfiguration config;
	private int[] pixels;

//	private int posX = 0;
//	private int posY = 0;

	GameRender(int width, int height) {

		config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		resizeImage(width, height);
		setSize(width, height);
		setIgnoreRepaint(true);

	}
	
	void resizeImage(int width, int height) {

		image = config.createCompatibleImage(width + 64, height + 64, Transparency.OPAQUE);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
	}

	void render(Scene scene) {

		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
				
		drawTiles(scene);
		
		graphics.drawImage(image, -32, -32, image.getWidth(), image.getHeight(), null);

		bufferStrategy.show();
		graphics.dispose();

	}

	void drawTiles(Scene scene) {

//		int xOffset = posX % 32;
//		int yOffset = posY % 32;
//
//		for (int x = posX; x < (posX + (image.getWidth())) / 32; x++) {
//			for (int y = posY; y < (posY + (image.getHeight())) / 32; y++) {
//
//			}
//		}

	}

	void renderBitmap(Bitmap bitmap, int x, int y) {

		int startX = (x < 0) ? 0 : x;

		int startY = (y < 0) ? 0 : y;

		int endX = (x + bitmap.width > image.getWidth()) ? image.getWidth() : bitmap.width + x;

		int endY = (y + bitmap.height > image.getHeight()) ? image.getHeight() : bitmap.height + y;

		for (int yy = startY; yy < endY; yy++) {
			
			int tp = yy * image.getWidth() + startX;
			
			int sp = (yy - y) * bitmap.width + (startX - x);
			
			tp -= sp;
			
			for (int xx = sp; xx < sp + (endX - startX); xx++) {
				
				int col = bitmap.pixels[xx];
				pixels[tp + xx] = col;
				
			}
			
		}

	}

	public int[] scale(int[] pixels, int w1, int h1, int w2, int h2) {

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
