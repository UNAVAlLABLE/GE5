package ge5;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TextureManager {
	
	protected BufferedImage[] textures;
	
	protected TextureManager(int numTextures) {
		
		textures = new BufferedImage[numTextures];
		
	}
	
	protected void loadTexture(String fileName, int index) {
		
		try {
			
			textures[index] = ImageIO.read(new File("res/" + fileName));
		
		} catch (Exception e) {
			
			System.out.println("Failed to load image file");
			
		}
		
	}

}
