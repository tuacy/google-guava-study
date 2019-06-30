package com.tuacy.guava.study.concurrent;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.*;

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

        ListenableFuture<String> future = Futures.catching(catchingFuture,
                IllegalArgumentException.class,
                new Function<IllegalArgumentException, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable IllegalArgumentException input) {
                        return "error";
                    }
                },
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

        AsyncFunction<IllegalArgumentException, String> asyncFunction = new  AsyncFunction<IllegalArgumentException, String>() {
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

        ListenableFuture<String> future = Futures.catchingAsync(catchingFuture,
                IllegalArgumentException.class,
                asyncFunction,
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

    //withTimeout
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

        ListenableFuture<String> future = Futures.withTimeout(listenableFuture1,
                5,
                TimeUnit.SECONDS,
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

}
