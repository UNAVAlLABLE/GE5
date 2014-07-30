package ge5.engine;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;

public class GameLoader{
	
	static ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	
	static Game game;
	static Hashtable<String, Scene> scenes = new Hashtable<String, Scene>();
	static Hashtable<String, Tile> tiles = new Hashtable<String, Tile>();
	static Hashtable<String, Entity> entities = new Hashtable<String, Entity>();
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		new GameLoader(args);
				
	}
	
	GameLoader(String[] classNames) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		Arrays.sort(classNames);
		
		for(String s:classNames){
			
			Class<?> c = classLoader.loadClass(s);
				
			if(Scene.class.isAssignableFrom(c)){
				
				scenes.put(s, (Scene) c.newInstance());
				System.out.println("Found scene class " + s);
				continue;
				
			}
						
			if(Entity.class.isAssignableFrom(c)){
				
				entities.put(s, (Entity) c.newInstance());
				System.out.println("Found entity class " + s);
				continue;
				
			}
			
			if(Tile.class.isAssignableFrom(c)){
				
				tiles.put(s, (Tile) c.newInstance());
				System.out.println("Found tile class " + s);
				continue;
				
			}
			
			if(Game.class.isAssignableFrom(c)){
				
				game = (Game) c.newInstance();
				System.out.println("Found game class  " + s);
				
			}
			
		}
		
		if(game == null || scenes.isEmpty()){
			
			System.out.print("Lacking game or scene class.\nAborting.");
			System.exit(1);
			
		}
		
		new Thread(game).start();
						
	}
	
	// Can be used to load images, sound, maps ... from a file
	// TODO Currently has problems reaching absolute system paths and relatively parent directories
	public static InputStream getFileData(String path) {
		
		return classLoader.getResourceAsStream(path);
		
	}

}