// Basically just an integer array with a width and height to represent an image

package ge5;

public class Bitmap {
	
	public int width;
	public int height;
	public int[] pixels;
	
	public Bitmap(int[] p, int w, int h){
		
		pixels = p;
		
		width = w;
		
		height = h;
		
	}

}
