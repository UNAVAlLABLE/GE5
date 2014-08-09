// TODO Create a method to read a variable number of configurations from a file and load them here
// to be stored in a configuration hash-table

// TODO Create a method to find all names of all classes from all jars, packages and sub-packages in a directory

package ge5;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;

public class GameLoader {

	static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	static Game game;

	static Hashtable<String, Scene> scenes = new Hashtable<String, Scene>();
	static Hashtable<String, Class<?>> classes = new Hashtable<String, Class<?>>();


	public static void main(String[] args) throws Exception {

		new GameLoader(args);

	}

	GameLoader(String[] classNames) throws Exception{

		Arrays.sort(classNames);

		for(final String s:classNames){

			final Class<?> c = classLoader.loadClass(s);

			if(Scene.class.isAssignableFrom(c)){

				scenes.put(s, (Scene) c.newInstance());
				System.out.println("Found scene " + s);
				continue;

			}

			if(Game.class.isAssignableFrom(c)){

				game = (Game) c.newInstance();
				System.out.println("Found game  " + s);
				continue;

			}

			classes.put(s, (Class<?>) c.newInstance());
			System.out.println("Found unspecified class " + s);

		}

		if(game == null || scenes.isEmpty()){

			System.out.print("Lacking game or scene class.\nAborting.");
			System.exit(1);

		}

		game.startGame();

	}

	// Can be used to load images, sound, maps ... from a file
	public static InputStream getFileData(String path) {

		// TODO Currently has problems reaching absolute system paths and relatively parent directories. Needs fixing / replacing
		return classLoader.getResourceAsStream(path);

	}

}