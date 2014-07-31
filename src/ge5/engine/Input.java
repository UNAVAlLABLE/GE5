package ge5.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseWheelListener {
			
	public static boolean up, down, left, right, space, e, q;
		
	protected Input() {}

	@Override
	public void keyPressed(KeyEvent event) {
		
		int keyCode = event.getKeyCode();

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
		
		if (keyCode == KeyEvent.VK_E)
			e = true;

		if (keyCode == KeyEvent.VK_Q)
			q = true;

	}

	@Override
	public void keyReleased(KeyEvent event) {
		
		int keyCode = event.getKeyCode();

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
			up = false;

		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
			down = false;

		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
			left = false;

		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
			right = false;
		
		if (keyCode == KeyEvent.VK_E)
			e = false;

		if (keyCode == KeyEvent.VK_Q)
			q = false;

		if (keyCode == KeyEvent.VK_SPACE)
			space = false;

	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
	}

}