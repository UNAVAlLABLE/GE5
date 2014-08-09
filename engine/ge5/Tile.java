// Unlike other tickable classes, Tiles are not to tick every frame but instead only tick when directly called.

// This class is to be extended for more advanced tile types like animated tiles or transparent tiles.

// All images stored in this tile will be in the form of integer arrays and must always be static.

// Should contain methods to load and unload the tile images and other resources in and out of the memory
// as we are manually keeping this class in scope.

package ge5;

public abstract class Tile implements tickable {

	// When this tile is loaded this should contain all images of this tile type
	static int[][] imageSet;

	// References which image in the imageSet that would be displayed during rendering
	int imageState;

	void load(){

	}

	void unload(){

		imageSet = null;
		imageState = 0;

	}

	@Override
	public String toString () {

		return this.getClass().getName();

	}

}