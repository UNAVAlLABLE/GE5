package ge5;

public class Camera {
	
	public Vector position;
	
	protected int screenWidth;
	protected int screenHeight;
	
	protected int halfScreenWidth;
	protected int halfScreenHeight;
	
	protected float ratio;
	
	private float height;
	
	private MapManager map;
	
	protected Camera(Vector position, float height, int screenWidth, int screenHeight) {
		
		this.position = position;
		this.height = height;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		halfScreenWidth = screenWidth / 2;
		halfScreenHeight = screenHeight / 2;
		
		ratio = (float) screenWidth / screenHeight;
		
	}
	
	protected void setMap(MapManager map) {
		this.map = map;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		if (this.height != height) {
			this.height = height;
			map.setTileDimensions();
		}
	}
	
	public void addHeight(float amount) {
		if (amount != 0) {
			height += amount;
			map.setTileDimensions();
		}
	}
	
	public void subHeight(float amount) {
		if (amount != 0) {
			height -= amount;
			map.setTileDimensions();
		}
	}
	
}