package ge5;

public abstract class Scene implements tickable {
			
	protected void load() {

	}
	
	protected void unload() {

	}
	
	public String toString () {
		
		return this.getClass().getName();
		
	}
	
}