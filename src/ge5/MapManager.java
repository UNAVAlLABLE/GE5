// Controls Map, (position and type of tiles) and entity positions on the map
// Contains a list of all the map dependencies (Tile and entity types)
// In the future it may be able to save and load map states to map files

package ge5;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MapManager {
	
	private Camera camera;
	private TextureManager textureManager;
	
	private Tile[][] tiles;
	private final int tileSize;
	
	private int width;
	private int height;
	
	private int halfWidth;
	private int halfHeight;
	
	private BufferedImage map;
		
	protected MapManager(Camera camera) {
		
		this.camera = camera;
		
		textureManager = new TextureManager(2, 1);
		textureManager.loadTexture("grass.jpg", 0);
		textureManager.loadTexture("sand.jpg", 1);
		textureManager.loadBlendMap("bt1.bmp", 0);
		
		tileSize = textureManager.textureSize;
		
		width = 3;
		height = 3;
		halfWidth = width / 2;
		halfHeight = height / 2;
		
		tiles = new Tile[width][height];
		
		tiles[0][0] = new Tile(0);
		tiles[0][1] = new Tile(1);
		tiles[0][2] = new Tile(0);
		tiles[1][0] = new Tile(1);
		tiles[1][1] = new Tile(0);
		tiles[1][2] = new Tile(1);
		tiles[2][0] = new Tile(0);
		tiles[2][1] = new Tile(1);
		tiles[2][2] = new Tile(0);
		
		map = new BufferedImage(width * tileSize, height * tileSize, BufferedImage.TYPE_INT_RGB);
		blendTiles();
				
	}
	
	protected void render(Graphics2D g) {
				
		for (int i = 0; i < tiles.length; i++) {
			
			for (int j = 0; j < tiles[0].length; j++) {
				
				g.drawImage(map, null, camera.halfScreenWidth - map.getWidth() / 2, camera.halfScreenHeight - map.getHeight() / 2);
			
			}
			
		}
	
	}
	
	// Doesn't do exactly what we want yet
	// Only works if blend map has exact same resolution as texture
	private void blendTiles() {
		
		Graphics2D g = map.createGraphics();
		
		int blendMapSize = textureManager.blendMapSize;
		
		int pixels[] = map.getRGB(0, 0, map.getWidth(), map.getHeight(), null, 0, map.getWidth());
		int blendMap[] = textureManager.blendMaps[0];
		
		for (int i = 0; i < tiles.length; i++) {
			
			for (int j = 0; j < tiles[0].length; j++) {
			
				int texture[] = textureManager.textures[tiles[i][j].textureID];
				
				int startX = i * tileSize - (blendMapSize - tileSize) / 2;
				int startY = j * tileSize - (blendMapSize - tileSize) / 2;
				
				for (int x = 0; x < blendMapSize; x++) {
					
					for (int y = 0; y < blendMapSize; y++) {
						
						int index = startX + x + (startY + y) * map.getWidth();
						
						if (blendMap[x + y * blendMapSize] == 0) {
							
							pixels[index] = texture[x + y * blendMapSize];
							
						}
						
					}
					
				}
			
			}
		
		}
		
		map.setRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
				
		g.drawImage(map, null, 0, 0);
		
		g.dispose();
		
	}
	
}