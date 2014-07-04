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
	private Graphics2D g1;
	private Graphics2D g2;
	private MapManager map;
	
	public int width;
	public int height;

	protected GameRender(int width, int height, MapManager map) {
		
		this.width = width;	
		this.height = height;
		this.map = map;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		g1 = image.createGraphics();
		
		setSize(width, height);
		setIgnoreRepaint(true);
		requestFocus();
	
	}
	
	protected void initBufferStrategy() {
		
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
		
	}
	
	protected void render() {
				
		//// Image Edited Here //////////////////
		/////////////////////////////////////////
		
		g1.setColor(Color.RED);
		g1.fillRect(0, 0, width, height);
		map.render(g1);
		
		/////////////////////////////////////////
		/////////////////////////////////////////
		
		g2.drawImage(image, 0, 0, width , height, null);

		bufferStrategy.show();

	}

}
