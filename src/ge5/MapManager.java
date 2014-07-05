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
				
		width = 19;
		height = 19;
		
		tiles = new Tile[width][height];
		
		tiles[0][0] = new Tile(0);
		tiles[1][0] = new Tile(0);
		tiles[2][0] = new Tile(0);
		tiles[3][0] = new Tile(0);
		tiles[4][0] = new Tile(0);
		tiles[5][0] = new Tile(0);
		tiles[6][0] = new Tile(0);
		tiles[7][0] = new Tile(0);
		tiles[8][0] = new Tile(0);
		tiles[9][0] = new Tile(0);
		tiles[10][0] = new Tile(0);
		tiles[11][0] = new Tile(0);
		tiles[12][0] = new Tile(0);
		tiles[13][0] = new Tile(0);
		tiles[14][0] = new Tile(0);
		tiles[15][0] = new Tile(0);
		tiles[16][0] = new Tile(0);
		tiles[17][0] = new Tile(0);
		tiles[18][0] = new Tile(0);
		tiles[0][1] = new Tile(0);
		tiles[1][1] = new Tile(1);
		tiles[2][1] = new Tile(0);
		tiles[3][1] = new Tile(1);
		tiles[4][1] = new Tile(0);
		tiles[5][1] = new Tile(0);
		tiles[6][1] = new Tile(0);
		tiles[7][1] = new Tile(0);
		tiles[8][1] = new Tile(0);
		tiles[9][1] = new Tile(0);
		tiles[10][1] = new Tile(1);
		tiles[11][1] = new Tile(0);
		tiles[12][1] = new Tile(0);
		tiles[13][1] = new Tile(0);
		tiles[14][1] = new Tile(0);
		tiles[15][1] = new Tile(0);
		tiles[16][1] = new Tile(0);
		tiles[17][1] = new Tile(1);
		tiles[18][1] = new Tile(0);
		tiles[0][2] = new Tile(0);
		tiles[1][2] = new Tile(0);
		tiles[2][2] = new Tile(1);
		tiles[3][2] = new Tile(0);
		tiles[4][2] = new Tile(0);
		tiles[5][2] = new Tile(0);
		tiles[6][2] = new Tile(0);
		tiles[7][2] = new Tile(0);
		tiles[8][2] = new Tile(1);
		tiles[9][2] = new Tile(1);
		tiles[10][2] = new Tile(0);
		tiles[11][2] = new Tile(0);
		tiles[12][2] = new Tile(0);
		tiles[13][2] = new Tile(0);
		tiles[14][2] = new Tile(0);
		tiles[15][2] = new Tile(1);
		tiles[16][2] = new Tile(1);
		tiles[17][2] = new Tile(0);
		tiles[18][2] = new Tile(0);
		tiles[0][3] = new Tile(0);
		tiles[1][3] = new Tile(0);
		tiles[2][3] = new Tile(1);
		tiles[3][3] = new Tile(0);
		tiles[4][3] = new Tile(0);
		tiles[5][3] = new Tile(0);
		tiles[6][3] = new Tile(0);
		tiles[7][3] = new Tile(0);
		tiles[8][3] = new Tile(0);
		tiles[9][3] = new Tile(0);
		tiles[10][3] = new Tile(1);
		tiles[11][3] = new Tile(0);
		tiles[12][3] = new Tile(0);
		tiles[13][3] = new Tile(0);
		tiles[14][3] = new Tile(0);
		tiles[15][3] = new Tile(0);
		tiles[16][3] = new Tile(0);
		tiles[17][3] = new Tile(1);
		tiles[18][3] = new Tile(0);
		tiles[0][4] = new Tile(0);
		tiles[1][4] = new Tile(0);
		tiles[2][4] = new Tile(0);
		tiles[3][4] = new Tile(0);
		tiles[4][4] = new Tile(0);
		tiles[5][4] = new Tile(0);
		tiles[6][4] = new Tile(0);
		tiles[7][4] = new Tile(0);
		tiles[8][4] = new Tile(0);
		tiles[9][4] = new Tile(0);
		tiles[10][4] = new Tile(0);
		tiles[11][4] = new Tile(0);
		tiles[12][4] = new Tile(0);
		tiles[13][4] = new Tile(0);
		tiles[14][4] = new Tile(0);
		tiles[15][4] = new Tile(0);
		tiles[16][4] = new Tile(0);
		tiles[17][4] = new Tile(0);
		tiles[18][4] = new Tile(0);
		tiles[0][5] = new Tile(0);
		tiles[1][5] = new Tile(0);
		tiles[2][5] = new Tile(0);
		tiles[3][5] = new Tile(0);
		tiles[4][5] = new Tile(0);
		tiles[5][5] = new Tile(0);
		tiles[6][5] = new Tile(0);
		tiles[7][5] = new Tile(0);
		tiles[8][5] = new Tile(0);
		tiles[9][5] = new Tile(0);
		tiles[10][5] = new Tile(0);
		tiles[11][5] = new Tile(0);
		tiles[12][5] = new Tile(0);
		tiles[13][5] = new Tile(0);
		tiles[14][5] = new Tile(0);
		tiles[15][5] = new Tile(0);
		tiles[16][5] = new Tile(0);
		tiles[17][5] = new Tile(0);
		tiles[18][5] = new Tile(0);
		tiles[0][6] = new Tile(0);
		tiles[1][6] = new Tile(0);
		tiles[2][6] = new Tile(0);
		tiles[3][6] = new Tile(0);
		tiles[4][6] = new Tile(0);
		tiles[5][6] = new Tile(0);
		tiles[6][6] = new Tile(0);
		tiles[7][6] = new Tile(0);
		tiles[8][6] = new Tile(1);
		tiles[9][6] = new Tile(0);
		tiles[10][6] = new Tile(1);
		tiles[11][6] = new Tile(0);
		tiles[12][6] = new Tile(0);
		tiles[13][6] = new Tile(0);
		tiles[14][6] = new Tile(0);
		tiles[15][6] = new Tile(0);
		tiles[16][6] = new Tile(0);
		tiles[17][6] = new Tile(0);
		tiles[18][6] = new Tile(0);
		tiles[0][7] = new Tile(0);
		tiles[1][7] = new Tile(0);
		tiles[2][7] = new Tile(0);
		tiles[3][7] = new Tile(0);
		tiles[4][7] = new Tile(0);
		tiles[5][7] = new Tile(0);
		tiles[6][7] = new Tile(0);
		tiles[7][7] = new Tile(0);
		tiles[8][7] = new Tile(0);
		tiles[9][7] = new Tile(1);
		tiles[10][7] = new Tile(0);
		tiles[11][7] = new Tile(0);
		tiles[12][7] = new Tile(0);
		tiles[13][7] = new Tile(0);
		tiles[14][7] = new Tile(0);
		tiles[15][7] = new Tile(0);
		tiles[16][7] = new Tile(0);
		tiles[17][7] = new Tile(0);
		tiles[18][7] = new Tile(0);
		tiles[0][8] = new Tile(0);
		tiles[1][8] = new Tile(1);
		tiles[2][8] = new Tile(0);
		tiles[3][8] = new Tile(1);
		tiles[4][8] = new Tile(0);
		tiles[5][8] = new Tile(0);
		tiles[6][8] = new Tile(1);
		tiles[7][8] = new Tile(0);
		tiles[8][8] = new Tile(0);
		tiles[9][8] = new Tile(1);
		tiles[10][8] = new Tile(0);
		tiles[11][8] = new Tile(0);
		tiles[12][8] = new Tile(1);
		tiles[13][8] = new Tile(0);
		tiles[14][8] = new Tile(0);
		tiles[15][8] = new Tile(0);
		tiles[16][8] = new Tile(1);
		tiles[17][8] = new Tile(0);
		tiles[18][8] = new Tile(0);
		tiles[0][9] = new Tile(0);
		tiles[1][9] = new Tile(0);
		tiles[2][9] = new Tile(1);
		tiles[3][9] = new Tile(0);
		tiles[4][9] = new Tile(0);
		tiles[5][9] = new Tile(0);
		tiles[6][9] = new Tile(0);
		tiles[7][9] = new Tile(1);
		tiles[8][9] = new Tile(1);
		tiles[9][9] = new Tile(0);
		tiles[10][9] = new Tile(1);
		tiles[11][9] = new Tile(1);
		tiles[12][9] = new Tile(0);
		tiles[13][9] = new Tile(0);
		tiles[14][9] = new Tile(0);
		tiles[15][9] = new Tile(0);
		tiles[16][9] = new Tile(1);
		tiles[17][9] = new Tile(0);
		tiles[18][9] = new Tile(0);
		tiles[0][10] = new Tile(0);
		tiles[1][10] = new Tile(0);
		tiles[2][10] = new Tile(1);
		tiles[3][10] = new Tile(0);
		tiles[4][10] = new Tile(0);
		tiles[5][10] = new Tile(0);
		tiles[6][10] = new Tile(1);
		tiles[7][10] = new Tile(0);
		tiles[8][10] = new Tile(0);
		tiles[9][10] = new Tile(1);
		tiles[10][10] = new Tile(0);
		tiles[11][10] = new Tile(0);
		tiles[12][10] = new Tile(1);
		tiles[13][10] = new Tile(0);
		tiles[14][10] = new Tile(0);
		tiles[15][10] = new Tile(1);
		tiles[16][10] = new Tile(0);
		tiles[17][10] = new Tile(1);
		tiles[18][10] = new Tile(0);
		tiles[0][11] = new Tile(0);
		tiles[1][11] = new Tile(0);
		tiles[2][11] = new Tile(0);
		tiles[3][11] = new Tile(0);
		tiles[4][11] = new Tile(0);
		tiles[5][11] = new Tile(0);
		tiles[6][11] = new Tile(0);
		tiles[7][11] = new Tile(0);
		tiles[8][11] = new Tile(0);
		tiles[9][11] = new Tile(1);
		tiles[10][11] = new Tile(0);
		tiles[11][11] = new Tile(0);
		tiles[12][11] = new Tile(0);
		tiles[13][11] = new Tile(0);
		tiles[14][11] = new Tile(0);
		tiles[15][11] = new Tile(0);
		tiles[16][11] = new Tile(0);
		tiles[17][11] = new Tile(0);
		tiles[18][11] = new Tile(0);
		tiles[0][12] = new Tile(0);
		tiles[1][12] = new Tile(0);
		tiles[2][12] = new Tile(0);
		tiles[3][12] = new Tile(0);
		tiles[4][12] = new Tile(0);
		tiles[5][12] = new Tile(0);
		tiles[6][12] = new Tile(0);
		tiles[7][12] = new Tile(0);
		tiles[8][12] = new Tile(1);
		tiles[9][12] = new Tile(0);
		tiles[10][12] = new Tile(1);
		tiles[11][12] = new Tile(0);
		tiles[12][12] = new Tile(0);
		tiles[13][12] = new Tile(0);
		tiles[14][12] = new Tile(0);
		tiles[15][12] = new Tile(0);
		tiles[16][12] = new Tile(0);
		tiles[17][12] = new Tile(0);
		tiles[18][12] = new Tile(0);
		tiles[0][13] = new Tile(0);
		tiles[1][13] = new Tile(0);
		tiles[2][13] = new Tile(0);
		tiles[3][13] = new Tile(0);
		tiles[4][13] = new Tile(0);
		tiles[5][13] = new Tile(0);
		tiles[6][13] = new Tile(0);
		tiles[7][13] = new Tile(0);
		tiles[8][13] = new Tile(0);
		tiles[9][13] = new Tile(0);
		tiles[10][13] = new Tile(0);
		tiles[11][13] = new Tile(0);
		tiles[12][13] = new Tile(0);
		tiles[13][13] = new Tile(0);
		tiles[14][13] = new Tile(0);
		tiles[15][13] = new Tile(0);
		tiles[16][13] = new Tile(0);
		tiles[17][13] = new Tile(0);
		tiles[18][13] = new Tile(0);
		tiles[0][14] = new Tile(0);
		tiles[1][14] = new Tile(0);
		tiles[2][14] = new Tile(0);
		tiles[3][14] = new Tile(0);
		tiles[4][14] = new Tile(0);
		tiles[5][14] = new Tile(0);
		tiles[6][14] = new Tile(0);
		tiles[7][14] = new Tile(0);
		tiles[8][14] = new Tile(0);
		tiles[9][14] = new Tile(0);
		tiles[10][14] = new Tile(0);
		tiles[11][14] = new Tile(0);
		tiles[12][14] = new Tile(0);
		tiles[13][14] = new Tile(0);
		tiles[14][14] = new Tile(0);
		tiles[15][14] = new Tile(0);
		tiles[16][14] = new Tile(0);
		tiles[17][14] = new Tile(0);
		tiles[18][14] = new Tile(0);
		tiles[0][15] = new Tile(0);
		tiles[1][15] = new Tile(1);
		tiles[2][15] = new Tile(0);
		tiles[3][15] = new Tile(0);
		tiles[4][15] = new Tile(0);
		tiles[5][15] = new Tile(0);
		tiles[6][15] = new Tile(0);
		tiles[7][15] = new Tile(0);
		tiles[8][15] = new Tile(1);
		tiles[9][15] = new Tile(0);
		tiles[10][15] = new Tile(0);
		tiles[11][15] = new Tile(0);
		tiles[12][15] = new Tile(0);
		tiles[13][15] = new Tile(0);
		tiles[14][15] = new Tile(0);
		tiles[15][15] = new Tile(0);
		tiles[16][15] = new Tile(1);
		tiles[17][15] = new Tile(0);
		tiles[18][15] = new Tile(0);
		tiles[0][16] = new Tile(0);
		tiles[1][16] = new Tile(0);
		tiles[2][16] = new Tile(1);
		tiles[3][16] = new Tile(1);
		tiles[4][16] = new Tile(0);
		tiles[5][16] = new Tile(0);
		tiles[6][16] = new Tile(0);
		tiles[7][16] = new Tile(0);
		tiles[8][16] = new Tile(0);
		tiles[9][16] = new Tile(1);
		tiles[10][16] = new Tile(1);
		tiles[11][16] = new Tile(0);
		tiles[12][16] = new Tile(0);
		tiles[13][16] = new Tile(0);
		tiles[14][16] = new Tile(0);
		tiles[15][16] = new Tile(0);
		tiles[16][16] = new Tile(1);
		tiles[17][16] = new Tile(0);
		tiles[18][16] = new Tile(0);
		tiles[0][17] = new Tile(0);
		tiles[1][17] = new Tile(1);
		tiles[2][17] = new Tile(0);
		tiles[3][17] = new Tile(0);
		tiles[4][17] = new Tile(0);
		tiles[5][17] = new Tile(0);
		tiles[6][17] = new Tile(0);
		tiles[7][17] = new Tile(0);
		tiles[8][17] = new Tile(1);
		tiles[9][17] = new Tile(0);
		tiles[10][17] = new Tile(0);
		tiles[11][17] = new Tile(0);
		tiles[12][17] = new Tile(0);
		tiles[13][17] = new Tile(0);
		tiles[14][17] = new Tile(0);
		tiles[15][17] = new Tile(1);
		tiles[16][17] = new Tile(0);
		tiles[17][17] = new Tile(1);
		tiles[18][17] = new Tile(0);
		tiles[0][18] = new Tile(0);
		tiles[1][18] = new Tile(0);
		tiles[2][18] = new Tile(0);
		tiles[3][18] = new Tile(0);
		tiles[4][18] = new Tile(0);
		tiles[5][18] = new Tile(0);
		tiles[6][18] = new Tile(0);
		tiles[7][18] = new Tile(0);
		tiles[8][18] = new Tile(0);
		tiles[9][18] = new Tile(0);
		tiles[10][18] = new Tile(0);
		tiles[11][18] = new Tile(0);
		tiles[12][18] = new Tile(0);
		tiles[13][18] = new Tile(0);
		tiles[14][18] = new Tile(0);
		tiles[15][18] = new Tile(0);
		tiles[16][18] = new Tile(0);
		tiles[17][18] = new Tile(0);
		tiles[18][18] = new Tile(0);
		
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