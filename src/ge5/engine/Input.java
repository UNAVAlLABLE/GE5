package ge5.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseWheelListener {
		
	private int scrollWheelAxis = 0;
	
	public boolean up, down, left, right, space;
		
	protected Input() {}
	
	protected void clear() {
		scrollWheelAxis = 0;
	}
	
	public int getMouseWheelAxis() {
		return scrollWheelAxis;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
			up = true;

		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
			down = true;

		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
			left = true;

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
			right = true;

		if (keyCode == KeyEvent.VK_SPACE)
			space = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
			up = false;

		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
			down = false;

		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
			left = false;

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
			right = false;

		if (keyCode == KeyEvent.VK_SPACE)
			space = false;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollWheelAxis -= e.getWheelRotation();
	}

}