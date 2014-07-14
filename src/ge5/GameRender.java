package ge5;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

class GameRender extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
    private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	private int[] pixels;
	
	private int xOffset = 0;
	private int yOffset = 0;

	GameRender(int width, int height) {
		
		setGameSize(width, height);
		setIgnoreRepaint(true);
	
	}
	
	void setGameSize(int width, int height){

		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		image = config.createCompatibleImage(width + 64, height + 64, Transparency.OPAQUE);
		
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		setSize(width, height);	
				
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
			
		for(int x = xOffset/32 + 1; x < (xOffset + getWidth())/32; x ++){
			for(int y = yOffset/32 + 1; y < (yOffset + getHeight())/32; y ++){
				
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
