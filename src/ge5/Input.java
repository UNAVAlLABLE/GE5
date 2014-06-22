// This class is to control input for all input devices.
// Keys should be updated before each fixed tick and accessible
// directly from the game classes

package ge5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	
	Game game;
	
	Input(Game game) {
	
		this.game = game;
	
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}