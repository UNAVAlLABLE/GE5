package ge5;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

class GameRender extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
    private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	int[] pixels;
	
	int xOffset = 0;
	int yOffset = 0;

	GameRender(int width, int height) {
		
		setGameSize(width,height);
		setIgnoreRepaint(true);
	
	}
	
	void setGameSize(int width, int height){

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		setSize(width, height);	
				
	}
	
	void render() {
		
		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
		
		drawTiles();
		
		graphics.drawImage(image, 0, 0, getWidth() , getHeight(), null);
		bufferStrategy.show();
		graphics.dispose();
	}
	
	void drawTiles() {
		
		int width = image.getWidth();
		int height = image.getHeight();
			
		for(int x = xOffset & -32; x < width; x += 32){
			for(int y = yOffset & -32; y < height; y += 32){
				
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
