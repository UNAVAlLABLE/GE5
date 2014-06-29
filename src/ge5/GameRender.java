package ge5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

class GameRender extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
    BufferedImage image;
	BufferStrategy bufferStrategy;
	Graphics2D g;
	
	int width, height;

	GameRender(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		
		setSize(width, height);
		setIgnoreRepaint(true);
		requestFocus();
	
	}
	
	void render() {
		
		bufferStrategy = getBufferStrategy();
		
		//// Image Edited Here //////////////////
		/////////////////////////////////////////
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
		
		/////////////////////////////////////////
		
		g = (Graphics2D) getBufferStrategy().getDrawGraphics();
		g.drawImage(image, 0, 0, width , height, null);

		g.dispose();
		bufferStrategy.show();

	}

}
