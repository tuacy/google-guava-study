package com.tuacy.guava.study.service;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @name: AbstractExecutionThreadServiceImplTest
 * @author: tuacy.
 * @date: 2019/6/29.
 * @version: 1.0
 * @Description:
 */
public class AbstractExecutionThreadServiceImplTest {

	@Test
	public void abstractExecutionThreadServiceTest() {
		// 定义我们自定义的AbstractExecutionThreadServiceImpl的类对象
		AbstractExecutionThreadServiceImpl service = new AbstractExecutionThreadServiceImpl();
		// 添加状态监听
		service.addListener(new Service.Listener() {
			@Override
			public void starting() {
				System.out.println("服务开始启动");
			}

			@Override
			public void running() {
				System.out.println("服务开始运行");
			}

			@Override
			public void stopping(Service.State from) {
				System.out.println("服务关闭中");
			}

			@Override
			public void terminated(Service.State from) {
				System.out.println("服务终止");
			}

			@Override
			public void failed(Service.State from, Throwable failure) {
				System.out.println("失败，cause：" + failure.getCause());
			}
		}, MoreExecutors.directExecutor());
		// 启动服务
		service.startAsync().awaitRunning();
		System.out.println("服务状态为:" + service.state());
		// 等待30s
		Uninterruptibles.sleepUninterruptibly(30, TimeUnit.SECONDS);
		// 停止服务
		service.stopAsync().awaitTerminated();

		System.out.println("服务状态为:" + service.state());
	}

}
