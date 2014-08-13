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
// the start (inclusive) and end (exclusive) integer arguments. AsyncTask will automatically
// divide the task into batches to be run on the thread pool.
//
// Use getIteration() to get the current loop number:
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

// To pause the calling thread until the completion of the task call the await() method:
//
// 	AsyncTask task = new AsyncTask () {
// 		public void run() {
//
// 			// Code goes here
//
// 		}
// 	};
//
// task.await();

// TODO Create priority based queue system
// TODO Debug async related crashes and make sure all WorkerThreads remain active.

package ge5;

import java.util.LinkedList;

public abstract class AsyncTask implements Runnable {

	private final static int nThreads = Runtime.getRuntime().availableProcessors();
	private final static WorkerThread[] workerThreads = new WorkerThread[nThreads];

	private static volatile LinkedList<AsyncTask> taskQueue = new LinkedList<AsyncTask>();

	private volatile int iterator, nGroups;
	private final int iterations, start;
	private volatile boolean isDone = false;

	static {

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

		iterations = end - start;
		this.start = start;

		if(start >= end){

			synchronized (this) {

				notify();
				isDone = true;

			}

			return;

		}

		iterator = start;

		nGroups = iterations > nThreads ? nThreads : iterations;

		synchronized (taskQueue) {

			for(int i = 0; i < nGroups;i++)
				taskQueue.addLast(this);

			taskQueue.notifyAll();

		}

	}

	protected int getIteration() {

		return iterator;

	}

	public float getProgress() {

		if(isDone)
			return 1f;

		return (float) iterator / (float) (iterations + start);

	}

	public synchronized void await() {

		while (!isDone)
			try {

				wait();

			} catch (final Exception e) {}

	}

	public static synchronized void awaitAll() {

		for(final AsyncTask task:taskQueue)
			task.await();

	}

	private void execute () {

		synchronized (this) {nGroups--;}

		for (int i = 0; i < iterations / nThreads; i++) {

			run();
			synchronized (this) {iterator++;}

		}

		if(nGroups == 0) for (int i = 0; i < iterations % nThreads; i++) {

			run();
			synchronized (this) {iterator++;}

		}

		if(iterator == iterations + start){

			isDone = true;
			synchronized (this) {notify();}

		}

	}

	@Override
	public abstract void run(); // This is to be overwritten by the task caller

	private static class WorkerThread extends Thread {

		static int nThreads = 0;
		int threadID = nThreads++;

		@Override
		public void run() {

			AsyncTask task;

			while (true) {

				synchronized (taskQueue) {

					while (taskQueue.isEmpty())
						try {

							taskQueue.wait();

						} catch (final InterruptedException e) {

							// Just to make sure a thread continues to wait even when interrupted
							System.out.println("Worker thread " + threadID + " interrupted while waiting for task");

						}

					task = taskQueue.removeFirst();

				}

				try {

					task.execute();

				} catch (final RuntimeException e) {

					// This is to make sure that a runtime exception does not terminate the thread
					System.out.println("WorkerThread " + threadID + ": Runtime exception during execution.");

				}

			}

		}

	}

}
