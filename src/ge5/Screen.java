// Used to initialize and control the Frame and Canvas
// manages resolution, fullscreen and window resizes

package ge5;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

class Screen {
	
	Game game;
	
	Canvas canvas;
	
	Frame frame;
	
	BufferStrategy bufferStrategy;
	
    BufferedImage image;
    
	Screen(Game game, String title, int width, int height, int scale) 
	{
				
		this.game = game;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
		canvas.setSize(width * scale, height * scale);
		canvas.setIgnoreRepaint(true);
		canvas.addKeyListener(game.input);	
		
		frame = new Frame();
		frame.setTitle(title);
		frame.setIgnoreRepaint(true);
		frame.setBackground(Color.black);
		
		frame.add(canvas);
		
		// Resize the frame around the canvas
		frame.pack();
		
		frame.setResizable(false);

		// What happens when the x button is pressed
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent we) {
				
				System.exit(0);
				
			}
			
		});
		
		canvas.createBufferStrategy(2);
		
		// Centers the window
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.requestFocus();
		canvas.requestFocus();

	}

}
