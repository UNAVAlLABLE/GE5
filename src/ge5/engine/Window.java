package ge5.engine;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;

class Window extends Frame {
	
	private static final long serialVersionUID = 1L;
	
	static GameRender gameRender;
	static Input input;
			    
    Window(String title, int width, int height) {
    	
    	setTitle(title);
    	
		setIgnoreRepaint(true);
		setResizable(false);
		
		input = new Input();
		
		addKeyListener(input);
		addMouseWheelListener(input);
		
		setWindowIcon("icon.png");
		
		gameRender = new GameRender(width, height);
		add(gameRender);
		
		pack();
		center();
		setVisible(true);
		requestFocus();
		
		// Has to be called after setVisible
		gameRender.createBufferStrategy(2);
		
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
	
	void center() {
		
		setLocationRelativeTo(null);
		
	}

}