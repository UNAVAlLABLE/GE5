package ge5;

public class GameLauncher{

	public static void main(String[] args) {
		
	    ClassLoader classLoader = GameLauncher.class.getClassLoader();

	    try {
	   
	    	for (String str:args) {
	    		
				classLoader.loadClass(str).newInstance();
				
			}
	        
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
				
	}

}