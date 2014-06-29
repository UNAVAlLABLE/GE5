package ge5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

class GameRender extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
    private BufferedImage image;
	private BufferStrategy bufferStrategy;
	private Graphics2D g;
	private MapManager map;
	
	public int width;
	public int height;

	GameRender(int width, int height, MapManager map) {
		
		this.width = width;	
		this.height = height;
		this.map = map;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		g = image.createGraphics();
		
		setSize(width, height);
		setIgnoreRepaint(true);
		requestFocus();
	
	}
	
	void render() {
		
		bufferStrategy = getBufferStrategy();
		g = (Graphics2D) image.getGraphics();
		
		//// Image Edited Here //////////////////
		/////////////////////////////////////////
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
		map.render(g);
		
		/////////////////////////////////////////
		/////////////////////////////////////////
		
		g.dispose();
		
		g = (Graphics2D) getBufferStrategy().getDrawGraphics();
		g.drawImage(image, 0, 0, width , height, null);

		g.dispose();
		bufferStrategy.show();

	}

}
