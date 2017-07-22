package com.nowcoder;

import sun.reflect.annotation.ExceptionProxy;

import javax.swing.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MyThread extends Thread{
	int tid;
	public MyThread(int id){
		tid = id;
	}

	@Override
	public void run() {
		try{
			for(int i = 0; i < 10; i++){
				Thread.sleep(1000);
				System.out.println(String.format("Thread%d: %d", tid, i));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public class MultiThreadTests {
	public static void testThread(){
		/*for(int i = 0; i < 10; i++){
			new MyThread(i).start();
		}*/

		for(int i = 0; i < 10; i++){
			int tid = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try{
						for(int j = 0; j < 10; j++){
							Thread.sleep(1000);
							System.out.println(String.format("Thread%d: %d", tid, j));
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public static Object obj = new Object();

	public static void testSync1(){
		synchronized (obj){
			try{
				for(int i = 0; i < 10; i++){
					Thread.sleep(1000);
					System.out.println(String.format("Thread1: %d", i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void testSync2(){
		synchronized (new Object()){
			try{
				for(int i = 0; i < 10; i++){
					Thread.sleep(1000);
					System.out.println(String.format("Thread2: %d", i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void testSync(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 10; i++){
					testSync1();
					testSync2();
				}
			}
		}).start();
	}

	static class Consumer implements Runnable {
		private BlockingQueue<String> q;
		public Consumer(BlockingQueue<String> q) {
			this.q = q;
		}
		@Override
		public void run() {
			try {
				while (true) {
					System.out.println(Thread.currentThread().getName() + ":" + q.take());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static class Producer implements Runnable {
		private BlockingQueue<String> q;
		public Producer(BlockingQueue<String> q) {
			this.q = q;
		}
		@Override
		public void run() {
			try {
				for (int i = 0; i < 100; ++i) {
					Thread.sleep(1000);
					q.put(String .valueOf(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void testBlockingQueue(){
		BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
		new Thread(new Producer(q)).start();
		new Thread(new Consumer(q), "Consumer1").start();
		new Thread(new Consumer(q), "Consumer2").start();

	}

	private static int userId;
	private static ThreadLocal<Integer> threadUserIds = new ThreadLocal<Integer>();
	public static void testThreadLocal(){
		for(int i = 0; i < 10; i++){
			final int id = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try{
						//threadUserIds.set(id);
						userId = id;
						Thread.sleep(1000);
						System.out.println("ThreadLocal: " + userId);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public static void testExecutor(){
		//ExecutorService ec = Executors.newSingleThreadExecutor();
		ExecutorService ec = Executors.newFixedThreadPool(2);
		ec.submit(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 10; i++){
					try{
						Thread.sleep(1000);
						System.out.println("Executor1: " + i);
					}catch(Exception e){
						e.printStackTrace();
					}

				}
			}
		});

		ec.submit(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 10; i++){
					try{
						Thread.sleep(1000);
						System.out.println("Executor2: " + i);
					}catch(Exception e){
						e.printStackTrace();
					}

				}
			}
		});
	}

	private static int count = 0;
	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	public static void testAtomic(){

		for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try{
						for(int j = 0; j < 10; j++){
							//count++;
							Thread.sleep(10);
							//count++;
							System.out.println(atomicInteger.incrementAndGet());
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();

		}
	}

	public static void testFuture(){
		ExecutorService ec = Executors.newSingleThreadExecutor();
		Future<Integer> future = ec.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(5000);
				return 1;
			}
		});

		try{
			System.out.println(future.get(100, TimeUnit.MILLISECONDS));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		//testThread();
		testSync();
		//testBlockingQueue();
		//testThreadLocal();
		//testExecutor();
		//testAtomic();
		//testFuture();
	}
}
