// Controls the final rendering
// It loads map data, views what's visible to the camera and decides what tiles IDs 
// and entities to render.It then renders them based on their spreadsheets.

// Hardware acceleration and double buffering done here

package ge5;

import java.awt.Graphics2D;

public class Render {
	
	private GUI gui;
	private int width;
	private int height;
		
	protected Render(GUI gui) {
		this.gui = gui;
	}
	
	protected void render(Graphics2D g) {
		g.clearRect(0, 0, width, height);
		g.drawImage(gui.image, null, 0, 0);
	}
	
	protected void onScreenChanged(int width, int height) {
		this.width = width;
		this.height = height;
		gui.onScreenChanged(width, height);
	}

}
