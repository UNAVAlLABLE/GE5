package ge5;

public class Camera {
	
	public Vector position;
	public float height;
	
	public float ratio;
	
	protected Camera(Vector position, float height, float aspectRatio) {
		
		this.position = position;
		this.height = height;
		this.ratio = aspectRatio;
		
	}
	
}