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
	private final int tileSize = 32;
	
	private int width;
	private int height;
	
	private BufferedImage map;
		
	protected MapManager(Camera camera) {
		
		this.camera = camera;
		
		textureManager = new TextureManager(2, 1);
		textureManager.loadTexture("grass.jpg", 0);
		textureManager.loadTexture("sand.jpg", 1);
		textureManager.loadBlendMap("bt1.bmp", 0);
				
		width = 3;
		height = 3;
		
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
		
		camera.setHeight(2);
		camera.translate(-map.getWidth() / 2, -map.getHeight() / 2);
				
	}
	
	protected void render(Graphics2D g) {
	
		g.drawRenderedImage(map, camera.transform);
		
	}
	
	private void blendTiles() {
		
		Graphics2D g = map.createGraphics();
				
		int pixels[] = map.getRGB(0, 0, map.getWidth(), map.getHeight(), null, 0, map.getWidth());
		
		int textureSize = textureManager.textureSize;
		
		for (int i = 0; i < width; i++) {
			
			for (int j = 0; j < height; j++) {
			
				int texture[] = textureManager.textures[tiles[i][j].textureID];
				
				int startX = i * tileSize;
				int startY = j * tileSize;
				
				for (int x = 0; x < tileSize; x++) {
					
					for (int y = 0; y < tileSize; y++) {
						
						pixels[startX + x + (startY + y) * map.getWidth()] = texture[x + (textureSize - tileSize) / 2 + (y + (textureSize - tileSize) / 2) * textureSize];
						
					}
					
				}
			
			}
		
		}
		

		for (int i = 0; i < width; i++) {
			
			for (int j = 0; j < height; j++) {
			
				if (tiles[i][j].textureID == 0) {
			
					int texture[] = textureManager.textures[tiles[i][j].textureID];
					int blendMap[] = textureManager.blendMaps[0];
					
					int startX;
					int startY;
					
					if (i != 0) {
						
						startX = i * tileSize - (textureSize - tileSize) / 2;
						startY = j * tileSize;
						
						for (int x = 0; x < (textureSize - tileSize) / 2; x++) {
							
							for (int y = 0; y < tileSize; y++) {
								
								int index = x + (y + (textureSize - tileSize) / 2) * textureSize;
								
								if (blendMap[index] == 0) {
									
									pixels[startX + x + (startY + y) * map.getWidth()] = texture[index];
								
								}
								
							}
							
						}
						
					}
					
					if (j != 0) {
						
						startX = i * tileSize;
						startY = j * tileSize - (textureSize - tileSize) / 2;
						
						for (int x = 0; x < tileSize; x++) {
							
							for (int y = 0; y < (textureSize - tileSize) / 2; y++) {
								
								int index = x + (textureSize - tileSize) / 2 + y * textureSize;
								
								if (blendMap[index] == 0) {
									
									pixels[startX + x + (startY + y) * map.getWidth()] = texture[index];
								
								}
								
							}
							
						}
						
					}
					
					if (i != width - 1) {
						
						startX = (i + 1) * tileSize;
						startY = j * tileSize;
						
						for (int x = 0; x < (textureSize - tileSize) / 2; x++) {
							
							for (int y = 0; y < tileSize; y++) {
								
								int index = x + (textureSize + tileSize) / 2 + (y + (textureSize - tileSize) / 2) * textureSize;
								
								if (blendMap[index] == 0) {
									
									pixels[startX + x + (startY + y) * map.getWidth()] = texture[index];
								
								}
								
							}
							
						}
						
					}
					
					if (j != height - 1) {
						
						startX = i * tileSize;
						startY = (j + 1) * tileSize;
						
						for (int x = 0; x < tileSize; x++) {
							
							for (int y = 0; y < (textureSize - tileSize) / 2; y++) {
								
								int index = x + (textureSize - tileSize) / 2 + (y + (textureSize + tileSize) / 2) * textureSize;
								
								if (blendMap[index] == 0) {
									
									pixels[startX + x + (startY + y) * map.getWidth()] = texture[index];
								
								}
								
							}
							
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