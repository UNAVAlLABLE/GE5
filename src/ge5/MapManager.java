// Controls Map, (position and type of tiles) and entity positions on the map
// Contains a list of all the map dependencies (Tile and entity types)
// In the future it may be able to save and load map states to map files

package ge5;

import java.awt.Color;
import java.awt.Graphics2D;

public class MapManager {
	
	private Camera camera;
	
	// Tile size is 1x1 in map space
	private Tile[][] tiles;
	
	private int width;
	private int height;
	
	protected MapManager(Camera camera) {
		
		this.camera = camera;
		
		tiles = new Tile[2][2];
		
		width = 2;
		height = 2;
		
		tiles[0][0] = new Tile(Color.BLUE);
		tiles[0][1] = new Tile(Color.GREEN);
		tiles[1][0] = new Tile(Color.BLACK);
		tiles[1][1] = new Tile(Color.WHITE);
		
	}
	
	protected void render(Graphics2D g) {
		
		for (int i = 0; i < tiles.length; i++) {
			
			for (int j = 0; j < tiles[0].length; j++) {
				
				g.setColor(tiles[i][j].color);
								
				int tileWidth = (int) (1280 / (camera.ratio * camera.height));
				int tileHeight = (int) (720 / camera.height);
				
				int x = tileWidth * (i - width / 2) + 1280 / 2;
				int y = tileHeight * (j - height / 2) + 720 / 2;
				
				g.fillRect((int) x, (int) y, tileWidth, tileHeight);
			
			}
			
		}
	
	}
	
}