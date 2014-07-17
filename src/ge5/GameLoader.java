package ge5;

import java.io.InputStream;
import java.util.ArrayList;

public class GameLoader{
	
	static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	
	static Game game;
	static ArrayList<Tile> tiles = new ArrayList<Tile>();
	static ArrayList<Scene> scenes = new ArrayList<Scene>();
	static ArrayList<Entity> entities = new ArrayList<Entity>();

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		new GameLoader(args);
				
	}
	
	GameLoader(String[] classNames) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
				
		for(String s:classNames){
			
			Class<?> c = classLoader.loadClass(s);
				
			if(Scene.class.isAssignableFrom(c))
				
				scenes.add((Scene) c.newInstance());
			
			if(Entity.class.isAssignableFrom(c))
				
				entities.add((Entity) c.newInstance());
			
			if(Tile.class.isAssignableFrom(c))
				
				tiles.add((Tile) c.newInstance());
			
			if(Game.class.isAssignableFrom(c))
				
				game = (Game) c.newInstance();				
			
		}
		
		game.startGame();
				
	}
	
	// Can be used to load images, sound, maps ... from a file
	public static InputStream getFileData(String path) {
		
		return classLoader.getResourceAsStream(path);
		
	}

}