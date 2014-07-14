package ge5;

import java.io.FileInputStream;
import java.io.IOException;

public class GameLauncher extends ClassLoader {
	
	public Class<?> findClass(String name) {
	    byte[] classData = null;
	    try {
	      FileInputStream f = new FileInputStream("C:\\" + name + ".class");
	      int num = f.available();
	      classData = new byte[num];
	      f.read(classData);
	      f.close();
	    } catch (IOException e) {
	      System.out.println(e);
	    }
	    Class<?> x = defineClass(name, classData, 0, classData.length);
	    return x;
	}

	public static void main(String[] args) {
				
//		Scene[] scenes;
		
//		int numScenes = 0;
		
	    ClassLoader classLoader = new GameLauncher();

	    try {
	   
//	    	for (String str : args) {
//	    		
//				if (Scene.class.isAssignableFrom(classLoader.loadClass(str)))
//					numScenes++;
//				
//			}
	    	
//	    	scenes = new Scene[numScenes];
	    	
	    	for (int i = 0; i < args.length; i++) {
	    		
	    		Class<?> clazz = classLoader.loadClass(args[i]);
	    	
				if (Scene.class.isAssignableFrom(clazz)) {
//					scenes[i] = (Scene) clazz.newInstance();
				}
				
				else if (Game.class.isAssignableFrom(clazz)) {
					clazz.newInstance();
				}
					
			}
	    		    	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
				
	}

}