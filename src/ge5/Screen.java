// Used to initialize and control the Frame and Canvas
// manages resolution, fullscreen and window resizes

package ge5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Screen {
	
	private int width;

	private int height;
	
	protected int scale = 1;
		
	private Render renderer;
	
	private Canvas canvas;
	
	private Graphics2D g;
	
	private Frame frame;
	
	private BufferStrategy bufferStrategy;
	    
    public Screen(Game game, GUI gui, String title) {
		this(game, gui, title, 1280, 720, 1);
	}
    
	Screen(Game game, GUI gui, String title, int width, int height, int scale) {
		
		renderer = new Render(gui);
				
		canvas = new Canvas(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
		canvas.setIgnoreRepaint(true);
		canvas.addKeyListener(game.input);
				
		frame = new Frame();
		frame.setTitle(title);
		frame.setIgnoreRepaint(true);
		frame.setBackground(Color.black);
		
		frame.add(canvas);
								
		frame.setResizable(false);

		// What happens when the x button is pressed
		frame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				System.exit(0);
				
			}
			
		});
		
		onScreenChanged(width, height);
				
		// Centers the window
		center();
		frame.setVisible(true);
		
		frame.requestFocus();
		canvas.requestFocus();

	}

	public void update() {
		renderer.render(g);
		bufferStrategy.show();
	}
	
	public void center() {
		frame.setLocationRelativeTo(null);
	}
	
	public void setWidth(int width) {
		this.width = width;
		onScreenChanged(width, height);
	}
	
	public void setHeight(int height) {
		this.height = height;
		onScreenChanged(width, height);
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		onScreenChanged(width, height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void onScreenChanged(int width, int height) {
		canvas.setSize(width * scale, height * scale);
		frame.pack();
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		g = (Graphics2D) bufferStrategy.getDrawGraphics();
		renderer.onScreenChanged(width, height);
	}

}