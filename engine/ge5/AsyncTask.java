package ge5;

import java.util.LinkedList;

public class AsyncTask{
	
	private static final WorkerThread[] workerThreads;  
	private static volatile LinkedList<Runnable> taskQueue = new LinkedList<Runnable>();

    static {
    	
    	// Gets number of processors (includes hyper threading)
    	int cores = Runtime.getRuntime().availableProcessors();  
    	
    	// Initializes each element as null
    	workerThreads = new WorkerThread[cores];
    	    	
		for(int i = 0; i < cores; i++){
			
			workerThreads[i] = new WorkerThread();
			workerThreads[i].start();
			
		}
		
    }
	
	public AsyncTask(Runnable r) {
		
        synchronized (taskQueue) {
        	
			taskQueue.addLast(r);
			taskQueue.notify();
			
		}
		
	}
	
	static class WorkerThread extends Thread {
		
		@Override
		public void run(){
			
			Runnable task;
			
			while (true) {
		    	
				synchronized(taskQueue) {
		        	
					while (taskQueue.isEmpty()) {
							
		                try {
		                	
							taskQueue.wait();
							
						} catch (InterruptedException e) {
							
							e.printStackTrace();
							
						}
		                	
		            }
		
					task = (Runnable) taskQueue.removeFirst();
		        }
		
		        try {
		        			        	
		        	task.run();		 
		            
		        }
		        
		        catch (RuntimeException e) {
		        	
		        	e.printStackTrace();
		        	
		        }
		        
		    }
			
		}
		
	}
	     
}
