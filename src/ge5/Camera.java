package ge5;

import java.awt.geom.AffineTransform;

public class Camera {
	
	private Vector position;
	private float height;
	private float invHeight;
	
	private int halfScreenWidth;
	private int halfScreenHeight;
		
	protected AffineTransform transform;
	
	private final float sqrt2 = (float) Math.sqrt(2);
	
	protected Camera(Vector position, int screenWidth, int screenHeight) {
		
		this.position = position;
		
		halfScreenWidth = screenWidth / 2;
		halfScreenHeight = screenHeight / 2;
		
		height = invHeight = 1.0f;
						
		calculateTransform();
		
	}
	
	private void calculateTransform() {
				
		transform = new AffineTransform();
		transform.scale(height, height);
		transform.translate(invHeight * halfScreenWidth + position.x, invHeight * halfScreenHeight + position.y);
		
	}
	
	public void translate(int x, int y) {
		
		position.add(x, y);
		calculateTransform();
		
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = (float) Math.pow(sqrt2, height - 1);
		invHeight = 1.0f / this.height;
		calculateTransform();
	}
	
	public void addHeight(float amount) {
		height *= (float) Math.pow(sqrt2, amount);
		invHeight = 1.0f / height;
		calculateTransform();
	}
	
	public void subHeight(float amount) {
		height /= (float) Math.pow(sqrt2, amount);
		invHeight = 1.0f / height;
		calculateTransform();
	}
	
}