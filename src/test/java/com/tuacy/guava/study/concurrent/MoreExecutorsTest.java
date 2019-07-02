package com.tuacy.guava.study.concurrent;

import com.google.common.util.concurrent.MoreExecutors;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @name: MoreExecutorsTest
 * @author: tuacy.
 * @date: 2019/7/2.
 * @version: 1.0
 * @Description:
 */
public class MoreExecutorsTest {

	@Test
	public void shutdownAndAwaitTermination() {

		Callable<String> callable0 = new Callable<String>() {
			@Override
			public String call() throws Exception {
				while (true) {

				}
//				return "返回结果";
			}
		};

		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.submit(callable0);
		boolean shutDownResult = MoreExecutors.shutdownAndAwaitTermination(executorService, 5, TimeUnit.SECONDS);
		System.out.println("是否成功关闭：" + shutDownResult);
	}

}
