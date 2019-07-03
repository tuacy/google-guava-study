package com.tuacy.guava.study.concurrent;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @name: FuturesTest
 * @author: tuacy.
 * @date: 2019/6/28.
 * @version: 1.0
 * @Description:
 */
public class FuturesTest {

	private final static ListeningExecutorService sService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	//immediateFuture
	@Test
	public void immediateFuture() {

		ListenableFuture<String> future = Futures.immediateFuture("tuacy");

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
			}
		}, MoreExecutors.directExecutor());
	}

	// immediateFailedFuture
	@Test
	public void immediateFailedFuture() {
		ListenableFuture<String> future = Futures.immediateFailedFuture(new Exception("error"));

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
			}
		}, MoreExecutors.directExecutor());
	}

	// immediateCancelledFuture
	@Test
	public void immediateCancelledFuture() {

		ListenableFuture<String> future = Futures.immediateCancelledFuture();

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
				System.out.println(t.getMessage());
			}
		}, MoreExecutors.directExecutor());
	}

	// submitAsync
	@Test
	public void submitAsync() {
		final CountDownLatch latch = new CountDownLatch(1);

		ListenableFuture<String> future = Futures.submitAsync(new AsyncCallable() {
			@Override
			public ListenableFuture call() throws Exception {
				final ListenableFuture<String> asyncFuture = sService.submit(() -> {
					System.out.println("任务开始执行...");
					// 模拟一个耗时操作
					Thread.sleep(10 * 1000);
					return "我是正常的";
				});
				return asyncFuture;
			}
		}, Executors.newCachedThreadPool());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// scheduleAsync -- 延时一段时间去执行AsyncCallable里面的任务
	@Test
	public void scheduleAsync() {
		final CountDownLatch latch = new CountDownLatch(1);

		ListenableFuture<String> future = Futures.scheduleAsync(new AsyncCallable() {
			@Override
			public ListenableFuture call() throws Exception {
				final ListenableFuture<String> asyncFuture = sService.submit(() -> {
					System.out.println("业务任务开始执行...");
					// 模拟一个耗时操作
					Thread.sleep(10 * 1000);
					System.out.println("业务任务执行完成");
					return "我是正常的";
				});
				return asyncFuture;
			}
		}, 5, TimeUnit.SECONDS, Executors.newSingleThreadScheduledExecutor());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// catching
	@Test
	public void catching() {
		final boolean exceptionFlag = true;
		final CountDownLatch latch = new CountDownLatch(1);
		final ListenableFuture<String> catchingFuture = sService.submit(() -> {
			System.out.println("业务任务开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(10 * 1000);
			if (exceptionFlag) {
				throw new IllegalArgumentException("参数异常");
			}
			System.out.println("业务任务执行完成");
			return "我是正常的";
		});

		ListenableFuture<String> future = Futures.catching(catchingFuture, IllegalArgumentException.class,
														   new Function<IllegalArgumentException, String>() {
															   @Nullable
															   @Override
															   public String apply(@Nullable IllegalArgumentException input) {
																   return "error";
															   }
														   }, MoreExecutors.directExecutor());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	//catchingAsync
	@Test
	public void catchingAsync() {
		final boolean exceptionFlag = true;
		final CountDownLatch latch = new CountDownLatch(1);
		final ListenableFuture<String> catchingFuture = sService.submit(() -> {
			System.out.println("业务任务开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(10 * 1000);
			if (exceptionFlag) {
				throw new IllegalArgumentException("参数异常");
			}
			System.out.println("业务任务执行完成");
			return "我是正常的";
		});

		AsyncFunction<IllegalArgumentException, String> asyncFunction = new AsyncFunction<IllegalArgumentException, String>() {
			@Override
			public ListenableFuture<String> apply(@Nullable IllegalArgumentException input) throws Exception {
				ListenableFuture<String> asyncFuture = sService.submit(() -> {
					System.out.println("发生特定异常之后，我开始执行...");
					// 模拟一个耗时操作
					Thread.sleep(10 * 1000);
					System.out.println("发生特定异常之后，我执行完成");
					return "我是异常之后执行的";
				});
				return asyncFuture;
			}
		};

		ListenableFuture<String> future = Futures.catchingAsync(catchingFuture, IllegalArgumentException.class, asyncFunction,
																MoreExecutors.directExecutor());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	//withTimeout -- 多长时间没有获取到结果就抛异常
	@Test
	public void withTimeout() {
		final CountDownLatch latch = new CountDownLatch(1);
		ListenableFuture<String> listenableFuture1 = sService.submit(() -> {
			System.out.println("任务开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务结束");
			return "我是正常的";
		});

		ListenableFuture<String> future = Futures.withTimeout(listenableFuture1, 5, TimeUnit.SECONDS,
															  Executors.newSingleThreadScheduledExecutor());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// transformAsync
	@Test
	public void transformAsync() {
		final CountDownLatch latch = new CountDownLatch(1);
		ListenableFuture<String> baseFuture = sService.submit(() -> {
			System.out.println("任务开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务结束");
			return "我是正常的";
		});

		AsyncFunction<String, String> asyncFunction = new AsyncFunction<String, String>() {
			@Override
			public ListenableFuture<String> apply(@Nullable String input) throws Exception {
				ListenableFuture<String> asyncFuture = sService.submit(() -> {
					System.out.println("transform，我开始执行...");
					// 模拟一个耗时操作
					Thread.sleep(10 * 1000);
					System.out.println("transform，我执行完成");
					return input + " 我是transform执行的";
				});
				return asyncFuture;
			}
		};

		ListenableFuture<String> future = Futures.transformAsync(baseFuture, asyncFunction, MoreExecutors.directExecutor());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// transform
	@Test
	public void transform() {
		final CountDownLatch latch = new CountDownLatch(1);
		ListenableFuture<String> baseFuture = sService.submit(() -> {
			System.out.println("任务开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务结束");
			return "我是正常的";
		});

		Function<String, String> asyncFunction = new Function<String, String>() {

			@Nullable
			@Override
			public String apply(@Nullable String input) {
				return input + "  transform";
			}
		};

		ListenableFuture<String> future = Futures.transform(baseFuture, asyncFunction, MoreExecutors.directExecutor());

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println("正常执行完成 -- " + result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// allAsList 同时启动多个任务，等所有的任务都结束了之后，在统一获取结果。如果当中 有一个任务抛异常了，整个就异常
	@Test
	public void allAsList() {
		final CountDownLatch latch = new CountDownLatch(1);
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		ListenableFuture<String> baseFuture1 = sService.submit(() -> {
			System.out.println("任务1开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			//			if (exception) {
			//				throw new IllegalArgumentException("exception");
			//			}
			System.out.println("任务1结束");
			return "任务1返回值";
		});

		ListenableFuture<List<String>> future = Futures.allAsList(baseFuture0, baseFuture1);

		Futures.addCallback(future, new FutureCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> result) {
				if (result != null && !result.isEmpty()) {
					System.out.println(result);
				}
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// whenAllComplete -- 所有任务都执行了完了之后(有异常的也算)就调用，
	@Test
	public void whenAllComplete() {
		final CountDownLatch latch = new CountDownLatch(1);
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		ListenableFuture<String> baseFuture1 = sService.submit(() -> {
			System.out.println("任务1开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			if (exception) {
				throw new IllegalArgumentException("exception");
			}
			System.out.println("任务1结束");
			return "任务1返回值";
		});

		Futures.FutureCombiner<String> future = Futures.whenAllComplete(baseFuture0, baseFuture1);
		ListenableFuture future1 = future.call(new Callable<String>() {
			@Override
			public String call() throws Exception {
				//				String future0Result = baseFuture0.get();
				//				System.out.println(future0Result);
				return "所有任务都执行完了";
			}
		}, MoreExecutors.directExecutor());

		Futures.addCallback(future1, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (result != null && !result.isEmpty()) {
					System.out.println(result);
				}
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// whenAllSucceed -- 所有任务成功执行之后会调用
	@Test
	public void whenAllSucceed() {
		final CountDownLatch latch = new CountDownLatch(1);
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		ListenableFuture<String> baseFuture1 = sService.submit(() -> {
			System.out.println("任务1开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			if (exception) {
				throw new IllegalArgumentException("exception");
			}
			System.out.println("任务1结束");
			return "任务1返回值";
		});

		Futures.FutureCombiner<String> future = Futures.whenAllSucceed(baseFuture0, baseFuture1);
		ListenableFuture future1 = future.call(new Callable<String>() {
			@Override
			public String call() throws Exception {
				//				String future0Result = baseFuture0.get();
				//				System.out.println(future0Result);
				return "所有任务都执行完了";
			}
		}, MoreExecutors.directExecutor());

		Futures.addCallback(future1, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (result != null && !result.isEmpty()) {
					System.out.println(result);
				}
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// nonCancellationPropagating
	// 创建一个新的ListenableFuture，当给定的future完成时，它的结果才会被设置。取消给定的future也会取消返回 的ListenableFuture，但是取消返回的ListenableFuture对给定的future没有影响
	@Test
	public void nonCancellationPropagating() {
		final CountDownLatch latch = new CountDownLatch(1);
		ListenableFuture<String> baseFuture = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		//		Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
		Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
		baseFuture.cancel(true);

		ListenableFuture<String> future = Futures.nonCancellationPropagating(baseFuture);

		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (result != null && !result.isEmpty()) {
					System.out.println(result);
				}
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// successfulAsList
	@Test
	public void successfulAsList() {
		final CountDownLatch latch = new CountDownLatch(1);
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		ListenableFuture<String> baseFuture1 = sService.submit(() -> {
			System.out.println("任务1开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			if (exception) {
				throw new IllegalArgumentException("exception");
			}
			System.out.println("任务1结束");
			return "任务1返回值";
		});

		ListenableFuture<List<String>> future = Futures.successfulAsList(baseFuture0, baseFuture1);

		Futures.addCallback(future, new FutureCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> result) {
				if (result != null && !result.isEmpty()) {
					for (String item : result) {
						System.out.println(item);
					}
				}
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// inCompletionOrder
	@Test
	public void inCompletionOrder() {
		final CountDownLatch latch = new CountDownLatch(1);
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(3 * 1000);
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		ListenableFuture<String> baseFuture1 = sService.submit(() -> {
			System.out.println("任务1开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(2 * 1000);
			//			if (exception) {
			//				throw new IllegalArgumentException("exception");
			//			}
			System.out.println("任务1结束");
			return "任务1返回值";
		});

		// 按照完成时间排序
		ImmutableList<ListenableFuture<String>> futureList = Futures.inCompletionOrder(Lists.newArrayList(baseFuture0, baseFuture1));

		Futures.addCallback(futureList.get(0), new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println(result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("抛异常了");
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

	// getChecked -- 如果执行过程中产生了非RuntimeException，Error之外的异常会转换为我们自定义的异常
	@Test
	public void getChecked() {
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(3 * 1000);
			if (exception) {
				throw new Exception("异常了");
			}
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		String result = null;
		try {
			result = Futures.getChecked(baseFuture0, CheckedException.class);
			//			result = Futures.getUnchecked(baseFuture0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	// getUnchecked -- 会把异常转换为 UncheckedExecutionException
	@Test
	public void getUnchecked() {
		final boolean exception = true;
		ListenableFuture<String> baseFuture0 = sService.submit(() -> {
			System.out.println("任务0开始执行...");
			// 模拟一个耗时操作
			Thread.sleep(3 * 1000);
			if (exception) {
				throw new IllegalArgumentException("异常了");
			}
			System.out.println("任务0结束");
			return "任务0返回值";
		});

		String result = null;
		try {
			result = Futures.getUnchecked(baseFuture0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}


	public static class CheckedException extends Exception {

		public CheckedException() {
		}

		public CheckedException(String message) {
			super(message);
		}

		public CheckedException(String message, Throwable cause) {
			super(message, cause);
		}

		public CheckedException(Throwable cause) {
			super(cause);
		}

		public CheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}
	}
}
