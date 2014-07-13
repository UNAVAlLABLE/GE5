package ge5;

public abstract class Entity {
	
	static int IDCounter = 0;
	
	public int ID;
	
	public Entity() {
		
		ID = IDCounter++;
			
	}

}
