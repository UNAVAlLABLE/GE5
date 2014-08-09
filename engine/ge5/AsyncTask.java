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
// divide the tasks between threads. Use getIteration() within run() to get the current loop number:
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
// 	AsyncTask task = new AsyncTask () {
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

	private final static int nThreads = Runtime.getRuntime().availableProcessors();
	private final static WorkerThread[] workerThreads = new WorkerThread[nThreads];

	private static volatile LinkedList<AsyncTask> taskQueue = new LinkedList<AsyncTask>();

	private volatile int iterator, end, start, groupsToStart, groupsToEnd;
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

		this.start = start;
		this.end = end;
		iterator = start - 1;
		groupsToStart = nThreads;
		groupsToEnd = nThreads;

		if(start >= end){


			synchronized (this) {
				notify();
			}

			isDone = true;
			return;

		}

		synchronized (taskQueue) {

			for(int i = 0; i < groupsToStart + 1;i++)
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

			} catch (final Exception e) {}

		}

	}

	public static synchronized void awaitAll() {

		for(final AsyncTask task:taskQueue)
			task.await();

	}

	private void execute () {

		int i = 0;

		if(groupsToStart-- > 0){

			while (i < (end - start) / nThreads){

				synchronized (this) {

					iterator++;

				}

				run();
				i++;

			}

			synchronized (this) {

				groupsToEnd--;

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

				groupsToEnd--;

			}

		}

		if(groupsToEnd == 0){

			synchronized (this) {

				isDone = true;
				notify();

			}

		}

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

						} catch (final InterruptedException e) {}

					}

					task = taskQueue.removeFirst();

				}

				try {

					task.execute();

				} catch (final RuntimeException e) {}

			}

		}

	}

}
