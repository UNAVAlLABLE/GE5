package ge5;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Frame{
	
	private static final long serialVersionUID = 1L;
	
	private GameRender render;
			    
    Window(Game game, Input input, String title, int width, int height) {
    	
    	setTitle(title);
		setIgnoreRepaint(true);
		setResizable(false);
		addKeyListener(input);
		setBackground(Color.black);	
		
		render = new GameRender(width, height);
		add(render);
		
		pack();
		center();
		setVisible(true);
		
		// Has to be called after setVisible
		render.createBufferStrategy(2);
		
		requestFocus();
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				System.exit(0);
				
			}
			
		});

	}
    
    void renderGame(){
    	
    	System.out.println("Render");
    	
    	render.render();
    	
    }
	
	void center() {
		
		setLocationRelativeTo(null);
		
	}

}