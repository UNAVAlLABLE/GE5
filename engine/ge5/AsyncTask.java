// Class that allows you to create tasks to be added to a task queue and be run in thread pool

// To create a task simply create a new anonymous AsyncTask overriding method run() like so:
//
// 	new AsyncTask () {
// 		public void run() {
//
// 			// Code goes here
//
// 		}
// 	};

// You can also create an AsyncTask that that runs a set number of times by creating it with
// the start (inclusive) and end (exclusive) integer arguments. Use getIteration() within
// run() to get the current loop number:
//
// 	new AsyncTask (10,15) {
// 		public void run() {
//
// 			System.out.print(getIteration());
//
// 		}
// 	};
//
// The code above prints 10 11 12 13 14
//
// Creating an AsyncTask with a single integer argument assumes start = 0

// To pause the calling thread until the completion of the task call the await() method:
//
// 	AsyncTask task  new AsyncTask () {
// 		public void run() {
//
// 			// Code goes here
//
// 		}
// 	};
//
// task.await();

package ge5;

import java.util.LinkedList;

public abstract class AsyncTask implements Runnable {

	public final static int nThreads = Runtime.getRuntime().availableProcessors();
	private final static WorkerThread[] workerThreads;
	
	private static volatile LinkedList<AsyncTask> taskQueue = new LinkedList<AsyncTask>();

	private volatile int iterator, end, start, groupIndex; 
	private volatile boolean isDone = false;
	
	static {

		workerThreads = new WorkerThread[nThreads];

		for (int i = 0; i < nThreads; i++) {

			workerThreads[i] = new WorkerThread();
			workerThreads[i].start();

		}
		
	}

	public AsyncTask() {
		
		this(1);
		
	}
	
	public AsyncTask(int end) {
		
		this(0,end);
		
	}

	public AsyncTask(int start, int end) {
		
		this.start = start;
		this.end = end;
		
		iterator = start - 1;
		groupIndex = nThreads;
		
		if(start > end)
			return;
			
		if(start == end)
			end++;
		
		synchronized (taskQueue) {
			
			for(int i = 0; i < groupIndex + 1;i++)
				taskQueue.addLast(this);
						
			taskQueue.notifyAll();
			
		}

	}

	protected int getIteration() {

		return iterator;

	}
	
	public synchronized void await() {
		
		while (!isDone){
			
			try {
				
				wait();
				
			} catch (Exception e) {

			}
			
		}
			
	}
	
	public static synchronized void awaitAll() {
		
		for(AsyncTask task:taskQueue) 
			task.await();
		
	}
	
	private void execute () {
		
		int i = 0;

		if(groupIndex-- > 0){

			while (i < (end - start) / nThreads){
				
				synchronized (this) {
					
					iterator++;
				
				}
				
				run();
				i++;
				
			}
						
		}else{
			
			while (i < (end - start) % nThreads){
				
				synchronized (this) {
					
					iterator++;
				
				}
				
				run();
				i++;
				
			}
			
			synchronized (this) {
				
				isDone = true;
				notify();
			
			}
			
		}
				
	}

	@Override
	public abstract void run(); // This is to be overwritten by the task caller

	private static class WorkerThread extends Thread {
		
		static int nThreads = 0;
		final int threadID = nThreads++;
		
		@Override
		public void run() {

			AsyncTask task;
			
			while (true) {

				synchronized (taskQueue) {

					while (taskQueue.isEmpty()) {

						try {

							taskQueue.wait();

						} catch (InterruptedException e) {
					
							
							
						}

					}

					task = taskQueue.removeFirst();

				}
				
				try {
																									
					task.execute();
										
				} catch (RuntimeException e) {
					

					
				}

			}

		}

	}

}
