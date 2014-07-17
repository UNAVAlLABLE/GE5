package ge5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class Input implements KeyListener, MouseWheelListener {
		
	private int scrollWheelAxis = 0;
	
	public static boolean up, down, left, right, space;
		
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

		if (keyCode == e.VK_UP || keyCode == e.VK_W)
			up = true;

		if (keyCode == e.VK_DOWN || keyCode == e.VK_S)
			down = true;

		if (keyCode == e.VK_LEFT || keyCode == e.VK_A)
			left = true;

		if (keyCode == e.VK_RIGHT || keyCode == e.VK_D)
			right = true;

		if (keyCode == e.VK_SPACE)
			space = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode();

		if (keyCode == e.VK_UP || keyCode == e.VK_W)
			up = false;

		if (keyCode == e.VK_DOWN || keyCode == e.VK_S)
			down = false;

		if (keyCode == e.VK_LEFT || keyCode == e.VK_A)
			left = false;

		if (keyCode == e.VK_RIGHT || keyCode == e.VK_D)
			right = false;

		if (keyCode == e.VK_SPACE)
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