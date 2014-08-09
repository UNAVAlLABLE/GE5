package ge5;

public abstract class Scene implements tickable {

	// 2D to allow real time changing of tiles
	Tile[] tileMap;

	protected void load() {

	}

	protected void unload() {

		tileMap = null;

	}

	@Override
	public String toString () {

		return this.getClass().getName();

	}

}