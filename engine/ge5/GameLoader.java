// TODO Create a method to read a variable number of configurations from a file and load them here
// to be stored in a configuration hash-table

// TODO Create a method to find all names of all classes from all jars, packages and sub-packages in a directory outside the build path

package ge5;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;

public class GameLoader {

	static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	static Game game;
	static Hashtable<String, Object> classes = new Hashtable<String, Object>();

	public static void main(String[] args) throws Exception {

		new GameLoader(args);

	}

	GameLoader(String[] classNames) throws Exception{

		Arrays.sort(classNames);

		for(final String s:classNames){

			final Class<?> c = classLoader.loadClass(s);

			if(Game.class.isAssignableFrom(c) && game == null){

				game = (Game) c.newInstance();
				System.out.println("Found main game class " + s);
				continue;

			}

			try{

				classes.put(s, c.newInstance());
				System.out.println("Found and initialized " + s);

			}catch(final Exception e){

				System.out.println("Found but could not initialize " + s);

			}

		}

		if(game == null){

			System.out.print("Lacking game class.\nAborting.");
			System.exit(1);

		}

		System.out.println("Starting Game");
		game.startGame();

	}

	// Can be used to load images, sound, maps ... from a file
	public static InputStream getFileData(String path) {

		// TODO Currently has problems reaching absolute system paths and relatively parent directories. Needs fixing / replacing
		return classLoader.getResourceAsStream(path);

	}

}