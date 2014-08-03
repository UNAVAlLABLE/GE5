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

// Creating an AsyncTask with a single argument assumes start = 0;
//
// 	new AsyncTask (5) {
// 		public void run() {
//
// 			System.out.print(getIteration());
//
// 		}
// 	};
//
// The code above prints 0 1 2 3 4

package ge5;

import java.util.LinkedList;

public abstract class AsyncTask implements Runnable {

	// TODO add option so that iterations can be queued and run on the entire thread pool
	// instead of always looped through by one thread

	// TODO add a way to start one / multiple tasks and wait untill they are done

	private static final WorkerThread[] workerThreads;
	private static volatile LinkedList<AsyncTask> taskQueue = new LinkedList<AsyncTask>();

	private int start, end, iterator;

	static {

		// Gets number of processors (includes hyper threading)
		int cores = Runtime.getRuntime().availableProcessors();

		// Initializes each element as null
		workerThreads = new WorkerThread[cores];

		for (int i = 0; i < cores; i++) {

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

		if (end < start)
			return;

		this.start = start;
		this.end = end;

		synchronized (taskQueue) {

			taskQueue.addLast(this);
			taskQueue.notify();

		}

	}

	protected int getIteration() {

		return iterator;

	}

	private void execute() {
				
		for(iterator = start;iterator < end; iterator++)
			run();
		
	}

	@Override
	public abstract void run(); // This is to be overwritten by the task caller

	private static class WorkerThread extends Thread {

		@Override
		public void run() {

			AsyncTask task;

			while (true) {

				synchronized (taskQueue) {

					while (taskQueue.isEmpty()) {

						try {

							taskQueue.wait();

						} catch (InterruptedException e) {}

					}

					task = (AsyncTask) taskQueue.removeFirst();

				}

				try {

					task.execute();

				} catch (RuntimeException e) {}

			}

		}

	}

}
