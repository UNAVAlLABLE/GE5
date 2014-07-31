package ge5.engine;

public class Bitmap {
	
	public int width;
	public int height;
	public int[] pixels;
	
	public Bitmap(int[] p, int w, int h){
		
		pixels = p;
		
		width = w;
		
		height = h;
		
	}
	
	public void resize (int w, int h) {
		
		width = w;
		
		height = h;
		
	}

}
