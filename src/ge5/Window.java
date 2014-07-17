package ge5;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

class Window extends Frame {
	
	private static final long serialVersionUID = 1L;
	
	private GameRender render;
			    
    Window(Game game, Input input, String title, int width, int height) {
    	
    	setTitle(title);
    	
		setIgnoreRepaint(true);
		setResizable(false);
		
		addKeyListener(input);
		addMouseWheelListener(input);
		
		setWindowIcon("icon.bmp");
		
		render = new GameRender(width, height);
		add(render);
		
		pack();
		center();
		setVisible(true);
		requestFocus();
		
		// Has to be called after setVisible
		render.createBufferStrategy(2);
		
		//Ends the program when the 'x' is pressed on the window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				
				System.exit(0);
				
			}
		});

	}
    
    void setWindowIcon(String path){
    	
		try {
				
			setIconImage(ImageIO.read(GameLoader.getFileData(path)));
				
		} catch (Exception e) {
			
			System.out.println("Failed to load " + path + " as frame icon: " + e.getMessage());
				
		}

    	
    }
    
    void renderGame(Scene scene) {

    	render.render(scene);
    
    }
	
	void center() {
		
		setLocationRelativeTo(null);
		
	}

}