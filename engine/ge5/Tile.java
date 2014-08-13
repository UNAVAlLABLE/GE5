// Unlike other tickable classes, Tiles are not to tick every frame but instead only tick when directly called.

// This class is to be extended for more advanced tile types like animated tiles or transparent tiles.

// All images stored in this tile will be in the form of integer arrays and must always be static.

// Should contain methods to load and unload the tile images and other resources in and out of the memory
// as we are manually keeping this class in scope.

package ge5;

public abstract class Tile implements tickable {

	static int tileSize;
	static int tileStates;

	// When this tile is loaded this should contain all images of this tile type
	static int[][] imageSet = new int[tileStates][tileSize];

	// References which image in the imageSet that would be displayed during rendering
	int tileState = 0;

	void load(){

	}

	void unload(){

		imageSet = null;
		tileState = 0;

	}

	static void load(Tile ... tiles){

		for(Tile tile : tiles)
			load(tile);

	}

	static void unload(Tile ... tiles){

		for(Tile tile : tiles)
			unload(tile);

	}

	@Override
	public String toString () {

		return this.getClass().getName();

	}

}