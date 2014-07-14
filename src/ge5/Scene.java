package ge5;

public abstract class Scene {
		
	protected void load() {

	}
	
	protected void unload() {

	}
	
	protected abstract void start();
	
	protected abstract void tick(int skips);
	
}