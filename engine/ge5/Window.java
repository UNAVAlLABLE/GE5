package ge5;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;

class Window extends Frame {
	
	private static final long serialVersionUID = 1L;
	
	static GameRender gameRender;
			    
    Window(String title, int width, int height) {
    	
    	setTitle(title);
    	setWindowIcon("icon.png");
    	
		addKeyListener(new Input());
		
		gameRender = new GameRender(width, height);
		add(gameRender);
		
		pack();
		center();
		requestFocus();		
		setIgnoreRepaint(true);
		setResizable(false);
		setVisible(true);
		
		// Has to be called after setVisible
		gameRender.createBufferStrategy(2);
		
		// Ends the program when the 'x' is pressed on the window
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
	
	void center() {
		
		setLocationRelativeTo(null);
		
	}

}