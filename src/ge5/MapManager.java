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
	
	// Just so it doesn't have to be calculated every frame
	private float halfWidth;
	private float halfHeight;
	
	private int tileWidth;
	private int tileHeight;
	
	protected MapManager(Camera camera) {
		
		this.camera = camera;
		camera.setMap(this);
		
		width = 3;
		height = 3;
		halfWidth = width / 2.0f;
		halfHeight = height / 2.0f;
		
		tiles = new Tile[width][height];
		
		tiles[0][0] = new Tile(Color.BLUE.getRGB());
		tiles[0][1] = new Tile(Color.GREEN.getRGB());
		tiles[0][2] = new Tile(Color.ORANGE.getRGB());
		tiles[1][0] = new Tile(Color.BLACK.getRGB());
		tiles[1][1] = new Tile(Color.WHITE.getRGB());
		tiles[1][2] = new Tile(Color.CYAN.getRGB());
		tiles[2][0] = new Tile(Color.GRAY.getRGB());
		tiles[2][1] = new Tile(Color.BLUE.getRGB());
		tiles[2][2] = new Tile(Color.GREEN.getRGB());
		
		setTileDimensions();
		
		long start = System.nanoTime();
		interpolate();
		System.out.println((System.nanoTime() - start) / 10000000.0f);
		
	}
	
	protected void render(Graphics2D g) {
				
		for (int i = 0; i < tiles.length; i++) {
			
			for (int j = 0; j < tiles[0].length; j++) {
				
				g.setColor(new Color(tiles[i][j].color, true));
				
				int x = (int) (tileWidth * (i - halfWidth) + camera.halfScreenWidth);
				int y = (int) (tileHeight * (j - halfHeight) + camera.halfScreenHeight);
				
				g.fillRect(x, y, tileWidth, tileHeight);
			
			}
			
		}
	
	}
	
	// Called whenever tile screen size should change
	// I don't like the way this method is called right now (from the camera class)
	// But it's better than calling every frame
	protected void setTileDimensions() {
				
		float height = camera.getHeight();
		
		tileWidth = (int) (camera.screenWidth / (camera.ratio * height));
		tileHeight = (int) (camera.screenHeight / height);
	
	}
	
	
	// Bilinear interpolation between tile colors
	// High priority potential bottle-neck (if used every frame, of course)
	// OPTIMIZE!!!
	
	// This only looks at the neighboring 4 tiles
	// It can be extended to look at 8 tiles, but then the formula gets a little more complicated
	
	// Not using Vector class to avoid any associated overhead
	// Trying to stick to primitives
	
	// Very repetitive code, could probably be condensed
	
	// Note: code is incomplete at the moment
	// There are still some special cases like the outer tiles
	// Also... this method wasn't tested yet
	private void interpolate() {
		
		int[] pixels = new int[width * height * tileWidth * tileHeight];
		
		// Local center common to all tiles
		int localTileCenterX = tileWidth / 2;
		int localTileCenterY = tileHeight / 2;
		
		for (int i = 1; i < tiles.length - 1; i++) {
			
			for (int j = 1; j < tiles[0].length - 1; j++) {
				
				// Center of tile in pixels
				int tileCenterX = i * tileWidth + localTileCenterX;
				int tileCenterY = i * tileHeight + localTileCenterY;
								
				for (int x = 0; x < localTileCenterX; x++) {
					
					int x1 = (i - 1) * tileWidth + localTileCenterX;
					int x2 = tileCenterX;
					
					for (int y = 0; y < localTileCenterY; y++) {
						
						int pixelX = i * tileWidth + x;
						int pixelY = j * tileHeight + y;
						
						int y1 = (j - 1) * tileHeight + localTileCenterY;
						int y2 = tileCenterY;
						
						int divisor = (x2 - x1) * (y2 - y1);
						
						int Q11 = tiles[i - 1][j - 1].color;
						int Q21 = tiles[i][j - 1].color;
						int Q12 = tiles[i - 1][j + 1].color;
						int Q22 = tiles[i][j].color;
						
						// Implementation of formula copied from wikipedia :)
						float newColor = Q11 * (x2 - pixelX) * (y2 - pixelY);
						newColor += Q21 * (pixelX - x1) * (y2 - pixelY);
						newColor += Q12 * (x2 - pixelX) * (pixelY - y1);
						newColor += Q22 * (pixelX - x1) * (pixelY - y1);
						
						newColor = newColor / divisor;
						
						pixels[pixelX + pixelY * tileWidth * width] = (int) newColor;
						
					}
					
					for (int y = localTileCenterY + 1; y < tileHeight; y++) {
												
						int pixelX = i * tileWidth + x;
						int pixelY = j * tileHeight + y;
						
						int y1 = tileCenterY;
						int y2 = (j + 1) * tileHeight + localTileCenterY;
						
						int divisor = (x2 - x1) * (y2 - y1);
						
						int Q11 = tiles[i - 1][j].color;
						int Q21 = tiles[i][j].color;
						int Q12 = tiles[i - 1][j + 1].color;
						int Q22 = tiles[i][j + 1].color;
						
						float newColor = Q11 * (x2 - pixelX) * (y2 - pixelY);
						newColor += Q21 * (pixelX - x1) * (y2 - pixelY);
						newColor += Q12 * (x2 - pixelX) * (pixelY - y1);
						newColor += Q22 * (pixelX - x1) * (pixelY - y1);
						
						newColor = newColor / divisor;
						
						pixels[pixelX + pixelY * tileWidth * width] = (int) newColor;
						
					}
					
				}
				
				for (int x = localTileCenterX + 1; x < tileWidth; x++) {
					
					int x1 = tileCenterX;
					int x2 = (i + 1) * tileWidth + localTileCenterX;
					
					for (int y = 0; y < localTileCenterY; y++) {
						
						int pixelX = i * tileWidth + x;
						int pixelY = j * tileHeight + y;
						
						int y1 = (j - 1) * tileHeight + localTileCenterY;
						int y2 = tileCenterY;
						
						int divisor = (x2 - x1) * (y2 - y1);
						
						int Q11 = tiles[i][j - 1].color;
						int Q21 = tiles[i + 1][j - 1].color;
						int Q12 = tiles[i][j + 1].color;
						int Q22 = tiles[i + 1][j].color;
						
						float newColor = Q11 * (x2 - pixelX) * (y2 - pixelY);
						newColor += Q21 * (pixelX - x1) * (y2 - pixelY);
						newColor += Q12 * (x2 - pixelX) * (pixelY - y1);
						newColor += Q22 * (pixelX - x1) * (pixelY - y1);
						
						newColor = newColor / divisor;
						
						pixels[pixelX + pixelY * tileWidth * width] = (int) newColor;
						
					}
					
					for (int y = localTileCenterY + 1; y < tileHeight; y++) {
						
						int pixelX = i * tileWidth + x;
						int pixelY = j * tileHeight + y;
						
						int y1 = tileCenterY;
						int y2 = (j + 1) * tileHeight + localTileCenterY;
						
						int divisor = (x2 - x1) * (y2 - y1);
						
						int Q11 = tiles[i][j].color;
						int Q21 = tiles[i + 1][j].color;
						int Q12 = tiles[i][j + 1].color;
						int Q22 = tiles[i + 1][j + 1].color;
						
						float newColor = Q11 * (x2 - pixelX) * (y2 - pixelY);
						newColor += Q21 * (pixelX - x1) * (y2 - pixelY);
						newColor += Q12 * (x2 - pixelX) * (pixelY - y1);
						newColor += Q22 * (pixelX - x1) * (pixelY - y1);
						
						newColor = newColor / divisor;
						
						pixels[pixelX + pixelY * tileWidth * width] = (int) newColor;
						
					}
					
				}
							
			}
			
		}
		
	}
	
}