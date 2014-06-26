package ge5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GUI {

	protected BufferedImage image;
	
	private int width;
	private int height;
	private Graphics2D g;
	
	//Just so it can't be initialized outside package
	protected GUI() {}
	
	public void drawString(String string, int x, int y) {
		g.clearRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		g.drawString(string, x, y);
	}
	
	protected void onScreenChanged(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) image.getGraphics();
		this.width = width;
		this.height = height;
	}
	
}