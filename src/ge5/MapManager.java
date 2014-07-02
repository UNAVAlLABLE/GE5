// Controls Map, (position and type of tiles) and entity positions on the map
// Contains a list of all the map dependencies (Tile and entity types)
// In the future it may be able to save and load map states to map files

package ge5;

import java.awt.Color;
import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;

public class MapManager {
	
	private Camera camera;
	private TextureManager textureManager;
	
	private Tile[][] tiles;
	
	private int width;
	private int height;
	
	private float halfWidth;
	private float halfHeight;
	
	private int tileWidth;
	private int tileHeight;
	
//	private BufferedImage img;
	
	protected MapManager(Camera camera) {
		
		this.camera = camera;
		camera.setMap(this);
		
		textureManager = new TextureManager(2);
		textureManager.loadTexture("grass.jpg", 0);
		
		width = 3;
		height = 3;
		halfWidth = width / 2.0f;
		halfHeight = height / 2.0f;
		
		tiles = new Tile[width][height];
		
		tiles[0][0] = new Tile(0);
		tiles[0][1] = new Tile(0);
		tiles[0][2] = new Tile(0);
		tiles[1][0] = new Tile(0);
		tiles[1][1] = new Tile(0);
		tiles[1][2] = new Tile(0);
		tiles[2][0] = new Tile(0);
		tiles[2][1] = new Tile(0);
		tiles[2][2] = new Tile(0);
		
		setTileDimensions();
		
//		img = new BufferedImage(tileWidth * width, tileHeight * height, BufferedImage.TYPE_INT_ARGB);
//		
//		img.setRGB(0, 0, width * tileWidth, height * tileHeight, interpolate(), 0, width * tileWidth);
		
	}
	
	protected void render(Graphics2D g) {
				
		for (int i = 0; i < tiles.length; i++) {
			
			for (int j = 0; j < tiles[0].length; j++) {
				
//				g.drawImage(img, null, 0, 0);
				
				int x = (int) (tileWidth * (i - halfWidth) + camera.halfScreenWidth);
				int y = (int) (tileHeight * (j - halfHeight) + camera.halfScreenHeight);
				
				g.drawImage(textureManager.textures[tiles[i][j].textureID], null, x, y);
			
			}
			
		}
	
	}
	
