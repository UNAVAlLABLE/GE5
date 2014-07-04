package ge5;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Frame {
	
	private static final long serialVersionUID = 1L;
	
	private GameRender render;
			    
    Window(Game game, Input input, String title, int width, int height, MapManager map) {
    	
    	setTitle(title);
		setIgnoreRepaint(true);
		setResizable(false);
		addKeyListener(input);
		addMouseWheelListener(input);
		setBackground(Color.black);	
		
		render = new GameRender(width, height, map);
		add(render);
		
		pack();
		center();
		setVisible(true);
		
		// Has to be called after setVisible
		render.initBufferStrategy();
		
		requestFocus();
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				System.exit(0);
				
			}
			
		});

	}
    
    void renderGame(){

    	render.render();
    
    }
	
	void center() {
		
		setLocationRelativeTo(null);
		
	}

}