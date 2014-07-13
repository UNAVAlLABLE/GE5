package ge5;

public abstract class Scene {
	
	Byte[] tileMap;
	
	void load(){
		
	}
	
	void unload(){
		
	}
	
	protected abstract void tick(int skips);
	
}