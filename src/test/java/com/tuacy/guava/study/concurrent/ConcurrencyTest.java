package com.tuacy.guava.study.concurrent;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @name: RedisStudyApplicationTest
 * @author: tuacy.
 * @date: 2019/6/28.
 * @version: 1.0
 * @Description:
 */
public class ConcurrencyTest {

	//定义一个线程池，用于处理所有任务 -- MoreExecutors
	private final static ListeningExecutorService sService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	/**
	 * 通过JdkFutureAdapters.listenInPoolThread()创建ListenableFuture
	 */
	@Test
	public void jdkFutureAdaptersTest() {

		final CountDownLatch latch = new CountDownLatch(1);
		FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("任务开始执行");
				// 模拟一个耗时操作
				Thread.sleep(10 * 1000);
				return "任务正常结束";
			}
		});
		// 执行任务
		Executors.newCachedThreadPool().execute(futureTask);
		// futureTask转换成ListenableFuture
		ListenableFuture<String> listenableFuture = JdkFutureAdapters.listenInPoolThread(futureTask);
		// 监听返回结果
		Futures.addCallback(listenableFuture, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("任务结果: 正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("任务结果: 抛异常了");
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过ListenableFutureTask.create()创建ListenableFutureTask
	 */
	@Test
	public void listenableFutureTaskCreateTest() {

		final CountDownLatch latch = new CountDownLatch(2);
		// Callable转换成ListenableFutureTask
		ListenableFutureTask<String> listenableFutureTask1 = ListenableFutureTask.create(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// 模拟一个耗时操作
				Thread.sleep(10 * 1000);
				return "我是正常的";
			}
		});
		// 线程池里面执行任务
		//		Executors.newCachedThreadPool().execute(listenableTask);
		sService.execute(listenableFutureTask1);
		// 监听返回结果
		Futures.addCallback(listenableFutureTask1, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("任务1(测试正常执行)结果: 正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("任务1(测试正常执行)结果: 抛异常了");
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		// Runnable转换成ListenableFutureTask
		ListenableFutureTask<String> listenableFutureTask2 = ListenableFutureTask.create(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "success");
		sService.execute(listenableFutureTask2);
		Futures.addCallback(listenableFutureTask2, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("任务1(测试正常执行)结果: 正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("任务1(测试正常执行)结果: 抛异常了");
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 简单的使用ListenableFuture
	 */
	@Test
	public void taskCallbackTest() {

		final CountDownLatch latch = new CountDownLatch(2);
		final boolean normal = false;
		// 任务1 测试正常执行 简单的测试下任务的正常执行
		ListenableFuture<String> listenableFuture1 = sService.submit(() -> {
			// 模拟一个耗时操作
			Thread.sleep(10 * 1000);
			return "我是正常的";
		});
		Futures.addCallback(listenableFuture1, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("任务1(测试正常执行)结果: 正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("任务1(测试正常执行)结果: 抛异常了");
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		// 任务2 测试异常执行 简单的测试下任务执行过程中有异常情况
		ListenableFuture<String> listenableFuture2 = sService.submit(() -> {
			// 模拟一个耗时操作
			Thread.sleep(10 * 1000);
			if (normal) {
				return "我是正常的";
			} else {
				throw new Exception("哎呀，有异常了");
			}
		});
		Futures.addCallback(listenableFuture2, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("任务2(测试异常执行)结果: 正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("任务2(测试异常执行)结果: 抛异常了");
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Futures.transformAsync()支持多个任务链式异步执行，并且后面一个任务可以拿到前面一个任务的结果
	 */
	@Test
	public void multiTaskTransformAsyncTest() {

		final CountDownLatch latch = new CountDownLatch(1);

		// 第一个任务
		ListenableFuture<String> task1 = sService.submit(() -> {
			System.out.println("第一个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第一个任务的结果";
		});

		// 第二个任务，里面还获取到了第一个任务的结果
		AsyncFunction<String, String> queryFunction = new AsyncFunction<String, String>() {
			public ListenableFuture<String> apply(String input) {
				return sService.submit(new Callable<String>() {
					public String call() throws Exception {
						System.out.println("第二个任务开始执行...");
						try {
							Thread.sleep(10 * 1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return input + " & 第二个任务的结果 ";
					}
				});
			}
		};

		// 把第一个任务和第二个任务关联起来
		ListenableFuture<String> first = Futures.transformAsync(task1, queryFunction, sService);

		// 监听返回结果
		Futures.addCallback(first, new FutureCallback<String>() {
			public void onSuccess(String result) {
				System.out.println("结果: " + result);
				latch.countDown();
			}

			public void onFailure(Throwable t) {
				System.out.println(t.getMessage());
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Futures.transform()支持对任务的结果做一个转换操作
	 */
	@Test
	public void taskTransformTest() {
		final CountDownLatch latch = new CountDownLatch(1);

		// 定义一个任务
		ListenableFuture<String> task1 = sService.submit(() -> {
			System.out.println("第一个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第一个任务的结果";
		});

		// 对任务的结果做转换
		Function<String, String> transformFunction = new Function<String, String>() {

			@Override
			public String apply(@Nullable String input) {
				return input + " transform";
			}
		};

		// 任务和转换关联起来
		ListenableFuture<String> first = Futures.transform(task1, transformFunction, sService);

		// 获取结果
		Futures.addCallback(first, new FutureCallback<String>() {
			public void onSuccess(String result) {
				System.out.println("结果: " + result);
				latch.countDown();
			}

			public void onFailure(Throwable t) {
				System.out.println(t.getMessage());
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Futures.allAsList()多个任务同时执行，等所有任务都结束了。在返回所有的结果。如果有一个任务异常了
	 */
	@Test
	public void taskAllAsListTest() {
		final CountDownLatch latch = new CountDownLatch(1);

		ListenableFuture<String> task1 = sService.submit(() -> {
			System.out.println("第一个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第一个任务的结果";
		});

		ListenableFuture<String> task2 = sService.submit(() -> {
			System.out.println("第二个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第二个任务的结果";
		});

		ListenableFuture<String> task3 = sService.submit(() -> {
			System.out.println("第三个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第三个任务的结果";
		});

		ListenableFuture<List<String>> first = Futures.allAsList(task1, task2, task3);

		Futures.addCallback(first, new FutureCallback<List<String>>() {
			public void onSuccess(List<String> result) {
				System.out.println("所有任务结束了!!!");
				if (result != null && !result.isEmpty()) {
					for (String item : result) {
						System.out.println(item);
					}
				}
				latch.countDown();
			}

			public void onFailure(Throwable t) {
				System.out.println(t.getMessage());
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Futures.successfulAsList()多个任务同时执行，有异常的任务返回的是null
	 */
	@Test
	public void taskSuccessfulAsListTest() {
		final CountDownLatch latch = new CountDownLatch(1);
		final boolean normal = false;

		ListenableFuture<String> task1 = sService.submit(() -> {
			System.out.println("第一个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第一个任务的结果";
		});

		ListenableFuture<String> task2 = sService.submit(() -> {
			System.out.println("第二个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (normal) {
				return "第二个任务的结果";
			} else {
				throw new Exception("哎呀，有异常了");
			}
		});

		ListenableFuture<String> task3 = sService.submit(() -> {
			System.out.println("第三个任务开始执行...");
			try {
				Thread.sleep(10 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "第三个任务的结果";
		});

		ListenableFuture<List<String>> first = Futures.successfulAsList(task1, task2, task3);

		Futures.addCallback(first, new FutureCallback<List<String>>() {
			public void onSuccess(List<String> result) {
				System.out.println("所有任务结束了!!!");
				if (result != null && !result.isEmpty()) {
					for (String item : result) {
						System.out.println(item);
					}
				}
				latch.countDown();
			}

			public void onFailure(Throwable t) {
				System.out.println(t.getMessage());
				latch.countDown();
			}
		}, MoreExecutors.directExecutor());

		try {
			// 等待所有的线程执行完
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
