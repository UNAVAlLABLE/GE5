package ge5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseWheelListener {
			
	public static boolean up, down, left, right, space, shift, e, q, f, r;
	
		
	protected Input() {
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		
		int keyCode = event.getKeyCode();
		
		switch(keyCode){
			
			case KeyEvent.VK_UP: 
			case KeyEvent.VK_W:
			
				up = true;
				break;
				
			case KeyEvent.VK_LEFT: 
			case KeyEvent.VK_A:
			
				left = true;
				break;
				
			case KeyEvent.VK_DOWN: 
			case KeyEvent.VK_S:
			
				down = true;
				break;
				
			case KeyEvent.VK_RIGHT: 
			case KeyEvent.VK_D:
			
				right = true;
				break;
				
			case KeyEvent.VK_SPACE:
				
				space = true;
				break;
				
			case KeyEvent.VK_SHIFT:
				
				shift = true;
				break;
				
			case KeyEvent.VK_E:
				
				e = true;
				break;
				
			case KeyEvent.VK_Q:
				
				q = true;
				break;
				
			case KeyEvent.VK_F:
				
				f = true;
				break;
				
			case KeyEvent.VK_R:
				
				r = true;
				break;
			
		}
			
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
		int keyCode = event.getKeyCode();

		switch(keyCode){
			
			case KeyEvent.VK_UP: 
			case KeyEvent.VK_W:
			
				up = false;
				break;
				
			case KeyEvent.VK_LEFT: 
			case KeyEvent.VK_A:
			
				left = false;
				break;
				
			case KeyEvent.VK_DOWN: 
			case KeyEvent.VK_S:
			
				down = false;
				break;
				
			case KeyEvent.VK_RIGHT: 
			case KeyEvent.VK_D:
			
				right = false;
				break;
				
			case KeyEvent.VK_SPACE:
				
				space = false;
				break;
				
			case KeyEvent.VK_SHIFT:
				
				shift = false;
				break;
				
			case KeyEvent.VK_E:
				
				e = false;
				break;
				
			case KeyEvent.VK_Q:
				
				q = false;
				break;
				
			case KeyEvent.VK_F:
				
				f = false;
				break;
				
			case KeyEvent.VK_R:
				
				r = false;
				break;
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		
	}

}