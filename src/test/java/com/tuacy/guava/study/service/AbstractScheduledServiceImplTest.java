package com.tuacy.guava.study.service;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @name: AbstractScheduledServiceImplTest
 * @author: tuacy.
 * @date: 2019/6/29.
 * @version: 1.0
 * @Description:
 */
public class AbstractScheduledServiceImplTest {

	@Test
	public void abstractScheduledServiceImplTest() {
		// 定义AbstractScheduledServiceImpl对象
		AbstractScheduledServiceImpl service = new AbstractScheduledServiceImpl();
		// 添加状态监听器
		service.addListener(new Service.Listener() {
			@Override
			public void starting() {
				System.out.println("服务开始启动.....");
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
		// 启动任务
		service.startAsync().awaitRunning();
		System.out.println("服务状态为:" + service.state());

		// 等待30s
		Uninterruptibles.sleepUninterruptibly(30, TimeUnit.SECONDS);

		// 关闭任务
		service.stopAsync().awaitTerminated();
		System.out.println("服务状态为:" + service.state());
	}

}
