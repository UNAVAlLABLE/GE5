// This class is to control input for all input devices.
// Keys should be updated before each fixed tick and accessible
// directly from the game classes

package ge5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseWheelListener {
		
	private int scrollWheelAxis = 0;
		
	protected Input() {}
	
	protected void clear() {
		scrollWheelAxis = 0;
	}
	
	public int getMouseWheelAxis() {
		return scrollWheelAxis;
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollWheelAxis -= e.getWheelRotation();
	}

}