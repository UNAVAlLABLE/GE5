package ge5;

import java.util.LinkedList;

public abstract class AsyncTask implements Runnable{
	
	private static final WorkerThread[] workerThreads;  
	private static volatile LinkedList<AsyncTask> taskQueue = new LinkedList<AsyncTask>();
	
	private int iterations;
	int iterator;
	
	// True if task has been completed
	public Boolean isDone = false;
	
	// True if task is actually running on a thread
	public Boolean isRunning = false;
					
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
    
    public AsyncTask() {
    	
    	this(1);
    	
    }
	
	public AsyncTask(int i) {
		
		if(i <= 0){
			
			System.out.print("Iteratoions must be larger thatn 0. Currently +" + i);
			return;
			
		}
		
		iterations = i;
				
		synchronized (taskQueue) {
        	
			taskQueue.addLast(this);
			taskQueue.notify();
			
		}
		
	}
	
	protected int getIteration(){
		
		return iterator;
		
	}
	
	void start(){
		
		for(int i = 0; i < iterations; i++){
			
			iterator = i;			
			run();
			
		}
			
		
	}
	
	@Override
	public abstract void run(); // This is to be over written by task caller
	
	static class WorkerThread extends Thread {
				
		@Override
		public void run(){
			
			AsyncTask task;
			
			while (true) {
		    	
				synchronized(taskQueue) {
		        	
					while (taskQueue.isEmpty()) {
							
		                try {
		                	
							taskQueue.wait();
							
						} catch (InterruptedException e) {
							
							e.printStackTrace();
							
						}
		                	
		            }
		
					task = (AsyncTask) taskQueue.removeFirst();
					
		        }
		
		        try {
		        	
		        	task.isRunning = true;
		        	task.start();	
		        	task.isRunning = false;
		        	task.isDone = true;
		            
		        }
		        
		        catch (RuntimeException e) {
		        	
		        	e.printStackTrace();
		        	
		        }
		        
		    }
			
		}
		
	}
	     
}
