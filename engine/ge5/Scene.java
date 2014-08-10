package ge5;

import java.util.Hashtable;

public abstract class Scene implements tickable {

	private static Hashtable<String, Scene> sceneList = new Hashtable<String, Scene>();
	public static Scene loadedScene = null;
	public static String loadedSceneKey = null;

	Tile[] tileMap;

	public Scene () {

		sceneList.putIfAbsent(getClass().getName(), this);

	}

	protected void load() {

	}

	protected void unload() {

		tileMap = null;

	}

	public static void loadScene(final String sceneKey) {

		if(sceneList.get(sceneKey) != null) {

			if(loadedScene != null){
				loadedScene.unload();
				System.out.println("Unloaded " + loadedSceneKey + "");
			}

			loadedScene = sceneList.get(sceneKey);
			loadedScene.load();
			System.out.println("Loaded " + sceneKey + "");
			loadedScene.start();

			loadedSceneKey = sceneKey;

		} else {

			System.out.println("Failed to load " + sceneKey + "");

		}

	}

	protected Scene getLoadedScene () {
		return loadedScene;
	}

	protected String getLoadedSceneKey () {
		return loadedSceneKey;
	}

	@Override
	public String toString () {
		return getClass().getName();
	}

}