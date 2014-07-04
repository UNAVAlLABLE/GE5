package ge5;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// Should probably rename this to something else
public class TextureManager {
	
	protected final int textureSize = 64;
	protected final int blendMapSize = 64;
	
	protected int[][] textures;
	protected int[][] blendMaps;
	
	protected TextureManager(int numTextures, int numBlendMaps) {
		
		textures = new int[numTextures][textureSize];
		blendMaps = new int[numBlendMaps][blendMapSize];
		
	}
	
	protected void loadTexture(String fileName, int index) {
		
		try {
			
			BufferedImage img = ImageIO.read(new File("res/" + fileName));
			
			// Resize if necessary
			if (img.getWidth() != textureSize || img.getHeight() != textureSize) {
				
				BufferedImage newImg = new BufferedImage(textureSize, textureSize, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImg.createGraphics();
				g.drawImage(img, 0, 0, textureSize, textureSize, null);
				g.dispose();
				
				img = newImg;
				
			}
			
			textures[index] = img.getRGB(0, 0, textureSize, textureSize, null, 0, textureSize);
			
		} catch (Exception e) {
			
			System.out.println("Failed to load texture");
			
		}
		
	}
	
	protected void loadBlendMap(String fileName, int index) {
		
		try {
			
			BufferedImage img = ImageIO.read(new File("res/" + fileName));
			
			// Resize if necessary
			if (img.getWidth() != blendMapSize || img.getHeight() != blendMapSize) {
				
				BufferedImage newImg = new BufferedImage(blendMapSize, blendMapSize, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImg.createGraphics();
				g.drawImage(img, 0, 0, blendMapSize, blendMapSize, null);
				g.dispose();
				
				img = newImg;
				
			}
						
			int[] blendMap = new int[blendMapSize * blendMapSize];
			int[] pixels = img.getRGB(0, 0, blendMapSize, blendMapSize, null, 0, blendMapSize);
			
			for (int i = 0; i < pixels.length; i++) {
				
				if ((pixels[i] & 0x000000FF) == 0)
					blendMap[i] = 0;
				else
					blendMap[i] = 1;
				
			}
			
			blendMaps[index] = blendMap;
			
		} catch (Exception e) {
			
			System.out.println("Failed to load blend map");
			e.printStackTrace();
			
		}
		
	}

}