	protected void setTileDimensions() {
				
		float height = camera.getHeight();
		
		tileWidth = (int) (camera.screenWidth / (camera.ratio * height));
		tileHeight = (int) (camera.screenHeight / height);
	
	}
	
//	private int[] interpolate() {
//		
//		int[] pixels = new int[width * height * tileWidth * tileHeight];
//		
//		int localTileCenterX = tileWidth / 2;
//		int localTileCenterY = tileHeight / 2;
//		
//		int pixelX, pixelY;
//		int x1, x2, y1, y2;
//		int up, down, left, right;
//		int Q11, Q21, Q12, Q22;
//		
//		int i, j, x, y;
//		
//		for (i = 0; i < tiles.length; i++) {
//						
//			for (j = 0; j < tiles[0].length; j++) {
//								
//				if (i == 0)
//					left = i;
//				else
//					left = i - 1;
//				
//				if (j == 0)
//					up = j;
//				else
//					up = j - 1;
//				
//				right = i;
//				down = j;
//				
//				x1 = left * tileWidth + localTileCenterX;
//				y1 = up * tileHeight + localTileCenterY;
//				
//				x2 = right * tileWidth + localTileCenterX;
//				y2 = down * tileHeight + localTileCenterY;
//				
//				Q11 = tiles[left][up].color;
//				Q21 = tiles[right][up].color;
//				Q12 = tiles[left][down].color;
//				Q22 = tiles[right][down].color;
//								
//				for (x = 0; x < localTileCenterX; x++) {
//					
//					for (y = 0; y < localTileCenterY; y++) {
//						
//						pixelX = i * tileWidth + x;
//						pixelY = j * tileHeight + y;
//						
//						pixels[pixelX + pixelY * tileWidth * width] = (int) interpolateColor(pixelX, pixelY, x1, y1, x2, y2, Q11, Q21, Q12, Q22);
//						
//					}
//					
//				}
//				
//				if (i == 0)
//					left = i;
//				else
//					left = i - 1;
//				
//				if (j == tiles[0].length - 1)
//					down = j;
//				else
//					down = j + 1;
//				
//				right = i;
//				up = j;
//				
//				x1 = left * tileWidth + localTileCenterX;
//				y1 = up * tileHeight + localTileCenterY;
//				
//				x2 = right * tileWidth + localTileCenterX;
//				y2 = down * tileHeight + localTileCenterY;
//				
//				Q11 = tiles[left][up].color;
//				Q21 = tiles[right][up].color;
//				Q12 = tiles[left][down].color;
//				Q22 = tiles[right][down].color;
//				
//				for (x = 0; x < localTileCenterX; x++) {
//					
//					for (y = localTileCenterY; y < tileHeight; y++) {
//												
//						pixelX = i * tileWidth + x;
//						pixelY = j * tileHeight + y;
//						
//						pixels[pixelX + pixelY * tileWidth * width] = (int) interpolateColor(pixelX, pixelY, x1, y1, x2, y2, Q11, Q21, Q12, Q22);
//						
//					}
//					
//				}
//				
//				if (i == tiles.length - 1)
//					right = i;
//				else
//					right = i + 1;
//				
//				if (j == 0)
//					up = j;
//				else
//					up = j - 1;
//				
//				left = i;
//				down = j;
//				
//				x1 = left * tileWidth + localTileCenterX;
//				y1 = up * tileHeight + localTileCenterY;
//				
//				x2 = right * tileWidth + localTileCenterX;
//				y2 = down * tileHeight + localTileCenterY;
//				
//				Q11 = tiles[left][up].color;
//				Q21 = tiles[right][up].color;
//				Q12 = tiles[left][down].color;
//				Q22 = tiles[right][down].color;
//				
//				for (x = localTileCenterX; x < tileWidth; x++) {
//					
//					for (y = 0; y < localTileCenterY; y++) {
//						
//						pixelX = i * tileWidth + x;
//						pixelY = j * tileHeight + y;
//						
//						pixels[pixelX + pixelY * tileWidth * width] = (int) interpolateColor(pixelX, pixelY, x1, y1, x2, y2, Q11, Q21, Q12, Q22);
//						
//					}
//				
//				}
//					
//				if (i == tiles.length - 1)
//					right = i;
//				else
//					right = i + 1;
//				
//				if (j == tiles[0].length - 1)
//					down = j;
//				else
//					down = j + 1;
//				
//				left = i;
//				down = j;
//				
//				x1 = left * tileWidth + localTileCenterX;
//				y1 = up * tileHeight + localTileCenterY;
//				
//				x2 = right * tileWidth + localTileCenterX;
//				y2 = down * tileHeight + localTileCenterY;
//				
//				Q11 = tiles[left][up].color;
//				Q21 = tiles[right][up].color;
//				Q12 = tiles[left][down].color;
//				Q22 = tiles[right][down].color;
//				
//				for (x = localTileCenterX; x < tileWidth; x++) {
//				
//					for (y = localTileCenterY; y < tileHeight; y++) {
//						
//						pixelX = i * tileWidth + x;
//						pixelY = j * tileHeight + y;
//						
//						pixels[pixelX + pixelY * tileWidth * width] = (int) interpolateColor(pixelX, pixelY, x1, y1, x2, y2, Q11, Q21, Q12, Q22);
//						
//					}
//					
//				}
//				
//			}
//			
//		}
//		
//		return pixels;
//		
//	}
//	
//	private float interpolateColor(int x, int y, int x1, int y1, int x2, int y2, int Q11, int Q21, int Q12, int Q22) {
//		
//		int R11 = (Q11 & 0x00FF0000) >> 16;
//		int R21 = (Q21 & 0x00FF0000) >> 16;
//		int R12 = (Q12 & 0x00FF0000) >> 16;
//		int R22 = (Q22 & 0x00FF0000) >> 16;
//					
//		int r = (int) interpolateBilinear(x, y, x1, x2, y1, y2, R11, R21, R12, R22);
//		
//		int G11 = (Q11 & 0x0000FF00) >> 8;
//		int G21 = (Q21 & 0x0000FF00) >> 8;
//		int G12 = (Q12 & 0x0000FF00) >> 8;
//		int G22 = (Q22 & 0x0000FF00) >> 8;
//					
//		int g = (int) interpolateBilinear(x, y, x1, x2, y1, y2, G11, G21, G12, G22);
//		
//		int B11 = (Q11 & 0x000000FF);
//		int B21 = (Q21 & 0x000000FF);
//		int B12 = (Q12 & 0x000000FF);
//		int B22 = (Q22 & 0x000000FF);
//					
//		int b = (int) interpolateBilinear(x, y, x1, x2, y1, y2, B11, B21, B12, B22);
//						
//		return (r << 16) | (g << 8) | b | 0xFF000000;
//		
//	}
//	
//	private float interpolateBilinear(int x, int y, int x1, int y1, int x2, int y2, int Q11, int Q21, int Q12, int Q22) {
//				
//		if (x1 == x2 && y1== y2)
//			return 0;
//
//		if (x1 == x2) {
//			return 0;
//			//return Q11 + (Q12 - Q11) * (y - y1) / (y2 - y1);
//		}
//	
//		if (y1 == y2) {
//			return 0;
//			//return Q12 + (Q22 - Q12) * (x - x1) / (x2 - x1);
//		}
//		int divisor = (x2 - x1) * (y2 - y1);
//		
//		int deltaX1 = x - x1;
//		int deltaY1 = y - y1;
//		
//		int deltaX2 = x2 - x;
//		int deltaY2 = y2 - y;
//		
//		float newColor = Q11 * deltaX2 * deltaY2;
//		newColor += Q21 * deltaX1 * deltaY2;
//		newColor += Q12 * deltaX2 * deltaY1;
//		newColor += Q22 * deltaX1 * deltaY1;
//				
//		newColor /= divisor;
//				
//		return newColor;
//		
//	}
	
}