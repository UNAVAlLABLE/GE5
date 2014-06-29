package ge5;

import java.awt.Graphics2D;

public class Entity {
	
	static int IDCounter = 0;
	
	public int ID;
	public Vector pos;
	
	protected Entity(){
		
		ID = IDCounter++;
			
	}
	
	protected void render(Graphics2D g){
		
	}

}
