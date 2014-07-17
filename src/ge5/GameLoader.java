package ge5;

import java.io.InputStream;
import java.util.ArrayList;

public class GameLoader{
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		new GameLoader(args);
				
	}
	
	static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	
	static Game game;
	
	// TODO Change these into a hashtables for better identification
	static ArrayList<Tile> tiles = new ArrayList<Tile>();
	static ArrayList<Scene> scenes = new ArrayList<Scene>();
	static ArrayList<Entity> entities = new ArrayList<Entity>();
	
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
	// TODO Currently has problems reaching absolute system paths and parent directories
	public static InputStream getFileData(String path) {
		
		return classLoader.getResourceAsStream(path);
		
	}

}